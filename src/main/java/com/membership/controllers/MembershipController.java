package com.membership.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.membership.dao.PlanTier;
import com.membership.service.MembershipService;

@RestController
@RequestMapping("/api/membership")
public class MembershipController {

    @Autowired
    private MembershipService membershipService;

    @PostMapping("/subscribe")
    public ResponseEntity<?> subscribe(@RequestParam Long userId, @RequestParam Long planId) {
        return ResponseEntity.ok(membershipService.subscribe(userId, planId));
    }

    @PostMapping("/upgrade")
    public ResponseEntity<?> upgrade(@RequestParam Long userId, @RequestParam PlanTier tier) {
        return ResponseEntity.ok(membershipService.upgrade(userId, tier));
    }

    @PostMapping("/downgrade")
    public ResponseEntity<?> downgrade(@RequestParam Long userId, @RequestParam PlanTier tier) {
        return ResponseEntity.ok(membershipService.downgrade(userId, tier));
    }

    @PostMapping("/cancel")
    public ResponseEntity<?> cancel(@RequestParam Long userId, @RequestParam(required = false) PlanTier tier) {
        return ResponseEntity.ok(membershipService.cancel(userId, tier));
    }

    @GetMapping("/info")
    public ResponseEntity<?> info(@RequestParam Long userId) {
        return ResponseEntity.ok(membershipService.getMembership(userId));
    }
}

