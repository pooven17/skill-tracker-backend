package com.cts.skilltrkr.controller;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuthResponse {

	private String token;
	private String username;
	private String email;
	private List<String> roles;

}
