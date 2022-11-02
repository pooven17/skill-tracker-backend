package com.cts.skilltrkr.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name="profile_skill")
public class ProfileSkillEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int skillId;

    @Column(name = "skillName")
    private String skillName;

    @Column(name = "expertiseLevel")
    private int expertiseLevel;
    
    @Column(name = "technical")
    private boolean technical;
}