package com.ingress.ms.subscription.controller;

import com.ingress.ms.subscription.model.reuqest.CreateSubscriptionRequestDto;
import com.ingress.ms.subscription.service.SubscriptionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("/v1/subscriptions")
@RequiredArgsConstructor
@Tag(name = "Subscription Service API v1", description = "Endpoints for managing subscriptions")
public class SubscriptionController {
    private final SubscriptionService subscriptionService;

    @ResponseStatus(CREATED)
    @PostMapping
    @Operation(summary = "Create subscription", description = "User creation by userId, productId, subscriptionType")
    public void createSubscription(@RequestBody CreateSubscriptionRequestDto createSubscriptionRequestDto){
        subscriptionService.createSubscription(createSubscriptionRequestDto);
    }
}
