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

import com.membership.dao.IdempotencyStatus;
import com.membership.dao.Operations;
import com.membership.dao.UserMemberShipInfo;
import com.membership.dto.PlanRequest;
import com.membership.service.IdempotencyExecutorService;
import com.membership.service.MembershipService;

import tools.jackson.core.type.TypeReference;

@RestController
@RequestMapping("/api/membership")
public class MembershipController {

    @Autowired
    private MembershipService membershipService;

    @Autowired
    private IdempotencyExecutorService idempotencyExecutorService;

    @PostMapping("/subscribe")
    public ResponseEntity<?> subscribe(
        @RequestHeader("Idempotency-Key") String key,
        @RequestBody @Validated PlanRequest request) 
    {
        String canonicalString =  request.getPlanId() +"|" 
                                + request.getUserId() +"|" 
                                + request.getPlanTier(); 
        
        UserMemberShipInfo response = idempotencyExecutorService.execute(key,
                                                    Operations.POST_SUBSCRIBE, 
                                                    canonicalString,
                                                    () -> membershipService.subscribe(request),
                                                    new TypeReference<UserMemberShipInfo>() {});

        return ResponseEntity.ok(response);
    }

    @PostMapping("/upgrade")
    public ResponseEntity<?> upgrade(
        @RequestHeader("Idempotency-Key") String key,
        @RequestBody @Validated PlanRequest request) 
    {
        String canonicalString =  request.getPlanId() +"|" 
                                + request.getUserId() +"|" 
                                + request.getPlanTier(); 
        
        UserMemberShipInfo response = idempotencyExecutorService.execute(key,
                                                    Operations.POST_UPGRADE, 
                                                    canonicalString,
                                                    () -> membershipService.upgrade(request),
                                                    new TypeReference<UserMemberShipInfo>() {});

        return ResponseEntity.ok(response);
    }

    @PostMapping("/downgrade")
    public ResponseEntity<?> downgrade(
        @RequestHeader("Idempotency-Key") String key,
        @RequestBody @Validated PlanRequest request) 
    {
        String canonicalString =  request.getPlanId() +"|" 
                                + request.getUserId() +"|" 
                                + request.getPlanTier(); 
        
        UserMemberShipInfo response = idempotencyExecutorService.execute(key,
                                                    Operations.POST_DOWNGRADE, 
                                                    canonicalString,
                                                    () -> membershipService.downgrade(request),
                                                    new TypeReference<UserMemberShipInfo>() {});

        return ResponseEntity.ok(response);
    }

    @PostMapping("/cancel")
    public ResponseEntity<?> cancel(
        @RequestHeader("Idempotency-Key") String key,
        @RequestBody @Validated PlanRequest request) 
    {
        String canonicalString =  request.getPlanId() +"|" 
                                + request.getUserId() +"|" 
                                + request.getPlanTier(); 
        
        UserMemberShipInfo response = idempotencyExecutorService.execute(key,
                                                    Operations.POST_CANCEL, 
                                                    canonicalString,
                                                    () -> membershipService.cancel(request),
                                                    new TypeReference<UserMemberShipInfo>() {});

        return ResponseEntity.ok(response);
    }

    @GetMapping("/info")
    public ResponseEntity<?> info(
            @RequestHeader("Idempotency-Key") String key,
            @RequestParam Long userId) {
        String canonicalString =  userId +"|" ;
        
        UserMemberShipInfo response = idempotencyExecutorService.execute(key,
                                                    Operations.GET_INFO, 
                                                    canonicalString,
                                                    () -> membershipService.getMembership(userId),
                                                    new TypeReference<UserMemberShipInfo>() {});

        return ResponseEntity.ok(response);
    }
}

