package com.ingress.ms.subscription.model.queue;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SubscriptionQueueDto {
    private Long userId;
    private Long productId;
    private String subscriptionType;
    private String subscriptionStatus;
}
