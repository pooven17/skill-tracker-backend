package com.cts.skilltrkr.controller;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.cts.skilltrkr.service.JwtTokenUtil;

@RestController
@CrossOrigin
public class AuthorizationController {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@PostMapping("/skill-tracker/signin")
	public ResponseEntity<AuthResponse> authenticateUser(@Valid @RequestBody UserRequest loginRequest) {

		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

		SecurityContextHolder.getContext().setAuthentication(authentication);
		User userDetails = (User) authentication.getPrincipal();
		String jwt = jwtTokenUtil.generateToken(userDetails);

		List<String> roles = userDetails.getAuthorities().stream().map(item -> item.getAuthority())
				.collect(Collectors.toList());
		AuthResponse resp = AuthResponse.builder().token(jwt).username(userDetails.getUsername()).roles(roles).build();
		return ResponseEntity.ok(resp);
	}
}
