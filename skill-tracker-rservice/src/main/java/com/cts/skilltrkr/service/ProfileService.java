package com.cts.skilltrkr.service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.cts.skilltrkr.entity.ProfileEntity;
import com.cts.skilltrkr.entity.SkillEntity;
import com.cts.skilltrkr.exception.ProfileNotFoundException;
import com.cts.skilltrkr.model.Profile;
import com.cts.skilltrkr.model.ProfileSkill;
import com.cts.skilltrkr.repository.ProfileRepository;
import com.cts.skilltrkr.repository.SkillRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ProfileService {

	@Autowired
	ProfileRepository profileRepo;

	@Autowired
	SkillRepository skillRepo;

	public Map<String, List<Map<String, String>>> getSkillOptions() {
		Map<String, List<Map<String, String>>> resp = new HashMap<>();
		List<SkillEntity> skillList = skillRepo.findAll();
		List<Map<String, String>> tList = new ArrayList<>();
		skillList.stream().filter(check -> check.isTechnical()).forEach(e -> {
			Map<String, String> map = new HashMap<>();
			map.put("key", e.getSkillName());
			map.put("value", e.getSkillName());
			tList.add(map);
		});
		resp.put("TECHNICAL", tList);
		List<Map<String, String>> ntList = new ArrayList<>();
		skillList.stream().filter(check -> !check.isTechnical()).forEach(e -> {
			Map<String, String> map = new HashMap<>();
			map.put("key", e.getSkillName());
			map.put("value", e.getSkillName());
			ntList.add(map);
		});
		resp.put("NONTECHNICAL", ntList);
		return resp;
	}

	public List<Profile> getProfiles(String criteria, String criteriaValue, boolean forEditing) {
		log.info("Searching");
		List<ProfileEntity> profileList = null;
		if (StringUtils.isBlank(criteriaValue))
			throw new IllegalStateException("Criteria Value should be Non Null");
		switch (criteria) {
		case "All":
			profileList = profileRepo.findAll();
			if (!CollectionUtils.isEmpty(profileList)) {
				return manipulateResponse(profileList, "", forEditing);
			} else
				throw new ProfileNotFoundException("Profile Not Found the requested parameter");
		case "Id":
			ProfileEntity profile = profileRepo.findById(criteriaValue)
					.orElseThrow(() -> new ProfileNotFoundException("Not Found Profile for the Id:" + criteriaValue));
			profileList = new ArrayList<>();
			profileList.add(profile);
			return manipulateResponse(profileList, "", forEditing);
		case "Name":
			profileList = profileRepo.findByNameLike(criteriaValue);
			if (!CollectionUtils.isEmpty(profileList)) {
				return manipulateResponse(profileList, "", forEditing);
			} else
				throw new ProfileNotFoundException("Profile Not Found the requested parameter");
		case "Skill":
			profileList = profileRepo.findAll();
			if (!CollectionUtils.isEmpty(profileList)) {
				return manipulateResponse(profileList, criteriaValue, forEditing);
			} else
				throw new ProfileNotFoundException("Profile Not Found the requested parameter");
		default:
			throw new IllegalStateException(String.format("Unexpected Criteria: %s", criteria));
		}
	}

	private List<Profile> manipulateResponse(List<ProfileEntity> profileList, String skillName, boolean forEditing) {
		List<Profile> list = new ArrayList<>();
		profileList.stream().forEach(profile -> {
			Profile response = new Profile();
			response.setName(profile.getName());
			response.setAssociateId(profile.getAssociateId());
			response.setEmailId(profile.getEmailId());
			response.setContactNo(profile.getContactNo());
			if (StringUtils.isNotBlank(skillName)) {
				response.setTechnicalSkills(profile.getSkills().stream().filter(check -> check.isTechnical())
						.filter(s -> (s.getSkillName().equalsIgnoreCase(skillName) && s.getExpertiseLevel() > 10))
						.map(i -> new ProfileSkill(i.getSkillName(), i.getExpertiseLevel(), i.isTechnical()))
						.sorted(Comparator.comparingInt(ProfileSkill::getExpertiseLevel).reversed())
						.collect(Collectors.toList()));
				response.setNonTechnicalSkills(profile.getSkills().stream().filter(check -> !check.isTechnical())
						.filter(s -> (s.getSkillName().equalsIgnoreCase(skillName) && s.getExpertiseLevel() > 10))
						.map(i -> new ProfileSkill(i.getSkillName(), i.getExpertiseLevel(), i.isTechnical()))
						.sorted(Comparator.comparingInt(ProfileSkill::getExpertiseLevel).reversed())
						.collect(Collectors.toList()));
			} else {
				response.setTechnicalSkills(profile.getSkills().stream().filter(check -> check.isTechnical())
						.map(i -> new ProfileSkill(i.getSkillName(), i.getExpertiseLevel(), i.isTechnical()))
						.sorted(Comparator.comparingInt(ProfileSkill::getExpertiseLevel).reversed())
						.collect(Collectors.toList()));
				response.setNonTechnicalSkills(profile.getSkills().stream().filter(check -> !check.isTechnical())
						.map(i -> new ProfileSkill(i.getSkillName(), i.getExpertiseLevel(), i.isTechnical()))
						.sorted(Comparator.comparingInt(ProfileSkill::getExpertiseLevel).reversed())
						.collect(Collectors.toList()));
			}
			if (!(CollectionUtils.isEmpty(response.getTechnicalSkills())
					&& CollectionUtils.isEmpty(response.getNonTechnicalSkills())))
				list.add(response);
		});
		if (!CollectionUtils.isEmpty(list)) {
			return list.stream().sorted(Comparator.comparing(Profile::getAssociateId))
					.collect(Collectors.toList());
		}
		else
			throw new ProfileNotFoundException("Profile Not Found the requested parameter");

	}
}