package com.cts.skilltrkr.repositry;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cts.skilltrkr.entity.ProfileEntity;

public interface ProfileRepo extends JpaRepository<ProfileEntity, String> {
}
