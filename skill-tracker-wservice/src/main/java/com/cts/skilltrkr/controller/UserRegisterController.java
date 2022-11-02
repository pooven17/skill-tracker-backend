package com.cts.skilltrkr.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cts.skilltrkr.dto.ResponseDTO;
import com.cts.skilltrkr.dto.UserDTO;
import com.cts.skilltrkr.service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.extern.slf4j.Slf4j;

@RequestMapping("/skill-tracker/api/v1/user")
@RestController
@Slf4j
public class UserRegisterController {

	@Autowired
	UserService userService;

	@Operation(summary = "Create User Profile, This API is used to persist User Profile")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "User Created Successfully"),
			@ApiResponse(responseCode = "400", description = "Invalid User details") })
	@PostMapping(value = "/createUser")
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	public ResponseDTO createUser(@Valid @RequestBody UserDTO request) {
		log.info("User= " + request.toString());
		userService.createUser(request);
		return new ResponseDTO("Created", HttpStatus.CREATED);
	}

	@Operation(summary = "Update User Profile, This API is used to update User details")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "User updated Successfully"),
			@ApiResponse(responseCode = "400", description = "Invalid User details") })
	@PutMapping(value = "/updateUser/{id}")
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	public ResponseDTO updateUser(@PathVariable String id, @Valid @RequestBody UserDTO request) {
		log.info("User= " + request.toString());
		userService.updateUser(id, request);
		return new ResponseDTO("Updated", HttpStatus.OK);
	}
}