package com.membership.service;

import java.time.LocalDate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.membership.Repository.PlanRepository;
import com.membership.Repository.UserMembershipRepository;
import com.membership.dao.MemberStatus;
import com.membership.dao.MemberTier;
import com.membership.dao.Plan;
import com.membership.dao.PlanTier;
import com.membership.dao.UserMemberShipInfo;

@Service
public class MembershipService {

    private static final Logger log = LoggerFactory.getLogger(MembershipService.class);

    @Autowired
    private UserMembershipRepository membershipRepo;

    @Autowired
    private PlanRepository planRepo;

    @Autowired
    private MemberShipTransactionService memberShipTransactionService;

    public UserMemberShipInfo subscribe(Long userId, Long planId) {
        Plan plan = planRepo.findById(planId)
                .orElseThrow(() -> new RuntimeException("Invalid Plan"));

        log.info("User Id {} subscribing to Plan {}" , userId , plan);

        UserMemberShipInfo info = getMembership(userId);
        if(info != null)
        {
            log.info("User {} already has membership. Current tier: {}, Upgrading",
            userId, info.getPlanTier());
            
            MemberTier upgradedTier = upgradeTier(info.getMemberTier());
            if (upgradedTier == info.getMemberTier()) {
                log.warn("User {} is already at highest tier: {}",
                        userId, info.getPlanTier());
            }
            
            PlanTier upgradedPlanTier = upgradedPlanTier(info.getPlanTier());
            if(upgradedPlanTier != info.getPlanTier()){
                memberShipTransactionService.saveUpgradedTransaction(userId, info.getPlanTier(), upgradedPlanTier, info.getPlanType());
                info.setPlanTier(upgradedPlanTier);
            }else{
                log.warn("User {} is already at highest tier: {}",
                        userId, info.getPlanTier());
            }
            info.setMemberStatus(MemberStatus.UPGRADED);
            info.setExpiryDate(calculateNewExpiry(info, 30));

            log.info("User {} upgraded to tier {}", userId, upgradedTier);

            return membershipRepo.save(info);
        }
        else
        {
            UserMemberShipInfo newInfo = new UserMemberShipInfo();
            newInfo.setUserId(userId);
            newInfo.setMemberStatus(MemberStatus.NEW_SUBSCRIBER);
            newInfo.setMemberTier(MemberTier.SILVER);
            newInfo.setExpiryDate(LocalDate.now().plusDays(30));
            newInfo.setPlanTier(PlanTier.FREE);
            return membershipRepo.save(newInfo);
        }
    }

    public UserMemberShipInfo upgrade(Long userId, PlanTier newTier) {
        UserMemberShipInfo info = membershipRepo.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("User not subscribed"));

        log.info("UserMemberShipInfo  upgrade userId {} , newTier {}", userId , newTier); 
        
