package com.cts.skilltrkr.dto;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = UserNotBlankValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR, ElementType.TYPE_USE, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Repeatable(UserNotBlank.List.class)
public @interface UserNotBlank {

    String message() default "{javax.validation.constraints.NotBlank.message}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    @Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR, ElementType.PARAMETER, ElementType.TYPE_USE})
    @Retention(RetentionPolicy.RUNTIME)
    @Documented
    public @interface List {
        UserNotBlank[] value();
    }
}
