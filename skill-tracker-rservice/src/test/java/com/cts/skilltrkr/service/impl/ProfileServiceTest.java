package com.cts.skilltrkr.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.test.util.ReflectionTestUtils;

import com.cts.skilltrkr.entity.ProfileEntity;
import com.cts.skilltrkr.entity.ProfileSkillEntity;
import com.cts.skilltrkr.exception.ProfileNotFoundException;
import com.cts.skilltrkr.model.Profile;
import com.cts.skilltrkr.repository.ProfileRepository;
import com.cts.skilltrkr.service.ProfileService;

public class ProfileServiceTest {

	@InjectMocks
	ProfileService service;

	ProfileRepository profileRepo;

	Set<String> technicalSkillSet;

	Set<String> nonTechnicalSkillSet;

	@BeforeEach
	public void setup() throws Exception {
		profileRepo = Mockito.mock(ProfileRepository.class);

		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void getProfiles_Error_1() {
		IllegalStateException exception = assertThrows(IllegalStateException.class, () -> {
			service.getProfiles("Id", "", false);
		});

		assertEquals("Criteria Value should be Non Null", exception.getMessage());
	}

	@Test
	public void getProfiles_Error_2() {
		IllegalStateException exception = assertThrows(IllegalStateException.class, () -> {
			service.getProfiles("ContactNo", "12345", false);
		});

		assertEquals("Unexpected Criteria: ContactNo", exception.getMessage());
	}

	@Test
	public void getProfiles_Id_Error() {

		when(profileRepo.findById("CTS12345")).thenReturn(Optional.empty());

		ProfileNotFoundException exception = assertThrows(ProfileNotFoundException.class, () -> {
			service.getProfiles("Id", "CTS12345", false);
		});

		assertEquals("Not Found Profile for the Id:CTS12345", exception.getMessage());
	}

	@Test
	public void getProfiles_Name_Error() {

		when(profileRepo.findByNameLike("Test")).thenReturn(null);

		ProfileNotFoundException exception = assertThrows(ProfileNotFoundException.class, () -> {
			service.getProfiles("Name", "Test", false);
		});

		assertEquals("Profile Not Found the requested parameter", exception.getMessage());
	}

	@Test
	public void getProfiles_Skill_Error() {

		when(profileRepo.findAll()).thenReturn(null);

		ProfileNotFoundException exception = assertThrows(ProfileNotFoundException.class, () -> {
			service.getProfiles("Skill", "AWS", false);
		});

		assertEquals("Profile Not Found the requested parameter", exception.getMessage());
	}

	//@Test
	public void getProfiles_Id_Error_1() {

		ReflectionTestUtils.setField(service, "technicalSkillSet", getTechnicalSkill());
		ReflectionTestUtils.setField(service, "nonTechnicalSkillSet", getNonTechnicalSkill());

		ProfileEntity entity = getProfileEntity(false);
		when(profileRepo.findById("CTS12345")).thenReturn(Optional.of(entity));

		ProfileNotFoundException exception = assertThrows(ProfileNotFoundException.class, () -> {
			service.getProfiles("Id", "CTS12345", false);
		});

		assertEquals("Profile Not Found the requested parameter", exception.getMessage());
	}

	//@Test
	public void getProfiles_Id() {

		ReflectionTestUtils.setField(service, "technicalSkillSet", getTechnicalSkill());
		ReflectionTestUtils.setField(service, "nonTechnicalSkillSet", getNonTechnicalSkill());

		ProfileEntity entity = getProfileEntity(true);
		when(profileRepo.findById("CTS12345")).thenReturn(Optional.of(entity));

		List<Profile> actual = service.getProfiles("Id", "CTS12345", false);

		assertEquals("CTS12345", actual.get(0).getAssociateId());
	}

	//@Test
	public void getProfiles_Name() {

		ReflectionTestUtils.setField(service, "technicalSkillSet", getTechnicalSkill());
		ReflectionTestUtils.setField(service, "nonTechnicalSkillSet", getNonTechnicalSkill());

		ProfileEntity entity = getProfileEntity(true);
		List<ProfileEntity> list = new ArrayList<>();
		list.add(entity);
		when(profileRepo.findByNameLike("Test")).thenReturn(list);

		List<Profile> actual = service.getProfiles("Name", "Test", false);

		assertEquals("CTS12345", actual.get(0).getAssociateId());
	}

	//@Test
	public void getProfiles_Skill() {

		ReflectionTestUtils.setField(service, "technicalSkillSet", getTechnicalSkill());
		ReflectionTestUtils.setField(service, "nonTechnicalSkillSet", getNonTechnicalSkill());

		ProfileEntity entity = getProfileEntity(true);
		List<ProfileEntity> list = new ArrayList<>();
		list.add(entity);
		when(profileRepo.findAll()).thenReturn(list);

		List<Profile> actual = service.getProfiles("Skill", "AWS", false);

		assertEquals("CTS12345", actual.get(0).getAssociateId());
	}

	private Set<String> getNonTechnicalSkill() {
		Set<String> unmodifiableSkillSet = Collections
				.unmodifiableSet(new HashSet<>(Arrays.asList("SPOKEN", "COMMUNICATION", "APTITUDE")));
		return unmodifiableSkillSet;
	}

	private Set<String> getTechnicalSkill() {
		Set<String> unmodifiableSkillSet = Collections
				.unmodifiableSet(new HashSet<>(Arrays.asList("HTML-CSS-JAVASCRIPT", "ANGULAR", "REACT", "SPRING",
						"RESTFUL", "HIBERNATE", "GIT", "DOCKER", "JENKINS", "AWS")));
		return unmodifiableSkillSet;
	}

	private ProfileEntity getProfileEntity(boolean flag) {
		ProfileEntity entity = new ProfileEntity();
		entity.setAssociateId("CTS12345");
		entity.setName("Test");
		entity.setCreatedDate(Calendar.getInstance().getTime());

		List<ProfileSkillEntity> skills = new ArrayList<>();
		if (flag) {
			skills.add(new ProfileSkillEntity("ANGULAR", 12, true));
			skills.add(new ProfileSkillEntity("AWS", 19, true));
			skills.add(new ProfileSkillEntity("JENKINS", 13, true));
			skills.add(new ProfileSkillEntity("SPOKEN", 10, false));
			skills.add(new ProfileSkillEntity("COMMUNICATION", 15, false));
		}
		entity.setSkills(skills);
		return entity;
	}
}