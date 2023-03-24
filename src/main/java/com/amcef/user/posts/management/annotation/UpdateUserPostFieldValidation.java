package com.amcef.user.posts.management.annotation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

/**
 * @author Michal Remis
 */
@Documented
@Constraint(validatedBy = UpdateUserPostFieldValidator.class)
@Target( { ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface UpdateUserPostFieldValidation {
    String message() default "Invalid input";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
