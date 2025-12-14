package com.membership.dao;

import java.math.BigDecimal;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "product")
public class Product {
    
    @Id
    @GeneratedValue( strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "product_name", nullable = false)
    private String productName;

    @Column(name = "product_amount", nullable = false)
    private BigDecimal productAmount;

    @Column(name = "active", nullable = false)
    private Boolean active = true;

    // other information like product added date, product expiry date etc can be considered for enhancement
}
