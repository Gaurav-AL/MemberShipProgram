package com.membership.Repository;

import org.springframework.stereotype.Repository;
import com.membership.dao.Plan;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface PlanRepository extends JpaRepository<Plan, Long> {
    
}
