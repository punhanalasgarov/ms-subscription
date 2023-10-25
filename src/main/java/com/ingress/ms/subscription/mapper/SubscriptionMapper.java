package com.ingress.ms.subscription.mapper;

import com.ingress.ms.subscription.dao.entity.SubscriptionEntity;
import com.ingress.ms.subscription.model.enums.SubscriptionType;
import com.ingress.ms.subscription.model.queue.SubscriptionQueueDto;
import com.ingress.ms.subscription.model.reuqest.CreateSubscriptionRequestDto;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static com.ingress.ms.subscription.model.enums.SubscriptionStatus.ACTIVE;

public class SubscriptionMapper {

    public static SubscriptionEntity buildSubscriptionEntity(CreateSubscriptionRequestDto source){
        var expireDate = LocalDateTime.now().plusDays(source.getSubscriptionType().getDay());
        return SubscriptionEntity.builder()
                .userId(source.getUserId())
                .productId(source.getProductId())
                .type(source.getSubscriptionType())
                .status(ACTIVE)
                .expireDate(expireDate)
                .build();

    }

    public static SubscriptionQueueDto buildSubscriptionQueueDto(SubscriptionEntity source){
        return SubscriptionQueueDto.builder()
                .userId(source.getUserId())
                .productId(source.getProductId())
                .subscriptionType(source.getType())
                .subscriptionStatus(source.getStatus())
                .build();
    }

    public static List<SubscriptionQueueDto> buildSubscriptionQueueDto(List<SubscriptionEntity> from) {
        return from.stream().map(SubscriptionMapper::buildSubscriptionQueueDto).collect(Collectors.toList());
    }

}
