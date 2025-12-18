package com.membership.dao;

import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.annotation.Generated;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "membership_transaction")
public class MembershipTransaction {
    
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    
    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Enumerated(EnumType.STRING)
    @Column(name = "from_tier" , nullable = false)
    private PlanTier fromTier;

    @Enumerated(EnumType.STRING)
    @Column(name = "to_tier", nullable = false)
    private PlanTier toTier;

    @Column(name = "amount_paid", nullable = false)
    private Integer amountPaid;

    @Enumerated(EnumType.STRING)
    @Column(name = "transaction_reason" , nullable = false)
    private TransactionType reason; 

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Enumerated(EnumType.STRING)
    @Column(name = "plan_type" , nullable = false)
    private PlanType planType;

    // can add current, payment reference_id etc later, currently don't have payment service so ignoring it
    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
    }
}
