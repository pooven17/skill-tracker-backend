package com.cts.skilltrkr.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cts.skilltrkr.dto.ProfileDTO;
import com.cts.skilltrkr.dto.ResponseDTO;
import com.cts.skilltrkr.entity.ProfileEntity;
import com.cts.skilltrkr.service.ProfileService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.extern.slf4j.Slf4j;

@RequestMapping("/skill-tracker/api/v1")
@RestController
@Slf4j
public class SkillTrackerController {

	@Autowired
	ProfileService profileService;

	@Operation(description = "Create Profile, This API is used to persist Profile with Skill details to Database")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Profile Created Successfully"),
			@ApiResponse(responseCode = "400", description = "Invalid Profile details") })
	@PostMapping(value = "/engineer/add-profile")
	@PreAuthorize("hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_MODERATOR') or hasAuthority('ROLE_USER')")
	public ResponseDTO createProfile(@Valid @RequestBody ProfileDTO request) {
		log.info("Profile= " + request.toString());
		profileService.createProfile(request);
		return new ResponseDTO("Created", HttpStatus.CREATED);
	}

	@Operation(description = "Update Profile, This API is used to update Skill details of the Associate")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Profile updated Successfully"),
			@ApiResponse(responseCode = "400", description = "Invalid Profile details") })
	@PutMapping(value = "/engineer/update-profile/{id}")
	@PreAuthorize("hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_MODERATOR') or hasAuthority('ROLE_USER')")
	public ResponseEntity<ProfileEntity> updateProfile(@PathVariable String id,
			@Valid @RequestBody ProfileDTO request) {
		log.info("Profile= " + request.toString());
		return profileService.updateProfile(id, request);
	}

	@Operation(description = "Delete Engineer Profile, This API is used to delete Skill details of the Associate")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Profile deleted Successfully"),
			@ApiResponse(responseCode = "400", description = "Invalid Profile details") })
	@DeleteMapping(value = "/engineer/delete-profile/{id}")
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	public ResponseDTO deleteProfile(@PathVariable String id) {
		log.info("Profile Id= " + id);
		profileService.deleteProfile(id);
		return new ResponseDTO("Deleted", HttpStatus.ACCEPTED);
	}
}