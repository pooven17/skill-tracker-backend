package com.cts.skilltrkr.dto;

import java.util.Set;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.util.CollectionUtils;

public class ProfileNotBlankValidator implements ConstraintValidator<ProfileNotBlank, ProfileDTO> {

    @Autowired
    @Qualifier("skillSet")
    public Set<String> skillSet;

    @Override
    public boolean isValid(ProfileDTO profileRequest, ConstraintValidatorContext context) {

        if (StringUtils.isNotBlank(profileRequest.getAssociateId()) && !profileRequest.getAssociateId().startsWith("CTS")) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("Associate Id should start with CTS").addPropertyNode("associateId").addConstraintViolation();
            return false;
        }

        if (!CollectionUtils.isEmpty(profileRequest.getSkills())) {
            for (SkillDTO skill : profileRequest.getSkills()) {
                if (StringUtils.isNotBlank(skill.getSkillName()) && !skillSet.contains(skill.getSkillName())) {
                    context.disableDefaultConstraintViolation();
                    context.buildConstraintViolationWithTemplate(skill.getSkillName() + " is not matched with available Technical Skills").addConstraintViolation();
                    return false;
                }
            }
        }
        return true;
    }
}
