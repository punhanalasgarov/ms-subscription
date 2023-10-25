package com.ingress.ms.subscription.scheduler;

import com.ingress.ms.subscription.annotation.Log;
import com.ingress.ms.subscription.service.SubscriptionService;
import lombok.RequiredArgsConstructor;
import net.javacrumbs.shedlock.spring.annotation.SchedulerLock;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SchedulerService {

    private final SubscriptionService subscriptionService;

    @Scheduled(fixedDelayString = "PT1M")
    @SchedulerLock(name = "subscriptionsStatusChange", lockAtLeastFor = "PT1H", lockAtMostFor = "PT5H")
    public void subscriptionsStatusChange(){
        subscriptionService.subscriptionsStatusChange();
    }
}



