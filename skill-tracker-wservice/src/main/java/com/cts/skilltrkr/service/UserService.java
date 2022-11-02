package com.cts.skilltrkr.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.cts.skilltrkr.dto.UserDTO;
import com.cts.skilltrkr.entity.UserEntity;
import com.cts.skilltrkr.exception.ProfileNotFoundException;
import com.cts.skilltrkr.kafka.KafkaProducer;
import com.cts.skilltrkr.repositry.UserRepo;
import com.cts.skilltrkr.util.ProfileUtil;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service(value = "userService")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@Slf4j
public class UserService implements UserDetailsService {

	@Autowired
	UserRepo userRepo;

	@Autowired
	KafkaProducer kafkaProducer;
	
	@Override
	@Transactional
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		var user = userRepo.findById(username)
				.orElseThrow(() -> new UsernameNotFoundException("Incorrect Username / Password supplied!"));

		return org.springframework.security.core.userdetails.User.withUsername(username).password(user.getPassword())
				.authorities(user.getRoles()).accountExpired(false).accountLocked(false).credentialsExpired(false)
				.disabled(false).build();
	}

	@Transactional
	public void createUser(UserDTO request) {
		log.info("User Creating");
		UserEntity user = userRepo.save(request.toUser());
		log.info("User Created");
		kafkaProducer.sendUser(ProfileUtil.convertObjectToJson(user));
		log.info("Sent to Kafka");
	}

	@Transactional
	public void updateUser(String id, UserDTO request) {
		log.info("Updating User");
		UserEntity user = userRepo.findById(id)
				.orElseThrow(() -> new ProfileNotFoundException("Not Found User for this UserName:" + id));

		UserEntity updatedUser = request.toUser();
		updatedUser.setUserName(user.getUserName());
		log.info("User Updated");
		kafkaProducer.sendUser(ProfileUtil.convertObjectToJson(updatedUser));
		log.info("Sent to Kafka");
	}
}
