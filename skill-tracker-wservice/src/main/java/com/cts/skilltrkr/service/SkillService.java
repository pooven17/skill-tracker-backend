package com.cts.skilltrkr.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cts.skilltrkr.entity.SkillEntity;
import com.cts.skilltrkr.kafka.KafkaProducer;
import com.cts.skilltrkr.repositry.SkillRepo;
import com.cts.skilltrkr.util.ProfileUtil;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class SkillService {

	@Autowired
	KafkaProducer kafkaProducer;

	@Autowired
	SkillRepo skillRepo;

	public Set<String> saveSkill() {
		log.info("Save Skills");

		Set<String> skillList = new HashSet<>();

		Set<String> techSkills = Collections.unmodifiableSet(new HashSet<>(Arrays.asList("HTML-CSS-JAVASCRIPT",
				"ANGULAR", "REACT", "SPRING", "RESTFUL", "HIBERNATE", "GIT", "DOCKER", "JENKINS", "AWS")));

		Set<String> nonTechSkills = Collections
				.unmodifiableSet(new HashSet<>(Arrays.asList("TIME MANAGEMENT", "COMMUNICATION", "NETWORKING")));
		skillList.addAll(techSkills);
		skillList.addAll(nonTechSkills);

		List<SkillEntity> skillEntityList = new ArrayList<>();

		techSkills.stream().forEach(e -> skillEntityList
				.add(SkillEntity.builder().skillId(e).skillName(e).skillDesc(e).technical(true).build()));

		nonTechSkills.stream().forEach(e -> skillEntityList
				.add(SkillEntity.builder().skillId(e).skillName(e).skillDesc(e).technical(false).build()));

		skillRepo.saveAll(skillEntityList);

		kafkaProducer.sendSkills(ProfileUtil.convertObjectToJson(skillEntityList));
		log.info("Posted Skills to Kafka");
		return skillList;
	}

}
