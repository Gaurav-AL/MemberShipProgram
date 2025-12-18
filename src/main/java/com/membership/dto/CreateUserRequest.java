package com.membership.dto;

import com.membership.Helpers.ContactValidator;
import com.membership.Helpers.EmailValidator;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@Getter
@Setter
public class CreateUserRequest {
    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;

    @NotBlank
    private String country;

    @NotBlank
    @EmailValidator
    private String email;

    @NotBlank
    @ContactValidator
    private String contact;

    @NotBlank
    private String idempotency_key;
}
