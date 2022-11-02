package com.cts.skilltrkr.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.cts.skilltrkr.model.Profile;
import com.cts.skilltrkr.model.ProfileSkill;
import com.cts.skilltrkr.service.ProfileService;

public class ProfileControllerTest {

	@InjectMocks
	ProfileController controller;

	ProfileService lookUpService;

	@BeforeEach
	public void setup() throws Exception {
		lookUpService = Mockito.mock(ProfileService.class);

		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void getProfile() {
		Profile profile = getProfileRequest();
		List<Profile> list = new ArrayList<>();
		list.add(profile);
		when(lookUpService.getProfiles("Id", "CTS12345", false)).thenReturn(list);

		ResponseEntity<List<Profile>> actual = controller.getProfile("Id", "CTS12345");

		assertEquals(HttpStatus.OK, actual.getStatusCode());
	}

	@Test
	public void getProfile_Error() {

		when(lookUpService.getProfiles("Id", "CTS12345", false)).thenReturn(null);

		ResponseEntity<List<Profile>> actual = controller.getProfile("Id", "CTS12345");

		assertEquals(HttpStatus.NO_CONTENT, actual.getStatusCode());
	}

	private Profile getProfileRequest() {
		Profile request = new Profile();
		request.setAssociateId("CTS12345");
		request.setEmailId("test@Test.com");
		request.setContactNo("3242334343");
		request.setName("Test");

		List<ProfileSkill> skills = new ArrayList<>();
		skills.add(new ProfileSkill("ANGULAR", 20, true));
		skills.add(new ProfileSkill("AWS", 20, true));
		skills.add(new ProfileSkill("REACT", 20, true));
		skills.add(new ProfileSkill("JENKINS", 20, true));
		skills.add(new ProfileSkill("SPOKEN", 20, false));
		skills.add(new ProfileSkill("COMMUNICATION", 20, false));
		request.setTechnicalSkills(skills);
		return request;
	}
}