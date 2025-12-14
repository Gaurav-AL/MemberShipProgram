package com.membership.dao;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Entity
@Table(name = "plans")
public class Plan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "plan_type", nullable = false)
    private PlanType planType;

    @Enumerated(EnumType.STRING)
    @Column(name = "plan_tier" , nullable = false)
    private PlanTier planTier;

    Plan() {}
    // Getters & Setters
    @Override
    public String toString(){
        return getId() + "-"+ getPlanType() +"-"+ getPlanTier();
    }
}
