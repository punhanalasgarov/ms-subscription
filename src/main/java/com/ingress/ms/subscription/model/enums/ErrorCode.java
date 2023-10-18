package com.ingress.ms.subscription.model.enums;

import lombok.Getter;

@Getter
public enum ErrorCode {
    UNEXPECTED_ERROR("ERROR_1", "Exception occurred"),

    SUBSCRIPTION_NOT_FOUND_EXCEPTION("ERROR_2", "Subscription could not found");

    private final String code;
    private final String description;

    ErrorCode(String code, String description) {
        this.code = code;
        this.description = description;
    }

}
