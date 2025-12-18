package com.membership.dto;

import com.membership.dao.PlanTier;
import jakarta.annotation.Nonnull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PlanRequest {
    @Nonnull
    private Long userId;

    @Nonnull
    private Long planId;

    @Nonnull
    private String idempotencyKey;

    private PlanTier planTier;
}

