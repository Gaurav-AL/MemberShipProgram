package com.membership.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.membership.dto.PlanRequest;
import com.membership.service.MembershipService;

@RestController
@RequestMapping("/api/membership")
public class MembershipController {

    @Autowired
    private MembershipService membershipService;

    @PostMapping("/subscribe")
    public ResponseEntity<?> subscribe(
        @RequestHeader("Idempotency-Key") String key,
        @RequestBody @Validated PlanRequest request) 
    {
        request.setIdempotencyKey(key);
        return ResponseEntity.ok(membershipService.subscribe(request));
    }

    @PostMapping("/upgrade")
    public ResponseEntity<?> upgrade(
        @RequestHeader("Idempotency-Key") String key,
        @RequestBody @Validated PlanRequest request) 
    {
        request.setIdempotencyKey(key);
        return ResponseEntity.ok(membershipService.upgrade(request));
    }

    @PostMapping("/downgrade")
    public ResponseEntity<?> downgrade(
        @RequestHeader("Idempotency-Key") String key,
        @RequestBody @Validated PlanRequest request) 
    {
        request.setIdempotencyKey(key);
        return ResponseEntity.ok(membershipService.downgrade(request));
    }

    @PostMapping("/cancel")
    public ResponseEntity<?> cancel(
        @RequestHeader("Idempotency-Key") String key,
        @RequestBody @Validated PlanRequest request) 
    {
        request.setIdempotencyKey(key);
        return ResponseEntity.ok(membershipService.cancel(request));
    }

    @GetMapping("/info")
    public ResponseEntity<?> info(@RequestParam Long userId) {
        return ResponseEntity.ok(membershipService.getMembership(userId));
    }
}

