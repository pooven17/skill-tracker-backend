package com.cts.skilltrkr.dto;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = ProfileNotBlankValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR, ElementType.TYPE_USE, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Repeatable(ProfileNotBlank.List.class)
public @interface ProfileNotBlank {

    String message() default "{javax.validation.constraints.NotBlank.message}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    @Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR, ElementType.PARAMETER, ElementType.TYPE_USE})
    @Retention(RetentionPolicy.RUNTIME)
    @Documented
    public @interface List {
        ProfileNotBlank[] value();
    }
}
