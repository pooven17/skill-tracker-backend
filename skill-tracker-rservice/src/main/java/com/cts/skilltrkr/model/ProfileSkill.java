package com.cts.skilltrkr.model;

import com.cts.skilltrkr.entity.ProfileSkillEntity;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(Include.NON_NULL)
public class ProfileSkill {

    private String skillName;

    private int expertiseLevel;
    
    private boolean technical;
    
    public ProfileSkillEntity toSkill() {
        return ProfileSkillEntity.builder().skillName(skillName)
                .expertiseLevel(expertiseLevel).technical(technical).build();
    }
}