        PlanTier upgradedPlanTier = upgradedPlanTier(info.getPlanTier());
        if(upgradedPlanTier != info.getPlanTier()){
            info.setPlanTier(upgradedPlanTier);
            memberShipTransactionService.saveUpgradedTransaction(userId, info.getPlanTier(), upgradedPlanTier, info.getPlanType());
        }else{
            log.warn("User {} is already at highest tier: {}",
                    userId, info.getPlanTier());
        }
        MemberTier upgradedTier = upgradeTier(info.getMemberTier());
        if (upgradedTier == info.getMemberTier()) {
            log.warn("User {} is already at highest tier: {}",
                    userId, info.getMemberTier());
        }else{
            info.setMemberTier(upgradedTier);
            info.setMemberStatus(MemberStatus.UPGRADED);
            info.setExpiryDate(calculateNewExpiry(info, 30));
        }
        return membershipRepo.save(info);
    }

    public UserMemberShipInfo downgrade(Long userId, PlanTier oldTier) {
        UserMemberShipInfo info = membershipRepo.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("User not subscribed"));

        log.info("UserMemberShipInfo  downgrade userId {} , oldTier {}", userId , oldTier);

        PlanTier downgradePlanTier = downgradeTierPlanTier(info.getPlanTier());
        if(downgradePlanTier != info.getPlanTier()){
            memberShipTransactionService.saveDowngradedTransaction(userId, info.getPlanTier(), downgradePlanTier, info.getPlanType());
            info.setPlanTier(downgradePlanTier);
        }else{
            log.warn("User {} is already at lowest tier: {}",
                    userId, info.getPlanTier());
        }

        MemberTier downgradeTier = downgradeTier(info.getMemberTier());
        if (downgradeTier == info.getMemberTier()) {
            log.warn("User {} is already at lowest tier: {}",
                    userId, info.getMemberTier());
        }else{
            info.setMemberTier(downgradeTier);
            info.setMemberStatus(MemberStatus.DOWNGRADED);
            info.setExpiryDate(calculateNewExpiry(info, 30));
        }
        if(info.getPlanTier() == PlanTier.FREE) info.setExpiryDate(null);
        return membershipRepo.save(info);
    }

    public UserMemberShipInfo cancel(Long userId, PlanTier oldTier) {
        UserMemberShipInfo info = membershipRepo.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("User not subscribed"));

        log.info("UserMemberShipInfo  cancel userId {} , oldTier {}", userId , oldTier);
        PlanTier cancelPlanTier = downgradeTierPlanTier(info.getPlanTier());
        if(cancelPlanTier != info.getPlanTier()){
            memberShipTransactionService.saveCancelledTransaction(userId, oldTier, cancelPlanTier, info.getPlanType());
            info.setPlanTier(cancelPlanTier);
        }else{
            log.warn("User {} is already at lowest tier: {}",
                    userId, info.getPlanTier());
        }
        MemberTier cancelledTier = downgradeTier(info.getMemberTier());
        if (cancelledTier == info.getMemberTier()) {
            log.warn("User {} is already at lowest tier: {}",
                    userId, info.getMemberTier());
        }else{
            info.setMemberStatus(MemberStatus.CANCELLED);
            info.setExpiryDate(calculateNewExpiry(info, 30));
        }
        if(info.getPlanTier() == PlanTier.FREE) info.setExpiryDate(null);
        info.setPlanTier(oldTier);
        info.setMemberStatus(MemberStatus.CANCELLED);
        return membershipRepo.save(info);
    }
    public UserMemberShipInfo getMembership(Long userId) {
        // System.out.println("getMembership");
        return membershipRepo.findByUserId(userId).orElse(null);
    }

    private MemberTier upgradeTier(MemberTier current) {
    return switch (current) {
        case SILVER -> MemberTier.GOLD;
        case GOLD -> MemberTier.PLATINUM;
        case PLATINUM -> MemberTier.PLATINUM;
        };
    }
    
    private PlanTier upgradedPlanTier(PlanTier planTier) {
        return switch (planTier) {
                case FREE -> PlanTier.BASIC;
                case BASIC -> PlanTier.PREMIUM;
                case PREMIUM -> PlanTier.PREMIUM;
        };
    }

    private MemberTier downgradeTier(MemberTier current) {
    return switch (current) {
        case SILVER -> MemberTier.SILVER;
        case GOLD -> MemberTier.SILVER;
        case PLATINUM -> MemberTier.GOLD;
        };
    }
    
    private PlanTier downgradeTierPlanTier(PlanTier planTier) {
        return switch (planTier) {
                case FREE -> PlanTier.FREE;
                case BASIC -> PlanTier.FREE;
                case PREMIUM -> PlanTier.BASIC;
        };
    }

    private LocalDate calculateNewExpiry(UserMemberShipInfo info, int days) {
        return (info.getExpiryDate() == null
                ? LocalDate.now()
                : info.getExpiryDate())
                .plusDays(days);
    }
}

