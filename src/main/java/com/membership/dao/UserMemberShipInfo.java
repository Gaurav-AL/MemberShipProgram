package com.membership.dao;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "user_membership_info")
public class UserMemberShipInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "user_id", nullable =  false)
    private Long userId;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "member_status", nullable = false)
    private MemberStatus memberStatus;

    @Enumerated(EnumType.STRING)
    @Column(name = "member_tier" , nullable = false)
    private MemberTier memberTier;

    @Enumerated(EnumType.STRING)
    @Column(name = "plan_tier" , nullable = false)
    private PlanTier planTier; 
    
    @Enumerated(EnumType.STRING)
    @Column(name = "plan_type" , nullable = false)
    private PlanType planType;

    @Column(name = "expiry_date")
    private LocalDate expiryDate;

    @Column(name = "last_active", nullable = false)
    private LocalDateTime lastActive;

    public UserMemberShipInfo() {} 

    @Override
    public String toString(){
        return getId() +"-" + getUserId() +"-" + getMemberStatus() + 
                getMemberTier() +"-" + getPlanTier()+"-"+ getPlanTier().getPrice() + "-"+ getLastActive();
    }
}
