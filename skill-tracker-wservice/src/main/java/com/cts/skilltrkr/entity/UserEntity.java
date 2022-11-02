package com.cts.skilltrkr.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "user")
public class UserEntity {

	@Id
	private String userName;

	@Column(name = "firstName")
	private String firstName;

	@Column(name = "lastName")
	private String lastName;

	@Column(name = "emailAddress")
	private String emailAddress;

	@Column(name = "password")
	private String password;
	
	@ElementCollection 
	private List<RoleEntity> roles;
}