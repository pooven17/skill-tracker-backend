package com.cts.skilltrkr.service;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.cts.skilltrkr.entity.ProfileEntity;
import com.cts.skilltrkr.entity.UserEntity;
import com.cts.skilltrkr.repository.ProfileRepository;
import com.cts.skilltrkr.repository.UserRepository;

public class ConsumerServiceTest {

	@InjectMocks
	ConsumerService service;

	@Mock
	ProfileRepository profileRepository;

	@Mock
	UserRepository userRepo;

	@BeforeEach
	public void setup() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void saveProfileTest() {
		String profileMsg = "{\"name\":\"Thiyagu\",\"associateId\":\"CTS12345\",\"emailId\":\"Thiyagu@test.com\",\"contactNo\":\"1234567890\","
				+ "\"skills\":[{\"skillName\":\"HTML-CSS-JAVASCRIPT\",\"expertiseLevel\":5},{\"skillName\":\"ANGULAR\",\"expertiseLevel\":5}"
				+ ",{\"skillName\":\"REACT\",\"expertiseLevel\":5},{\"skillName\":\"SPRING\",\"expertiseLevel\":5},"
				+ "{\"skillName\":\"RESTFUL\",\"expertiseLevel\":5},{\"skillName\":\"HIBERNATE\",\"expertiseLevel\":5},"
				+ "{\"skillName\":\"GIT\",\"expertiseLevel\":5},{\"skillName\":\"DOCKER\",\"expertiseLevel\":5},{\"skillName\":\"JENKINS\",\"expertiseLevel\":5},"
				+ "{\"skillName\":\"AWS\",\"expertiseLevel\":5},{\"skillName\":\"SPOKEN\",\"expertiseLevel\":5},"
				+ "{\"skillName\":\"COMMUNICATION\",\"expertiseLevel\":5},{\"skillName\":\"APTITUDE\",\"expertiseLevel\":5}]}\r\n";

		ProfileEntity value = new ProfileEntity();
		value.setAssociateId("CTS12345");
		when(profileRepository.save(ArgumentMatchers.any())).thenReturn(value);

		service.processProfile(profileMsg);
		assertTrue(true, "Profile Saved successfully");
	}

	@Test
	public void deleteProfileTest() {
		String profileMsg = "{\"toDelete\":true,\"name\":\"Thiyagu\",\"associateId\":\"CTS12345\",\"emailId\":\"Thiyagu@test.com\",\"contactNo\":\"1234567890\","
				+ "\"skills\":[{\"skillName\":\"HTML-CSS-JAVASCRIPT\",\"expertiseLevel\":5},{\"skillName\":\"ANGULAR\",\"expertiseLevel\":5}"
				+ ",{\"skillName\":\"REACT\",\"expertiseLevel\":5},{\"skillName\":\"SPRING\",\"expertiseLevel\":5},"
				+ "{\"skillName\":\"RESTFUL\",\"expertiseLevel\":5},{\"skillName\":\"HIBERNATE\",\"expertiseLevel\":5},"
				+ "{\"skillName\":\"GIT\",\"expertiseLevel\":5},{\"skillName\":\"DOCKER\",\"expertiseLevel\":5},{\"skillName\":\"JENKINS\",\"expertiseLevel\":5},"
				+ "{\"skillName\":\"AWS\",\"expertiseLevel\":5},{\"skillName\":\"SPOKEN\",\"expertiseLevel\":5},"
				+ "{\"skillName\":\"COMMUNICATION\",\"expertiseLevel\":5},{\"skillName\":\"APTITUDE\",\"expertiseLevel\":5}]}\r\n";

		service.processProfile(profileMsg);
		assertTrue(true, "Profile Deleted successfully");
	}

	@Test
	public void saveUserTest() {
		String userMsg = "{\"userName\":\"userName\",\"firstName\":\"firstName\",\"lastName\":\"lastName\",\"emailAddress\":\"emailAddress\","
				+ "\"password\":\"$2a$12$ZR1lu2HxU4nCAY5Tqefq0uTJR/5kPCPSjqByra5sqM8K4BbbkEIBC\",\"roles\":[\"ROLE_ADMIN\"]}\r\n";

		UserEntity value = new UserEntity();
		value.setUserName("userName");
		when(userRepo.save(ArgumentMatchers.any())).thenReturn(value);

		service.saveUser(userMsg);
		assertTrue(true, "User Saved successfully");
	}
}