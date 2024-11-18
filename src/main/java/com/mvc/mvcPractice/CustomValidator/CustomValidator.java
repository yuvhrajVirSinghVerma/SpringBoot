package com.mvc.mvcPractice.CustomValidator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class CustomValidator implements ConstraintValidator<CustomValidatorAnnotation,String> {

    @Override
    public boolean isValid(String str, ConstraintValidatorContext constraintValidatorContext) {
        System.out.println("custom validator"+constraintValidatorContext.toString());
        return str.contains("yuvi");
    }
}
