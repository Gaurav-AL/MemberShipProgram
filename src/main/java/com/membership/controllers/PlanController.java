package com.membership.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.membership.service.PlanService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;


@RestController
@RequestMapping("/api/v1")
public class PlanController {
    
    @Autowired
    private PlanService planService;

    @GetMapping("/plans")
    public ResponseEntity<?> getPlans() {
        return ResponseEntity.ok(planService.getV1PlanInfo());
    }
    
    @GetMapping("/planbenefits")
    public ResponseEntity<?> getPlanBenefits() {
        return ResponseEntity.ok(planService.getV1PlanBenefitsInfo());
    }
}
