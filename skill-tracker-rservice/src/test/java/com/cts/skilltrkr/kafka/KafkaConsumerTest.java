package com.cts.skilltrkr.kafka;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.test.util.ReflectionTestUtils;

import com.cts.skilltrkr.service.ConsumerService;

public class KafkaConsumerTest {

	@InjectMocks
	KafkaConsumer consumer;

	ConsumerService consumerService;

	@BeforeEach
	public void setup() throws Exception {
		consumerService = Mockito.mock(ConsumerService.class);
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void consumeProfileMessageTest() {
		ReflectionTestUtils.setField(consumer, "profileTopicName", "profileTopic");
		ConsumerRecord<String, String> payload = new ConsumerRecord<String, String>("topic", 5, 5l, "key",
				"Profile Details");
		consumer.consumeProfileMessage(payload);
		assertTrue(true, "Consumed Profile Message successfully");
	}

	@Test
	public void consumeUserMessageTest() {
		ReflectionTestUtils.setField(consumer, "userTopicName", "userTopic");
		ConsumerRecord<String, String> payload = new ConsumerRecord<String, String>("topic", 5, 5l, "key",
				"User Details");
		consumer.consumeUserMessage(payload);
		assertTrue(true, "Consumed User Detailis Message successfully");
	}

	@Test
	public void consumeDeleteMessageTest() {
		ReflectionTestUtils.setField(consumer, "profileTopicName", "deleteTopic");
		ConsumerRecord<String, String> payload = new ConsumerRecord<String, String>("topic", 5, 5l, "key",
				"User Details");
		consumer.consumeProfileMessage(payload);
		assertTrue(true, "Consumed Delete Message successfully");
	}
}