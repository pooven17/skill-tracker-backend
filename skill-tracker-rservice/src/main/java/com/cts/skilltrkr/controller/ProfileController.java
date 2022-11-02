package com.cts.skilltrkr.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cts.skilltrkr.model.Profile;
import com.cts.skilltrkr.service.ProfileService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.extern.slf4j.Slf4j;

@RequestMapping("/skill-tracker/api/v1")
@RestController
@Slf4j
public class ProfileController {

	@Autowired
	ProfileService profileService;

	@Operation(summary = "Gets Skill List")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Skills fetched Successfully"), })
	@GetMapping("/skills")
	@PreAuthorize("hasAuthority('ROLE_USER') or hasAuthority('ROLE_MODERATOR') or hasAuthority('ROLE_ADMIN')")
	public ResponseEntity<Map<String, List<Map<String, String>>>> getSkills() {
		log.info("Get Skills");
		Map<String, List<Map<String, String>>> skills = profileService.getSkillOptions();
		return new ResponseEntity<>(skills, HttpStatus.OK);
	}

	@Operation(summary = "Gets Engineer Profile, This API is used to get the Engineer details and Skill details of the Engineer")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Profile fetched Successfully"),
			@ApiResponse(responseCode = "400", description = "Invalid Search details") })
	@GetMapping("/admin/{criteria}/{criteriaValue}")
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	public ResponseEntity<List<Profile>> getProfile(@PathVariable String criteria, @PathVariable String criteriaValue) {
		log.info("Criteria= {}", criteria);
		log.info("Criteria Value= {}", criteriaValue);
		List<Profile> profileList = profileService.getProfiles(criteria, criteriaValue, false);
		if (CollectionUtils.isEmpty(profileList)) {
			return new ResponseEntity<>(new ArrayList<>(), HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<>(profileList, HttpStatus.OK);
	}

	@Operation(summary = "Gets Engineer Profile, This API is used to get the Engineer details and Skill details of the Engineer")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Profile fetched Successfully"),
			@ApiResponse(responseCode = "400", description = "Invalid Search details") })
	@GetMapping("/engineer/get-profile/{userName}")
	@PreAuthorize("hasAuthority('ROLE_USER') or hasAuthority('ROLE_MODERATOR') or hasAuthority('ROLE_ADMIN')")
	public ResponseEntity<List<Profile>> getProfile(@PathVariable String userName, @RequestParam boolean forEditing) {
		log.info("Criteria= Id");
		log.info("Criteria Value= {}", userName);
		if (asPermission(userName)) {
			List<Profile> profileList = profileService.getProfiles("Id", userName, forEditing);
			if (CollectionUtils.isEmpty(profileList)) {
				return new ResponseEntity<>(new ArrayList<>(), HttpStatus.NO_CONTENT);
			}
			return new ResponseEntity<>(profileList, HttpStatus.OK);
		}
		return new ResponseEntity<>(null, HttpStatus.FORBIDDEN);
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