package com.cts.skilltrkr.repositry;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cts.skilltrkr.entity.SkillEntity;

public interface SkillRepo extends JpaRepository<SkillEntity, Integer> {
}
