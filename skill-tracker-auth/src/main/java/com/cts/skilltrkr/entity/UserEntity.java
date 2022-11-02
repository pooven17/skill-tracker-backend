package com.cts.skilltrkr.entity;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(collection = "User")
public class UserEntity {

	@Id
	private String username;

	private String firstname;

	private String lastname;

	private String emailAddress;

	private String password;
	
	private List<RoleEntity> roles;
}