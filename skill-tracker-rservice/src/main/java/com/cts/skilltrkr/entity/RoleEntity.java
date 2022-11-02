package com.cts.skilltrkr.entity;

import org.springframework.security.core.GrantedAuthority;

public enum RoleEntity implements GrantedAuthority {
	ROLE_USER, ROLE_MODERATOR, ROLE_ADMIN;

	@Override
	public String getAuthority() {
		return name();
	}
}
