package com.ingress.ms.subscription.model.reuqest;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateSubscriptionRequestDto {
    Long userId;
    Long productId;
    SubscriptionTypeRequestDto subscriptionType;
}
