package com.amcef.user.posts.management.annotation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Optional;


/**
 * @author Michal Remis
 */
public class UpdateUserPostFieldValidator implements ConstraintValidator<UpdateUserPostFieldValidation, String> {

    @Override
    public void initialize(UpdateUserPostFieldValidation credentials) {
    }

    // allow nulls (handled in service layer) but don't allow empty values
    @Override
    public boolean isValid(String str, ConstraintValidatorContext constraintValidatorContext) {
        return str == null || !str.isBlank();
    }
}
