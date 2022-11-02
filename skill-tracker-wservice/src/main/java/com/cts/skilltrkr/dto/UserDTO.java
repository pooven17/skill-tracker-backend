package com.cts.skilltrkr.dto;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.cts.skilltrkr.entity.RoleEntity;
import com.cts.skilltrkr.entity.UserEntity;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@UserNotBlank
public class UserDTO {

	@NotBlank(message = "User Name is mandatory")
	@Size(min = 3, max = 30, message = "User Name must have within 3 -10 characters")
	private String userName;

	@NotBlank(message = "First Name is mandatory")
	@Size(min = 3, max = 30, message = "First Name must have within 5 -30 characters")
	private String firstName;

	@NotBlank(message = "Last Name is mandatory")
	@Size(min = 3, max = 30, message = "Last Name must have within 5 -30 characters")
	private String lastName;

	@NotBlank(message = "Email is mandatory")
	@Email(message = "Email should be valid")
	private String emailAddress;

	@NotBlank(message = "Password is mandatory")
	private String password;

	@NotBlank(message = "Role is mandatory")
	private String roleName;

	public UserEntity toUser() {
		return UserEntity.builder().firstName(firstName).lastName(lastName).emailAddress(emailAddress)
				.userName(userName).password(hashPassword(password)).roles(setRole(roleName)).build();
	}

	private List<RoleEntity> setRole(String roleName) {
		List<RoleEntity> roles = new ArrayList<>();
		if ("Admin".equalsIgnoreCase(roleName))
			roles.add(RoleEntity.ROLE_ADMIN);
		else if ("Moderator".equalsIgnoreCase(roleName))
			roles.add(RoleEntity.ROLE_MODERATOR);
		else
			roles.add(RoleEntity.ROLE_USER);
		return roles;
	}

	private String hashPassword(String password) {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);
		return encoder.encode(password);
	}
}