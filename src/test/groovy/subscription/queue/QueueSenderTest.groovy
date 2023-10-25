package subscription.queue

import com.fasterxml.jackson.databind.ObjectMapper
import com.ingress.ms.subscription.model.queue.SubscriptionQueueDto
import com.ingress.ms.subscription.queue.QueueSender
import com.ingress.ms.subscription.service.SubscriptionService
import io.github.benas.randombeans.EnhancedRandomBuilder
import io.github.benas.randombeans.api.EnhancedRandom
import org.springframework.amqp.core.AmqpTemplate
import spock.lang.Specification

class QueueSenderTest extends Specification {
    EnhancedRandom random = EnhancedRandomBuilder. aNewEnhancedRandom()
    AmqpTemplate amqpTemplate
    ObjectMapper objectMapper
    QueueSender queueSender

    def setup(){
        amqpTemplate = Mock()
        objectMapper = Mock()
        queueSender = new QueueSender(amqpTemplate, objectMapper)
    }

    def "TestSendToQueue" (){
        given:
        def request = random.nextObject(SubscriptionQueueDto)
        def queueName = random.nextObject(String)

        when:
        queueSender.sendToQueue(queueName, request)

        then:
        1 * amqpTemplate.convertAndSend(queueName, objectMapper.writeValueAsString(request))
    }

}
