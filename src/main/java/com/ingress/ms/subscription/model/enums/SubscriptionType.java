package com.ingress.ms.subscription.model.enums;

import lombok.Getter;

@Getter
public enum SubscriptionType {
    WEEKLY(7), MONTHLY(30);

    private final int day;
    SubscriptionType(int day) {
        this.day = day;
    }
}
