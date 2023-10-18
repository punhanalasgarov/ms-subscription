package com.ingress.ms.subscription.mapper;

import com.ingress.ms.subscription.dao.entity.SubscriptionEntity;
import com.ingress.ms.subscription.model.enums.SubscriptionType;
import com.ingress.ms.subscription.model.queue.SubscriptionQueueDto;
import com.ingress.ms.subscription.model.reuqest.CreateSubscriptionRequestDto;

import java.time.LocalDateTime;

import static com.ingress.ms.subscription.model.enums.SubscriptionStatus.ACTIVE;

public class SubscriptionMapper {

    public static SubscriptionEntity buildSubscriptionEntity(CreateSubscriptionRequestDto createSubscriptionRequestDto,
                                                             LocalDateTime expireDate){
        return SubscriptionEntity.builder()
                .userId(createSubscriptionRequestDto.getUserId())
                .productId(createSubscriptionRequestDto.getProductId())
                .type(SubscriptionType.valueOf(createSubscriptionRequestDto.getSubscriptionType().name()))
                .status(ACTIVE)
                .expireDate(expireDate)
                .build();

    }

    public static SubscriptionQueueDto buildSubscriptionQueueDto(CreateSubscriptionRequestDto createSubscriptionRequestDto){
        return SubscriptionQueueDto.builder()
                .userId(createSubscriptionRequestDto.getUserId())
                .productId(createSubscriptionRequestDto.getProductId())
                .subscriptionType(createSubscriptionRequestDto.getSubscriptionType().name())
                .subscriptionStatus(ACTIVE.name())
                .build();
    }


}
