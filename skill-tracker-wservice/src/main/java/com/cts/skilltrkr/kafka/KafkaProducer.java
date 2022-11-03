package com.cts.skilltrkr.kafka;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class KafkaProducer {

	//@Value("${topic.profile.producer}")
	private String profileTopicName= "PROFILE";

	//@Value("${topic.user.producer}")
	private String userTopicName= "USER";

	//@Value("${topic.skill.producer}")
	private String skillTopicName = "SKILL";

	private final KafkaTemplate<String, String> kafkaTemplate;

	@Async
	public void sendProfile(String message) {
		log.info("Profile Payload : {}", message);
		kafkaTemplate.send(profileTopicName, message);
		log.info("Profile Payload posted");
	}

	@Async
	public void sendUser(String message) {
		log.info("User Payload : {}", message);
		kafkaTemplate.send(userTopicName, message);
		log.info("User Payload posted");
	}

	@Async
	public void sendSkills(String message) {
		log.info("Skill Payload : {}", message);
		kafkaTemplate.send(skillTopicName, message);
		log.info("Skill Payload posted");
	}

}
