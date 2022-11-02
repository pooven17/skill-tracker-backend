package com.cts.skilltrkr.dto;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.apache.commons.lang3.StringUtils;

public class UserNotBlankValidator implements ConstraintValidator<UserNotBlank, UserDTO> {

    @Override
    public boolean isValid(UserDTO userRequest, ConstraintValidatorContext context) {
        if (StringUtils.isNotBlank(userRequest.getUserName()) && !userRequest.getUserName().startsWith("CTS")) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("User Id should start with CTS").addPropertyNode("userName").addConstraintViolation();
            return false;
        }
        return true;
    }
}
