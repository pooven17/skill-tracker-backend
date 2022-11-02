package com.cts.skilltrkr.dto;

import com.cts.skilltrkr.entity.ProfileSkillEntity;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SkillDTO {

    @NotBlank(message = "Skill Name should not be Null or Empty")
    private String skillName;

    @Min(value = 1, message = "Expertise Level minimum 1")
    @Max(value = 20, message = "Expertise Level maximum 20")
    private int expertiseLevel;
    
    private boolean technical;

    public ProfileSkillEntity getSkillEntity() {
        return ProfileSkillEntity.builder().skillName(skillName).expertiseLevel(expertiseLevel).technical(technical).build();
    }
}
