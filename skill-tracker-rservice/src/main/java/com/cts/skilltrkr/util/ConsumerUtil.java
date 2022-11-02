package com.cts.skilltrkr.util;

import java.util.List;

import org.springframework.stereotype.Service;

import com.cts.skilltrkr.entity.SkillEntity;
import com.cts.skilltrkr.model.Profile;
import com.cts.skilltrkr.model.User;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ConsumerUtil {

	public static Profile convertProfileJsontoObject(String msg) {

		ObjectMapper mapper = new ObjectMapper();
		try {
			Profile profile = mapper.readValue(msg, Profile.class);
			return profile;
		} catch (Exception ex) {
			log.error("Not able to convert to Object :{}", ex);
		}
		return null;
	}

	public static User convertUserJsontoObject(String message) {
		ObjectMapper mapper = new ObjectMapper();
		try {
			User user = mapper.readValue(message, User.class);
			return user;
		} catch (Exception ex) {
			log.error("Not able to convert to Object :{}", ex);
		}
		return null;
	}

	public static List<SkillEntity> convertSkillListJsontoObject(String message) {
		ObjectMapper mapper = new ObjectMapper();
		try {
			return mapper.readValue(message, new TypeReference<List<SkillEntity>>() {
			});
		} catch (Exception ex) {
			log.error("Not able to convert to Object :{}", ex);
		}
		return null;
	}
}