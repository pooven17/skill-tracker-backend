package com.cts.skilltrkr.model;

import java.io.Serializable;
import java.util.List;

import com.cts.skilltrkr.entity.RoleEntity;
import com.cts.skilltrkr.entity.UserEntity;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(Include.NON_NULL)
public class User implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String firstName;

	private String lastName;

	private String emailAddress;

	private String userName;

	private String password;
	
	private List<RoleEntity> roles;

	public UserEntity toUser() {
		return UserEntity.builder().userName(userName).firstName(firstName).lastName(lastName)
				.emailAddress(emailAddress).password(password).roles(roles).build();
	}
}