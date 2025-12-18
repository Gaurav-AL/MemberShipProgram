package com.membership.service;


import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.membership.Repository.IdempotencyKeyRepository;
import com.membership.Repository.MemberShipTransactionRepository;
import com.membership.dao.MembershipTransaction;
import com.membership.dao.Operations;
import com.membership.dao.OrderTransaction;
import com.membership.dao.PlanTier;
import com.membership.dao.PlanType;
import com.membership.dao.TransactionType;

import jakarta.transaction.Transactional;

@Service
public class MemberShipTransactionService {
    
    @Autowired
    private MemberShipTransactionRepository membershipTransRepo;

    @Transactional
    public void saveUpgradedTransaction(Long UserId, PlanTier from_tier , PlanTier to_tier, PlanType planType, String idempotencyKey){

        int calculateCumulativePrice = getCalculatedCumulativeUpgradedTierPricing(from_tier , to_tier ,planType);
        MembershipTransaction memTrans = new MembershipTransaction();
        memTrans.setUserId(UserId);
        memTrans.setAmountPaid(calculateCumulativePrice);
        memTrans.setFromTier(from_tier);
        memTrans.setToTier(to_tier);
        memTrans.setReason(TransactionType.UPGRADE);
        memTrans.setPlanType(planType);
        membershipTransRepo.save(memTrans);
    }

    @Transactional
    public void saveDowngradedTransaction(Long UserId, PlanTier from_tier , PlanTier to_tier,PlanType planType, String idempotencyKey){
        int calculateCumulativePrice = getCalculatedCumulativeDowngradedTierPricing(from_tier , to_tier, planType);

        MembershipTransaction memTrans = new MembershipTransaction();
        memTrans.setUserId(UserId);
        memTrans.setAmountPaid(calculateCumulativePrice);
        memTrans.setFromTier(from_tier);
        memTrans.setToTier(to_tier);
        memTrans.setReason(TransactionType.DOWNGRADE);
        memTrans.setPlanType(planType);
        membershipTransRepo.save(memTrans);
    }

    @Transactional
    public void saveCancelledTransaction(Long UserId, PlanTier from_tier , PlanTier to_tier, PlanType planType, String idempotencyKey){

        // to_tier will become BASIC, from_tier to price in negative have to amount to be paid
        MembershipTransaction memTrans = new MembershipTransaction();
        memTrans.setUserId(UserId);
        memTrans.setAmountPaid((from_tier.getPrice() * -1)* decodePlanType(planType));
        memTrans.setFromTier(from_tier);
        memTrans.setToTier(to_tier);
        memTrans.setReason(TransactionType.CANCEL);
        memTrans.setPlanType(planType);
        membershipTransRepo.save(memTrans);
    }

    private int getCalculatedCumulativeUpgradedTierPricing(PlanTier CurrentTier ,PlanTier upgradedPlanTier, PlanType planType) {
        return Math.max(0, upgradedPlanTier.getPrice() - CurrentTier.getPrice()) * decodePlanType(planType);
    }

    private int getCalculatedCumulativeDowngradedTierPricing(PlanTier CurrentTier ,PlanTier downgradePlanTier, PlanType planType) {
        return  CurrentTier.getPrice() - downgradePlanTier.getPrice() * decodePlanType(planType);
    }

    public List<MembershipTransaction> getAllUserTransactions(Long usedId) {
        return membershipTransRepo.findByUserId(usedId);
    }

    private int decodePlanType(PlanType planType) {
        return switch (planType) {
                case FREE -> 1;
                case MONTHLY -> 1;
                case QUARTERLY -> 4;
                case YEARLY -> 12;
            default -> throw new IllegalArgumentException("Unexpected value: " + planType);
        };
    }
}
