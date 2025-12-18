package com.membership.dto;

import com.membership.dao.PlanTier;
import jakarta.annotation.Nonnull;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PlanRequest {
    @NotBlank
    private Long userId;

    @NotBlank
    private Long planId;

    @NotBlank
    private String idempotencyKey;

    private PlanTier planTier;
}

