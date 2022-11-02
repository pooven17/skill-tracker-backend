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

import com.cts.skilltrkr.dto.ProfileDTO;
import com.cts.skilltrkr.dto.ResponseDTO;
import com.cts.skilltrkr.dto.SkillDTO;
import com.cts.skilltrkr.entity.ProfileEntity;
import com.cts.skilltrkr.service.ProfileService;

public class SkillTrackerControllerTest {

	@InjectMocks
	SkillTrackerController controller;

	ProfileService profileService;

	@BeforeEach
	public void setup() throws Exception {
		profileService = Mockito.mock(ProfileService.class);

		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void createProfile() {
		ProfileDTO request = getProfileRequest();

		ResponseDTO actual = controller.createProfile(request);
		assertEquals(HttpStatus.CREATED, actual.getHttpStatus());
	}

	@Test
	public void updateProfile() {
		ProfileDTO request = getProfileRequest();

		ResponseEntity<ProfileEntity> value = new ResponseEntity<>(HttpStatus.OK);
		when(profileService.updateProfile("id", request)).thenReturn(value);

		ResponseEntity<ProfileEntity> actual = controller.updateProfile("id", request);

		assertEquals(HttpStatus.OK, actual.getStatusCode());
	}

	@Test
	public void deleteProfile() {

		ResponseDTO actual = controller.deleteProfile("id");
		assertEquals(HttpStatus.ACCEPTED, actual.getHttpStatus());
	}

	private ProfileDTO getProfileRequest() {
		ProfileDTO request = new ProfileDTO();
		request.setAssociateId("CTS12345");
		request.setEmailId("test@Test.com");
		request.setContactNo("3242334343");
		request.setName("Test");

		List<SkillDTO> skills = new ArrayList<>();
		skills.add(new SkillDTO("ANGULAR", 20, true));
		skills.add(new SkillDTO("AWS", 20, true));
		skills.add(new SkillDTO("REACT", 20, true));
		skills.add(new SkillDTO("JENKINS", 20, true));
		skills.add(new SkillDTO("SPOKEN", 20, false));
		skills.add(new SkillDTO("COMMUNICATION", 20, false));
		request.setSkills(skills);
		return request;
	}
}