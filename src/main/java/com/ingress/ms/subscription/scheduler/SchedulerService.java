package com.ingress.ms.subscription.scheduler;

import com.ingress.ms.subscription.annotation.Log;
import com.ingress.ms.subscription.service.SubscriptionService;
import lombok.RequiredArgsConstructor;
import net.javacrumbs.shedlock.spring.annotation.SchedulerLock;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Log
public class SchedulerService {

    private final SubscriptionService subscriptionService;

    @Scheduled(fixedDelayString = "PT1M")
    @SchedulerLock(name = "subscriptionsStatusChange", lockAtLeastFor = "PT1M", lockAtMostFor = "PT5M")
    public void subscriptionsStatusCheck(){
        subscriptionService.subscriptionsStatusChange();
    }
}



