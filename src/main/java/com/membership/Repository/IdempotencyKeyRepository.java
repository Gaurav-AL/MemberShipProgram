package com.membership.Repository;

import java.util.UUID;

import org.hibernate.boot.beanvalidation.GroupsPerOperation.Operation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.membership.dao.IdempotencyKeys;
import com.membership.dao.Operations;
import java.util.Optional;



@Repository
public interface IdempotencyKeyRepository extends JpaRepository<IdempotencyKeys, UUID>{
    Optional<IdempotencyKeys> findByRequestHash(String requestHash);

    Optional<IdempotencyKeys> findByIdempotencyKey(String idempotencyKey);

    Optional<IdempotencyKeys> findByOperation(Operations operation);

    Optional<IdempotencyKeys> findByIdempotencyKeyAndOperation(String request_hash , Operations operation);
}
