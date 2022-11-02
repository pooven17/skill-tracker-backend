package com.cts.skilltrkr.controller;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRequest implements Serializable {

	private static final long serialVersionUID = 5926468583005150707L;

	@NotBlank
	private String username;

	@NotBlank
	private String password;
}
