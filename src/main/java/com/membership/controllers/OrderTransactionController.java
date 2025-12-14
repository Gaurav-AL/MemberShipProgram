package com.membership.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.membership.Repository.OrderTransactionRepository;
import com.membership.service.OrderTransactionService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/api/v1")
public class OrderTransactionController {
    
    @Autowired
    private OrderTransactionService orderTransactionService;

    @GetMapping("/order-transactions")
    public ResponseEntity<?> getMethodName(@RequestParam("userId") Long userId) {
        return ResponseEntity.ok(orderTransactionService.getAllOrderTransactionByUserId(userId));
    }
    
}
