package com.membership.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.membership.dao.PlanBenefit;

@Repository
public interface PlanBenefitRepository extends JpaRepository<PlanBenefit, Long> {
    List<PlanBenefit> findByPlanId(Long planId);
}

