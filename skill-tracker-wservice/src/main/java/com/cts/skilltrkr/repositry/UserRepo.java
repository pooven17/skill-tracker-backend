package com.cts.skilltrkr.repositry;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cts.skilltrkr.entity.UserEntity;

public interface UserRepo extends JpaRepository<UserEntity, String> {
}
