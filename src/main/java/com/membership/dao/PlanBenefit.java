package com.membership.dao;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "plan_benefits")
public class PlanBenefit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "plan_id" , nullable = false)
    private Plan plan;

    private String itemName;

    private float discountPercent;

    PlanBenefit() {}

    // extra benefits like coupons & early access can be added

    @Override
    public String toString(){
        return  getId() + "-"+ getDiscountPercent() +"-"+ getItemName() +"-"
                + getPlan();
    }
}
