package com.cts.skilltrkr.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.cts.skilltrkr.dto.ProfileDTO;
import com.cts.skilltrkr.dto.SkillDTO;
import com.cts.skilltrkr.entity.ProfileEntity;
import com.cts.skilltrkr.entity.ProfileSkillEntity;
import com.cts.skilltrkr.exception.NotValidUpdateException;
import com.cts.skilltrkr.exception.ProfileNotFoundException;
import com.cts.skilltrkr.kafka.KafkaProducer;
import com.cts.skilltrkr.repositry.ProfileRepo;

public class ProfileServiceTest {

	@InjectMocks
	ProfileService service;

	ProfileRepo profileRepo;

	KafkaProducer kafkaProducer;

	@BeforeEach
	public void setup() throws Exception {
		profileRepo = Mockito.mock(ProfileRepo.class);
		kafkaProducer = Mockito.mock(KafkaProducer.class);

		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void createProfileTest() {
		ProfileDTO request = getProfileRequest();

		ProfileEntity expected = new ProfileEntity();
		expected.setAssociateId("CTS12345");
		when(profileRepo.save(ArgumentMatchers.any())).thenReturn(expected);
		service.createProfile(request);
		assertTrue(true, "Saved successfully");
	}

	@Test
	public void updateProfileTest_1() {
		ProfileDTO request = getProfileRequest();

		when(profileRepo.findById("CTS12345")).thenReturn(Optional.empty());
	
		ProfileNotFoundException exception = assertThrows(ProfileNotFoundException.class, () -> {
			service.updateProfile("CTS12345", request);
		});

		assertEquals("Not Found Profile for the Id:CTS12345", exception.getMessage());
	}

	//@Test
	public void updateProfileTest_2() {
		ProfileDTO request = getProfileRequest();

		ProfileEntity entity = getProfileEntity();
		when(profileRepo.findById("CTS12345")).thenReturn(Optional.of(entity));

		NotValidUpdateException exception = assertThrows(NotValidUpdateException.class, () -> {
			service.updateProfile("CTS12345", request);
		});

		assertEquals("Not Eligible for update the Skills", exception.getMessage());
	}

	@Test
	public void updateProfileTest_3() {
		ProfileDTO request = getProfileRequest();

		ProfileEntity entity = getProfileEntity();
		Calendar updatedDate = Calendar.getInstance();
		updatedDate.add(Calendar.DATE, -12);
		entity.setUpdatedDate(updatedDate.getTime());

		ProfileEntity expected = new ProfileEntity();
		expected.setAssociateId("CTS12345");

		when(profileRepo.findById("CTS12345")).thenReturn(Optional.of(entity));
		when(profileRepo.save(ArgumentMatchers.any())).thenReturn(expected);

		ResponseEntity<ProfileEntity> actual = service.updateProfile("CTS12345", request);

		assertEquals(HttpStatus.OK, actual.getStatusCode());
	}

	@Test
	public void deleteProfileTest_1() {
		when(profileRepo.findById("CTS12345")).thenReturn(Optional.empty());

		ProfileNotFoundException exception = assertThrows(ProfileNotFoundException.class, () -> {
			service.deleteProfile("CTS12345");
		});

		assertEquals("Not Found Profile for the Id:CTS12345", exception.getMessage());
	}

	@Test
	public void deleteProfileTest_2() {
		ProfileEntity entity = getProfileEntity();
		Calendar updatedDate = Calendar.getInstance();
		updatedDate.add(Calendar.DATE, -12);
		entity.setUpdatedDate(updatedDate.getTime());

		when(profileRepo.findById("CTS12345")).thenReturn(Optional.of(entity));

		service.deleteProfile("CTS12345");

		assertTrue(true, "Profile deleted successfully");
	}

	private ProfileEntity getProfileEntity() {
		ProfileEntity entity = new ProfileEntity();
		entity.setAssociateId("CTS12345");
		entity.setCreatedDate(Calendar.getInstance().getTime());

		List<ProfileSkillEntity> skills = new ArrayList<>();
		skills.add(new ProfileSkillEntity(1, "ANGULAR", 20, true));
		skills.add(new ProfileSkillEntity(2, "AWS", 20, true));
		skills.add(new ProfileSkillEntity(3, "SPOKEN", 20, true));
		entity.setSkills(skills);
		return entity;
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