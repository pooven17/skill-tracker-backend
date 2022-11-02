package com.cts.skilltrkr.dto;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.cts.skilltrkr.entity.ProfileEntity;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@ProfileNotBlank
public class ProfileDTO {
	
    @NotBlank(message = "Associate Id should not be Null or Empty")
    @Size(min=5, max=30, message = "Name size should be between 5 & 30")
    private String associateId;

    @NotNull(message = "Name should not be Null or Empty")
    @Size(min=3, max=30, message = "Name size should be between 5 & 30")
    private String name;

    @Email(message = "Email Id is Empty or Null or Not in Valid Format")
    private String emailId;

    @NotBlank(message = "Contact Number should not be Null or Empty")
    @Pattern(regexp = "^\\d{10}$", message = "Contact Number should be 10 Characters")
    private String contactNo;

    @NotNull(message = "Invalid Skills List")
    @Valid
    private List<SkillDTO> skills;

    public ProfileEntity toProfile() {
        return ProfileEntity.builder().name(name).associateId(associateId).emailId(emailId).contactNo(contactNo)
                .skills(skills.stream().map(i -> i.getSkillEntity()).collect(Collectors.toList()))
                .createdDate(new Date()).build();
    }
}