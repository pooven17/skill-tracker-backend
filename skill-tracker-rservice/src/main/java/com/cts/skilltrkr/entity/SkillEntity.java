package com.cts.skilltrkr.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(collection="Skill")
@JsonIgnoreProperties(ignoreUnknown = true)
public class SkillEntity {

	@Id
	private String skillId;

	private String skillName;

	private String skillDesc;
	
	private boolean technical;

}