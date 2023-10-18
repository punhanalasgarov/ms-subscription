package com.ingress.ms.subscription.queue;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ingress.ms.subscription.annotation.Log;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Log
public class QueueSender {
    private final AmqpTemplate amqpTemplate;
    private final ObjectMapper objectMapper;

    @SneakyThrows
    public <T> void sendToQueue(String queue, T t){
        amqpTemplate.convertAndSend(queue, objectMapper.writeValueAsString(t));
    }
}
