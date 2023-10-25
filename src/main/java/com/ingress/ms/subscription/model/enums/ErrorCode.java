package com.ingress.ms.subscription.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    UNEXPECTED_ERROR("UNEXPECTED_ERROR", "Exception occurred");

    private final String code;
    private final String description;
}
