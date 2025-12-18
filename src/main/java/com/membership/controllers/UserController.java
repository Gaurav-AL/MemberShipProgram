package com.membership.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.membership.Repository.UserRepository;
import com.membership.dao.User;
import com.membership.dto.CreateUserRequest;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("api/v1")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/create-users")
    public ResponseEntity<?> postMethodName(@Valid @RequestBody CreateUserRequest request) {
        User user = mapToEntity(request);
        userRepository.save(user);
        return ResponseEntity.ok(user);
    }

    User mapToEntity(CreateUserRequest dto) {
        User user = new User();
        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());
        user.setEmail(dto.getEmail());
        user.setContact(dto.getContact());
        return user;
    }
}
