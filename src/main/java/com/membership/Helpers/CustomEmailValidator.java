package com.membership.Helpers;

import java.util.regex.Pattern;

import com.membership.dto.CreateUserRequest;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class CustomEmailValidator implements ConstraintValidator<EmailValidator , CreateUserRequest>{

    private static final String EMAIL_REGEX =
            "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";

    private static final Pattern EMAIL_PATTERN =
            Pattern.compile(EMAIL_REGEX);
    
    @Override
    public boolean isValid(CreateUserRequest userRequest, ConstraintValidatorContext context) {
        String email = userRequest.getEmail();
        if(email == null) return false;
        return EMAIL_PATTERN.matcher(email).matches();
    }
    
}
