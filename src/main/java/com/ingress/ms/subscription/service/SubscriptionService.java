package com.ingress.ms.subscription.service;

import com.ingress.ms.subscription.annotation.Log;
import com.ingress.ms.subscription.model.reuqest.CreateSubscriptionRequestDto;
import com.ingress.ms.subscription.model.enums.SubscriptionStatus;
import com.ingress.ms.subscription.dao.entity.SubscriptionEntity;
import com.ingress.ms.subscription.queue.QueueSender;
import com.ingress.ms.subscription.dao.repository.SubscriptionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

import static com.ingress.ms.subscription.mapper.SubscriptionMapper.buildSubscriptionEntity;
import static com.ingress.ms.subscription.mapper.SubscriptionMapper.buildSubscriptionQueueDto;

@Service
@RequiredArgsConstructor
@Slf4j
@Log
public class SubscriptionService {

    @Value("${rabbitmq.subscriptions.queue}")
    private String queueName;
    private final SubscriptionRepository subscriptionRepository;
    private final QueueSender queueSender;

    @Transactional
    public void createSubscription(CreateSubscriptionRequestDto createSubscriptionRequestDto){
        var subscriptionEntity = buildSubscriptionEntity(createSubscriptionRequestDto);
        var entity = subscriptionRepository.save(subscriptionEntity);
        publishQueue(entity);
    }

    public void publishQueue(SubscriptionEntity subscriptionEntity){
        var subscriptionQueueDto = buildSubscriptionQueueDto(subscriptionEntity);
        queueSender.sendToQueue(queueName, Collections.singletonList(subscriptionQueueDto));
    }

    @Transactional
    public void subscriptionsStatusChange(){
        List<SubscriptionEntity> expiredSubscriptions = subscriptionRepository.findAllByExpireDateEnded();
        expiredSubscriptions.forEach(s -> s.setStatus(SubscriptionStatus.EXPIRED));
        subscriptionRepository.saveAll(expiredSubscriptions);
        var subscriptionQueueDtoList = buildSubscriptionQueueDto(expiredSubscriptions);
        queueSender.sendToQueue(queueName, subscriptionQueueDtoList);
    }
}
