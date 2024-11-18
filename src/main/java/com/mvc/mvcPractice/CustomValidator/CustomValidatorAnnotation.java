package com.mvc.mvcPractice.CustomValidator;


import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import jakarta.validation.constraints.Email;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(value = ElementType.FIELD)
@Retention(value = RetentionPolicy.RUNTIME)
@Constraint(validatedBy = CustomValidator.class)
public @interface CustomValidatorAnnotation {
    String message() default "String must contain yuvi";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
