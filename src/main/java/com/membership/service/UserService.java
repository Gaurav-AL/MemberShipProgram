package com.membership.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.membership.Repository.UserRepository;
import com.membership.dao.User;
import com.membership.dto.CreateUserRequest;

@Service
public class UserService {
    
    @Autowired
    private UserRepository userRepository;

    public User createUser(CreateUserRequest request){
        User user = mapToEntity(request);
        userRepository.save(user);
        return user;
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
