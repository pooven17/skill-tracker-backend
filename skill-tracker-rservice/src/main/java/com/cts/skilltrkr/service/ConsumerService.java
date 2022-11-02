package com.cts.skilltrkr.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cts.skilltrkr.entity.ProfileEntity;
import com.cts.skilltrkr.entity.SkillEntity;
import com.cts.skilltrkr.entity.UserEntity;
import com.cts.skilltrkr.model.Profile;
import com.cts.skilltrkr.model.User;
import com.cts.skilltrkr.repository.ProfileRepository;
import com.cts.skilltrkr.repository.SkillRepository;
import com.cts.skilltrkr.repository.UserRepository;
import com.cts.skilltrkr.util.ConsumerUtil;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ConsumerService {

	@Autowired
	ProfileRepository profileRepository;

	@Autowired
	UserRepository userRepo;

	@Autowired
	SkillRepository skillRepo;

	public void processProfile(String message) {
		Profile profile = ConsumerUtil.convertProfileJsontoObject(message);
		log.info("Profile: {}", profile);
		if (profile.isToDelete()) {
			profileRepository.delete(profile.toProfile());
			log.info("Profile deleted in Mongo DB");
			return;
		}
		ProfileEntity updated = profileRepository.save(profile.toProfile());
		log.info("Profile updated in Mongo DB for the UserId:{}", updated.getAssociateId());
	}

	public void saveUser(String message) {
		User user = ConsumerUtil.convertUserJsontoObject(message);
		log.info("User:{}", user);
		UserEntity updated = userRepo.save(user.toUser());
		log.info("User updated in Mongo DB for the UserId:{}", updated.getUserName());
	}

	public void saveSkill(String message) {
		List<SkillEntity> skillList = ConsumerUtil.convertSkillListJsontoObject(message);
		log.info("skillList:{}", skillList);
		skillRepo.saveAll(skillList);
		log.info("Skills added in Mongo DB");
	}

}