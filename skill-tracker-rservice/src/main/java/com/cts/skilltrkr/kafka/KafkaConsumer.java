package com.cts.skilltrkr.kafka;

import com.cts.skilltrkr.service.ConsumerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class KafkaConsumer {

	@Value("${topic.profile.consumer}")
	private String profileTopicName;

	@Value("${topic.user.consumer}")
	private String userTopicName;
	
	@Value("${topic.skill.consumer}")
	private String skillTopicName;

	@Autowired
	private ConsumerService consumerService;

	@KafkaListener(topics = "${topic.profile.consumer}", groupId = "skill_tracker")
	public void consumeProfileMessage(ConsumerRecord<String, String> payload) {
		log.info("Topic: {}", profileTopicName);
		log.info("payload: {}", payload.value());
		consumerService.processProfile(payload.value());
	}

	@KafkaListener(topics = "${topic.user.consumer}", groupId = "skill_tracker")
	public void consumeUserMessage(ConsumerRecord<String, String> payload) {
		log.info("Topic: {}", userTopicName);
		log.info("payload: {}", payload.value());
		consumerService.saveUser(payload.value());
	}
	

	@KafkaListener(topics = "${topic.skill.consumer}", groupId = "skill_tracker")
	public void consumeSkillMessage(ConsumerRecord<String, String> payload) {
		log.info("Topic: {}", skillTopicName);
		log.info("payload: {}", payload.value());
		consumerService.saveSkill(payload.value());
	}

}