package com.cts.skilltrkr.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;

import com.cts.skilltrkr.dto.ResponseDTO;
import com.cts.skilltrkr.dto.UserDTO;
import com.cts.skilltrkr.service.UserService;

public class UserRegisterControllerTest {

	@InjectMocks
	UserRegisterController controller;

	UserService userService;

	@BeforeEach
	public void setup() throws Exception {
		userService = Mockito.mock(UserService.class);

		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void createUserTest() {
		UserDTO request = getUserRequest();

		ResponseDTO actual = controller.createUser(request);

		assertEquals(HttpStatus.CREATED, actual.getHttpStatus());
	}

	@Test
	public void updateUserTest() {
		UserDTO request = getUserRequest();

		ResponseDTO actual = controller.updateUser("testUser", request);

		assertEquals(HttpStatus.OK, actual.getHttpStatus());
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