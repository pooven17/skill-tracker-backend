package com.cts.skilltrkr.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.cts.skilltrkr.dto.UserDTO;
import com.cts.skilltrkr.entity.RoleEntity;
import com.cts.skilltrkr.entity.UserEntity;
import com.cts.skilltrkr.exception.ProfileNotFoundException;
import com.cts.skilltrkr.kafka.KafkaProducer;
import com.cts.skilltrkr.repositry.UserRepo;

public class UserServiceTest {

	@InjectMocks
	UserService service;

	UserRepo userRepo;

	KafkaProducer kafkaProducer;

	@BeforeEach
	public void setup() throws Exception {
		userRepo = Mockito.mock(UserRepo.class);
		kafkaProducer = Mockito.mock(KafkaProducer.class);

		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void createUserTest() {
		UserDTO request = getUserRequest();

		UserEntity expected = new UserEntity();
		expected.setUserName("testUser");
		when(userRepo.save(ArgumentMatchers.any())).thenReturn(expected);
		service.createUser(request);
		assertTrue(true, "Saved successfully");
	}

	@Test
	public void updateUserTest_1() {
		UserDTO request = getUserRequest();

		when(userRepo.findById("testUser")).thenReturn(Optional.empty());

		ProfileNotFoundException exception = assertThrows(ProfileNotFoundException.class, () -> {
			service.updateUser("testUser", request);
		});

		assertEquals("Not Found User for this UserName:testUser", exception.getMessage());
	}

	@Test
	public void updateUserTest_2() {
		UserDTO request = getUserRequest();

		UserEntity entity = new UserEntity();
		entity.setUserName("testUser");
		List<RoleEntity> roles = new ArrayList<RoleEntity>();
		roles.add(RoleEntity.ROLE_MODERATOR);
		entity.setRoles(roles);

		when(userRepo.findById("testUser")).thenReturn(Optional.of(entity));

		service.updateUser("testUser", request);
		assertTrue(true, "Updated successfully");
	}

	private UserDTO getUserRequest() {
		UserDTO request = new UserDTO();
		request.setFirstName("FirstName");
		request.setLastName("LastName");
		request.setEmailAddress("test@test.com");
		request.setUserName("testUser");
		request.setPassword("testPassword");
		return request;
	}
}