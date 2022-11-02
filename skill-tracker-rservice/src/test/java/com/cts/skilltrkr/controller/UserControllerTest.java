package com.cts.skilltrkr.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.cts.skilltrkr.entity.UserEntity;
import com.cts.skilltrkr.model.UserLookupResponse;
import com.cts.skilltrkr.repository.UserRepository;

public class UserControllerTest {

	@InjectMocks
	UserController controller;

	UserRepository userRepo;

	@BeforeEach
	public void setup() throws Exception {
		userRepo = Mockito.mock(UserRepository.class);

		MockitoAnnotations.initMocks(this);
	}

	//@Test
	public void validateUserTest() {
		UserEntity request = getUserRequest();
		Optional<UserEntity> value = Optional.of(request);
		when(userRepo.findById("userName")).thenReturn(value);

		ResponseEntity<UserLookupResponse> actual = controller.validateUser("userName");

		assertEquals(HttpStatus.OK, actual.getStatusCode());
	}

	//@Test
	public void validateUser_Error_1() {
		when(userRepo.findById("userName")).thenReturn(Optional.empty());

		ResponseEntity<UserLookupResponse> actual = controller.validateUser("userName");

		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, actual.getStatusCode());
	}

	//@Test
	public void validateUser_Error_2() {
		UserEntity request = getUserRequest();
		Optional<UserEntity> value = Optional.of(request);
		when(userRepo.findById("userName")).thenReturn(value);

		ResponseEntity<UserLookupResponse> actual = controller.validateUser("userName");

		assertEquals(HttpStatus.NO_CONTENT, actual.getStatusCode());
	}

	private UserEntity getUserRequest() {
		UserEntity request = new UserEntity();
		request.setFirstName("FirstName");
		request.setLastName("LastName");
		request.setEmailAddress("test@test.com");
		request.setUserName("userName");
		request.setPassword("$2a$12$2UmXfMXMm.w2SoMUDFUxZuuzcVaF01ZbYgae.fie/GHfHCmgbGDWO");
		return request;
	}
}