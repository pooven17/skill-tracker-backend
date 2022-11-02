package com.cts.skilltrkr.config;

import java.util.Set;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.cts.skilltrkr.dto.UserDTO;
import com.cts.skilltrkr.service.SkillService;
import com.cts.skilltrkr.service.UserService;

@Configuration
public class SkillTrackerConfig {

	@Bean("skillSet")
	public Set<String> skillSet(SkillService skillService) {
		return skillService.saveSkill();
	}

	@Bean("loadDefaultData")
	public boolean loadDefaultData(UserService userService) {
		UserDTO request = new UserDTO("admin", "admin", "admin", "admin@cts.com", "admin", "admin");
		userService.createUser(request);

		return true;
	}
}
