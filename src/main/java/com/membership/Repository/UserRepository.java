package com.membership.Repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.membership.dao.User;
import java.util.List;
import java.time.LocalDateTime;



public interface UserRepository extends JpaRepository<User , UUID>{
    User findByEmail(String email);
    User findByContact(String contact);
    List<User> findByCreatedAt(LocalDateTime createdAt);
    
} 