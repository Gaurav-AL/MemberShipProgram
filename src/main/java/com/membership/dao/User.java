package com.membership.dao;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Index;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "users",
    indexes = {
        @Index(name = "idx_users_email", columnList = "email"),
        @Index(name = "idx_users_contact", columnList = "contact"),
        @Index(name = "idx_users_role", columnList = "user_role")
    }
)
public class User {
    
    @Id
    @GeneratedValue( strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "first_name" , nullable = false)
    private String firstName;

    @Column(name = "last_name" , nullable = false)
    private String lastName;

    @Column(name = "age" , nullable = true)
    private Integer age;

    @Column(name = "email" , nullable = false, unique = true)
    private String email;

    @Column(name = "contact" , nullable = false)
    private String contact;

    @Enumerated(EnumType.STRING)
    @Column(name = "user_role" , nullable = false)
    private UserRole role;

    @Column(name = "is_active" , nullable = false)
    private boolean isActive;

    @Column(name = "last_active_date")
    private LocalDateTime lastActive;

    @CreationTimestamp
    @Column(name = "created_at" , nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "expired_at")
    private LocalDate expiredAt;

}
