package com.membership.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.membership.Repository.UserRepository;
import com.membership.dao.Operations;
import com.membership.dao.User;
import com.membership.dao.UserMemberShipInfo;
import com.membership.dto.CreateUserRequest;
import com.membership.service.IdempotencyExecutorService;
import com.membership.service.UserService;

import jakarta.validation.Valid;
import tools.jackson.core.type.TypeReference;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;


@RestController
@RequestMapping("api/v1")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private IdempotencyExecutorService idempotencyExecutorService;

    @PostMapping("/create-users")
    public ResponseEntity<?> postMethodName(
        @RequestHeader("Idempotency-Key") String key,
        @Valid @RequestBody CreateUserRequest request) {
        String canonicalString =  request.getContact() +"|" 
                                + request.getCountry() +"|" 
                                + request.getEmail() + "|"
                                + request.getFirstName() + "|"
                                + request.getLastName(); 
                        
        
        User response = idempotencyExecutorService.execute(key,
                                                    Operations.POST_SUBSCRIBE, 
                                                    canonicalString,
                                                    () -> userService.createUser(request),
                                                    new TypeReference<User>() {});

        return ResponseEntity.ok(response);
    }
}
