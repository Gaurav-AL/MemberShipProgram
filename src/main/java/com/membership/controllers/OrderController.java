package com.membership.controllers;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.membership.dao.Operations;
import com.membership.dao.Order;
import com.membership.dao.UserMemberShipInfo;
import com.membership.dto.OrderRequest;
import com.membership.service.IdempotencyExecutorService;
import com.membership.service.OrderService;

import tools.jackson.core.type.TypeReference;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/api/orders")
public class OrderController {
    
    @Autowired
    private OrderService orderService;

    @Autowired
    private IdempotencyExecutorService idempotencyExecutorService;

    @PostMapping("/create")
    public ResponseEntity<?> createOrder(
        @RequestHeader("Idempotency-Key") String key,
        @RequestBody @Validated OrderRequest request) 
    {
        String canonicalString =  request.getProductId() +"|" + request.getProductId() +"|" ; 
        
        Order response = idempotencyExecutorService.execute(key,
                                                    Operations.POST_UPGRADE, 
                                                    canonicalString,
                                                    () -> orderService.createOrders(request),
                                                    new TypeReference<Order>() {});

        return ResponseEntity.ok(response);
    }

    @GetMapping("/get")
    public ResponseEntity<?>  getOrderByUserId(
        @RequestHeader("Idempotency-Key") String key,
        @RequestParam("userId") Long userId) {
        String canonicalString =  userId +"|"; 
        
        List<Order> response = idempotencyExecutorService.execute(key,
                                                    Operations.POST_UPGRADE, 
                                                    canonicalString,
                                                    () -> orderService.getOrderByUserId(userId),
                                                    new TypeReference<List<Order>>() {});

        return ResponseEntity.ok(response);
    }
    

}
