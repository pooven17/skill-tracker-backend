package com.cts.skilltrkr.repositry;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cts.skilltrkr.entity.ProfileSkillEntity;

public interface ProfileSkillRepo extends JpaRepository<ProfileSkillEntity, Integer> {
}
