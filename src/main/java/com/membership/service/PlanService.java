package com.membership.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.membership.Repository.PlanBenefitRepository;
import com.membership.Repository.PlanRepository;
import com.membership.dao.Plan;
import com.membership.dao.PlanBenefit;

@Service
public class PlanService {
    private static final Logger log = LoggerFactory.getLogger(PlanService.class);

    @Autowired
    private PlanRepository planRepo;

    @Autowired
    private PlanBenefitRepository planbenefitsRepo;

    public List<Plan> getV1PlanInfo(){
        log.info("getV1PlanInfo() called to Fetch All the Plans");
        List<Plan> plans = planRepo.findAll();
        return plans;
    }

    public List<PlanBenefit> getV1PlanBenefitsInfo(){
        log.info("getV1PlanInfo() called to Fetch All the Plans");
        List<PlanBenefit> planbenefits = planbenefitsRepo.findAll();
        return planbenefits;
    }
}
