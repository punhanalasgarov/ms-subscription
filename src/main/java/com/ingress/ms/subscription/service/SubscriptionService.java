package com.ingress.ms.subscription.service;

import com.ingress.ms.subscription.annotation.Log;
import com.ingress.ms.subscription.exception.NotFoundException;
import com.ingress.ms.subscription.model.reuqest.CreateSubscriptionRequestDto;
import com.ingress.ms.subscription.model.reuqest.SubscriptionTypeRequestDto;
import com.ingress.ms.subscription.model.enums.SubscriptionStatus;
import com.ingress.ms.subscription.model.enums.SubscriptionType;
import com.ingress.ms.subscription.dao.entity.SubscriptionEntity;
import com.ingress.ms.subscription.queue.QueueSender;
import com.ingress.ms.subscription.dao.repository.SubscriptionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

import static com.ingress.ms.subscription.mapper.SubscriptionMapper.buildSubscriptionEntity;
import static com.ingress.ms.subscription.mapper.SubscriptionMapper.buildSubscriptionQueueDto;
import static com.ingress.ms.subscription.model.enums.ErrorCode.SUBSCRIPTION_NOT_FOUND_EXCEPTION;

@Service
@RequiredArgsConstructor
@Slf4j
@Log
public class SubscriptionService {

    @Value("${rabbitmq.subscriptions.queue}")
    private String queueName;
    private final SubscriptionRepository subscriptionRepository;
    private final QueueSender queueSender;

    public void createSubscription(CreateSubscriptionRequestDto createSubscriptionRequestDto){
        LocalDateTime expireDate = expireDate(createSubscriptionRequestDto.getSubscriptionType());
        var subscriptionEntity = buildSubscriptionEntity(createSubscriptionRequestDto, expireDate);
        subscriptionRepository.save(subscriptionEntity);
        publishQueue(createSubscriptionRequestDto);
    }

    public void publishQueue(CreateSubscriptionRequestDto createSubscriptionRequestDto){
        try{
            var subscriptionQueueDto= buildSubscriptionQueueDto(createSubscriptionRequestDto);
            queueSender.sendToQueue(queueName, subscriptionQueueDto);
        } catch (Exception e){
            log.error("ActionLog.publishQueue.error productId:{}", createSubscriptionRequestDto.getProductId());
        }
    }

    public void subscriptionsStatusChange(){
        List<SubscriptionEntity> expiredSubscriptions = subscriptionRepository
                .findAllByExpireDateEnded()
                .orElseThrow( () -> new NotFoundException(SUBSCRIPTION_NOT_FOUND_EXCEPTION.getDescription()));

        expiredSubscriptions.forEach(s -> s.setStatus(SubscriptionStatus.EXPIRED));
        subscriptionRepository.saveAll(expiredSubscriptions);
        queueSender.sendToQueue(queueName, expiredSubscriptions);
    }

    public LocalDateTime expireDate(SubscriptionTypeRequestDto subscriptionTypeRequestDto){
        var subscriptionType = SubscriptionType.valueOf(subscriptionTypeRequestDto.name());
        return LocalDateTime.now().plusDays(subscriptionType.getDay());
    }

}
