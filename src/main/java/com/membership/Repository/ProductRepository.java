package com.membership.Repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.membership.dao.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product , UUID>{
} 
