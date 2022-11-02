package com.cts.skilltrkr.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.cts.skilltrkr.entity.UserEntity;
import com.cts.skilltrkr.exception.ProfileNotFoundException;
import com.cts.skilltrkr.model.UserLookupResponse;
import com.cts.skilltrkr.repository.UserRepository;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class UserController {

	@Autowired
	UserRepository userRepo;

	@Operation(summary = "Get User details")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Logged in Successfully"),
			@ApiResponse(responseCode = "400", description = "Invalid Login details") })
	@GetMapping("/skill-tracker/api/v1/userinfo/{userName}")
	@PreAuthorize("hasAuthority('ROLE_USER') or hasAuthority('ROLE_MODERATOR') or hasAuthority('ROLE_ADMIN')")
	public ResponseEntity<UserLookupResponse> validateUser(@PathVariable String userName) {
		log.info("userName: {}", userName);
		try {
			if (asPermission(userName)) {
				UserEntity userEntity = userRepo.findById(userName)
						.orElseThrow(() -> new ProfileNotFoundException("Not Found UserName for the Id:" + userName));

				userEntity.setPassword(null);
				var response = UserLookupResponse.builder().users(userEntity).message("Successfully returned User(s)!")
						.build();
				return new ResponseEntity<>(response, HttpStatus.OK);
			}

			var response = UserLookupResponse.builder().message("No Permission to access the User(s)!").build();
			return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);

		} catch (Exception e) {
			var safeErrorMessage = "Failed to complete get all users request";
			log.info(e.toString());
			return new ResponseEntity<>(new UserLookupResponse(null, safeErrorMessage),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	private boolean asPermission(String userName) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication != null) {
			String currentPrincipalName = authentication.getName();
			if (currentPrincipalName.equals(userName)) {
				return true;
			} else if (authentication.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))) {
				return true;
			}
		}
		return false;
	}
}