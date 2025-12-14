package com.membership.dto;

import java.util.UUID;

import jakarta.annotation.Nonnull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderRequest {
    @Nonnull
    private Long userId;

    @Nonnull
    private UUID productId;

    @Nonnull
    private String idempotencyKey;

}
