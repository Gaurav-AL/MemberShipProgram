package com.membership.Repository;

import java.util.List;
import java.util.Optional;
// import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.membership.dao.MemberStatus;
import com.membership.dao.UserMemberShipInfo;
// import com.membership.dao.MemberStatus;

@Repository
public interface UserMembershipRepository extends JpaRepository<UserMemberShipInfo, UUID> {

    List<UserMemberShipInfo> findByUserIdAndMemberStatus(Long userId , MemberStatus ACTIVE);
    Optional<UserMemberShipInfo> findByUserId(Long userId);

    Optional<UserMemberShipInfo> findByIdempotencyKey(String idempotencyKey);

}

