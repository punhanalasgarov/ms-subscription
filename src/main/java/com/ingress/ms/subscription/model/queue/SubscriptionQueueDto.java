package com.ingress.ms.subscription.model.queue;

import com.ingress.ms.subscription.model.enums.SubscriptionStatus;
import com.ingress.ms.subscription.model.enums.SubscriptionType;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class SubscriptionQueueDto {
    private Long userId;
    private Long productId;
    private SubscriptionType subscriptionType;
    private SubscriptionStatus subscriptionStatus;
}
