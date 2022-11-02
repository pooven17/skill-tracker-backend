package com.cts.skilltrkr.model;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import com.cts.skilltrkr.entity.ProfileEntity;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Profile {

	private String name;

	private String associateId;

	private String emailId;

	private String contactNo;

	private Date createdDate;

	private Date updatedDate;

	private List<ProfileSkill> skills;

	private List<ProfileSkill> technicalSkills;

	private List<ProfileSkill> nonTechnicalSkills;

	private boolean toDelete;

	public ProfileEntity toProfile() {
		if(toDelete) {
			return ProfileEntity.builder().associateId(associateId).build();
		}
		return ProfileEntity.builder().associateId(associateId).name(name).emailId(emailId).contactNo(contactNo)
				.skills(skills.stream().map(i -> i.toSkill()).collect(Collectors.toList())).createdDate(createdDate)
				.updatedDate(updatedDate).build();
	}
	
	
}
