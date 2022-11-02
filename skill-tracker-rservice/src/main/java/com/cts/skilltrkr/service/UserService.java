package com.cts.skilltrkr.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.cts.skilltrkr.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service(value = "userService")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class UserService implements UserDetailsService {

	private final UserRepository authUserRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		var user = authUserRepository.findById(username)
				.orElseThrow(() -> new UsernameNotFoundException("Incorrect Username / Password supplied!"));

		return org.springframework.security.core.userdetails.User.withUsername(username).password(user.getPassword())
				.authorities(user.getRoles()).accountExpired(false).accountLocked(false).credentialsExpired(false)
				.disabled(false).build();
	}
}
