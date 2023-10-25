package com.ingress.ms.subscription.model.reuqest;

import com.ingress.ms.subscription.model.enums.SubscriptionType;
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
    SubscriptionType subscriptionType;
}
