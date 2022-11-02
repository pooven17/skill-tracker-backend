package com.cts.skilltrkr.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.cts.skilltrkr.entity.UserEntity;

@Repository
public interface UserRepository extends MongoRepository<UserEntity, String> {
}