package com.membership.Repository;

import org.springframework.stereotype.Repository;

import com.membership.dao.MembershipTransaction;
import com.membership.dao.OrderTransaction;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;


@Repository
public interface MemberShipTransactionRepository extends JpaRepository<MembershipTransaction , UUID> {
        List<MembershipTransaction> findByUserId(Long userId);

        Optional<MembershipTransaction> findByIdempotencyKey(String idempotencyKey);

}
