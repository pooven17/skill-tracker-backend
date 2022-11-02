package com.cts.skilltrkr.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.cts.skilltrkr.entity.SkillEntity;

@Repository
public interface SkillRepository extends MongoRepository<SkillEntity, String> {

}