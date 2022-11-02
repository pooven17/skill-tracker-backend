package com.cts.skilltrkr.service;

import java.util.Calendar;
import java.util.Date;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.cts.skilltrkr.dto.ProfileDTO;
import com.cts.skilltrkr.entity.ProfileEntity;
import com.cts.skilltrkr.exception.NotValidUpdateException;
import com.cts.skilltrkr.exception.ProfileNotFoundException;
import com.cts.skilltrkr.kafka.KafkaProducer;
import com.cts.skilltrkr.model.Profile;
import com.cts.skilltrkr.repositry.ProfileRepo;
import com.cts.skilltrkr.util.ProfileUtil;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ProfileService {

	@Autowired
	ProfileRepo profileRepo;

	@Autowired
	KafkaProducer kafkaProducer;
	
	@Value("${profile.update.days}")
	private int days;

	@Transactional
	public void createProfile(ProfileDTO request) {
		log.info("Creating Profile");
		ProfileEntity profile = profileRepo.save(request.toProfile());
		log.info("Created Profile");
		kafkaProducer.sendProfile(ProfileUtil.convertObjectToJson(profile));
		log.info("Sent to Kafka Profile Topic");
	}

	@Transactional
	public ResponseEntity<ProfileEntity> updateProfile(String id, ProfileDTO request) {
		log.info("Updating Profile");
		ProfileEntity profile = profileRepo.findById(id)
				.orElseThrow(() -> new ProfileNotFoundException("Not Found Profile for the Id:" + id));

		checkUpdateConstraint(profile.getUpdatedDate() != null ? profile.getUpdatedDate() : profile.getCreatedDate());

		ProfileEntity newProfile = request.toProfile();
		profile.setSkills(newProfile.getSkills());
//		request.getSkills().forEach(skillRequest -> {
//			profile.getSkills().forEach(skill -> {
//				if (skill.getSkillName().equalsIgnoreCase(skillRequest.getSkillName())) {
//					skill.setExpertiseLevel(skillRequest.getExpertiseLevel());
//				}
//			});
//		});

		profile.setUpdatedDate(new Date());
		ProfileEntity updatedProfile = profileRepo.save(profile);
		log.info("Updated Profile");
		kafkaProducer.sendProfile(ProfileUtil.convertObjectToJson(updatedProfile));
		log.info("Sent to Kafka Profile Topic");
		return new ResponseEntity<>(profile, HttpStatus.OK);
	}

	@Transactional
	public void deleteProfile(String id) {
		log.info("Deleting Profile");
		ProfileEntity entity = profileRepo.findById(id)
				.orElseThrow(() -> new ProfileNotFoundException("Not Found Profile for the Id:" + id));
		profileRepo.delete(entity);
		log.info("Deleted Profile");
		Profile profile = new Profile(id, true);
		kafkaProducer.sendProfile(ProfileUtil.convertObjectToJson(profile));
		log.info("Sent to Kafka Profile Topic");
	}

	private void checkUpdateConstraint(Date date) {
		Calendar updatedDate = Calendar.getInstance();
		updatedDate.setTime(date);
		updatedDate.add(Calendar.DATE, days);
		log.info("Added 10 Days:" + updatedDate.getTime());

		Calendar today = Calendar.getInstance();
		log.info("Today's Date:" + today.getTime());

		if (updatedDate.after(today)) {
			throw new NotValidUpdateException("Not Eligible for update the Skills");
		}
	}
}
