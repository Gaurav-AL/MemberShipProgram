package com.membership.dao;

public enum PlanTier {
    FREE(0),
    BASIC(50),
    PREMIUM(100);

    private final int price;

    PlanTier(int price) {
        this.price = price;
    }

    public int getPrice() {
        return price;
    }
}
