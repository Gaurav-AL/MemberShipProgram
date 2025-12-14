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

import com.membership.dao.Order;
import com.membership.dto.OrderRequest;
import com.membership.service.OrderService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/api/orders")
public class OrderController {
    
    @Autowired
    private OrderService orderService;

    @PostMapping("/create")
    public ResponseEntity<?> createOrder(
        @RequestHeader("Idempotency-Key") String key,
        @RequestBody @Validated OrderRequest request) 
    {
        request.setIdempotencyKey(key);
        Order order = orderService.createOrders(request);
        return ResponseEntity.ok(order);
    }

    @GetMapping("/get")
    public ResponseEntity<?>  getOrderByUserId(@RequestParam("userId") Long userId) {
        List<Order> order = orderService.getOrderByUserId(userId);
        return ResponseEntity.ok(order);
    }
    

}
