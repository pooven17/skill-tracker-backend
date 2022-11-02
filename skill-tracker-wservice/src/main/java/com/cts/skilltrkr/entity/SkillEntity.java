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
@Table(name = "skill")
public class SkillEntity {

	@Id
	private String skillId;

	@Column(name = "skillName")
	private String skillName;

	@Column(name = "skillDesc")
	private String skillDesc;

	@Column(name = "technical")
	private boolean technical;

}