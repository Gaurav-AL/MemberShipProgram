package com.membership.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.membership.dao.OrderTransaction;

@Repository
public interface OrderTransactionRepository extends JpaRepository<OrderTransaction , UUID>{
    List<OrderTransaction> findByUserId(Long userId);

    Optional<OrderTransaction> findByIdempotencyKey(String idempotencyKey);

} 