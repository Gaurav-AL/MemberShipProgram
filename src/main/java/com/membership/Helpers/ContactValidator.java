package com.membership.Helpers;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;



@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ContactValidatorImpl.class)
public @interface ContactValidator {
    String message() default "Invalid Contract for this Country";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
