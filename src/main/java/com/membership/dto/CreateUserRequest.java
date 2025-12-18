package com.membership.dto;

import com.membership.Helpers.ContactValidator;
import com.membership.Helpers.EmailValidator;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ContactValidator
@EmailValidator
public class CreateUserRequest {
    private String firstName;
    private String lastName;
    private String country;
    private String email;
    private String contact;
}
