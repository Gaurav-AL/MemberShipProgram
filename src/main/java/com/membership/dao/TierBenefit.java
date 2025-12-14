package com.membership.dao;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Entity
@Table(name = "tier_benefits")
public class TierBenefit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "member_tier" , nullable = false)
    private MemberTier memberTier;

    private String benefit;

    TierBenefit(){}
    // Getters and Setters are required By JPA

    @Override
    public String toString(){
        return  getId() + "-"+ getBenefit() +"-"+ getMemberTier();
    }
}
