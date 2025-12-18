package com.membership.Helpers;

import java.util.Map;
import java.util.regex.Pattern;

import com.membership.dto.CreateUserRequest;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ContactValidatorImpl implements ConstraintValidator<ContactValidator , CreateUserRequest> {

    private static Map<String , Pattern> COUNTRY_CODE_PATTERN = 
    Map.of(
        "IN" , Pattern.compile("^(\\+91|0)?[6-9][0-9]{9}$"),
        "US" , Pattern.compile("^\\+1[2-9][0-9]{9}$"));


    @Override
    public boolean isValid(CreateUserRequest userRequest, ConstraintValidatorContext context) {
        String countryCode = userRequest.getCountry();
        
        if(countryCode == null || !COUNTRY_CODE_PATTERN.containsKey(countryCode)) return false;

        Pattern countryCodePattern = COUNTRY_CODE_PATTERN.get(countryCode);

        return countryCodePattern.matcher(userRequest.getContact()).matches();
    }
    
}
