package subscription.service

import com.ingress.ms.subscription.dao.entity.SubscriptionEntity
import com.ingress.ms.subscription.dao.repository.SubscriptionRepository
import com.ingress.ms.subscription.model.queue.SubscriptionQueueDto
import com.ingress.ms.subscription.model.reuqest.CreateSubscriptionRequestDto
import com.ingress.ms.subscription.queue.QueueSender
import com.ingress.ms.subscription.service.SubscriptionService
import io.github.benas.randombeans.EnhancedRandomBuilder
import io.github.benas.randombeans.api.EnhancedRandom
import org.springframework.beans.factory.annotation.Value
import org.springframework.test.context.TestPropertySource
import spock.lang.Specification
import static com.ingress.ms.subscription.model.enums.SubscriptionStatus.ACTIVE
import static com.ingress.ms.subscription.model.enums.SubscriptionStatus.EXPIRED
import static com.ingress.ms.subscription.model.enums.SubscriptionType.WEEKLY

@TestPropertySource("/application.yml")
class SubscriptionServiceTest extends Specification {
    @Value('${rabbitmq.subscriptions.queue}')
    String queueName;

    EnhancedRandom random = EnhancedRandomBuilder. aNewEnhancedRandom()
    SubscriptionRepository subscriptionRepository
    QueueSender queueSender
    SubscriptionService subscriptionService

    def setup(){
        queueSender = Mock()
        subscriptionRepository = Mock()
        subscriptionService = new SubscriptionService(subscriptionRepository, queueSender)
    }

    def "TestCreateSubscription success case" () {
        given:

        def request = CreateSubscriptionRequestDto
                .builder()
                .userId(1L)
                .productId(1L)
                .subscriptionType(WEEKLY)
                .build()

        def entity = SubscriptionEntity
                .builder()
                .userId(request.userId)
                .productId(request.productId)
                .type(request.subscriptionType)
                .status(ACTIVE)
                .build()

        def subscriptionQueueDto = SubscriptionQueueDto
            .builder()
            .userId(entity.userId)
            .productId(entity.productId)
            .subscriptionType(entity.type)
            .subscriptionStatus(entity.status)
            .build()

        when:
        subscriptionService.createSubscription(request)

        then:
        1 * subscriptionRepository.save(entity) >> entity
        1 * queueSender.sendToQueue(queueName,  Collections.singletonList(subscriptionQueueDto))
    }

    def "TestPublishQueue success case" (){
        given:

        def entity = SubscriptionEntity
                .builder()
                .id(1L)
                .userId(1L)
                .productId(1L)
                .type(WEEKLY)
                .status(ACTIVE)
                .build()

        def subscriptionQueueDto = SubscriptionQueueDto
                .builder()
                .userId(entity.userId)
                .productId(entity.productId)
                .subscriptionType(entity.type)
                .subscriptionStatus(entity.status)
                .build()

        when:
        subscriptionService.publishQueue(entity)

        then:
        1 * queueSender.sendToQueue(queueName, Collections.singletonList(subscriptionQueueDto))
    }

    def "TestSubscriptionsStatusChange success case" () {
        given:
        def entity = SubscriptionEntity
                .builder()
                .userId(1L)
                .productId(1L)
                .type(WEEKLY)
                .status(ACTIVE)
                .build()

        def expiredSubscriptionsList = Arrays.asList(entity)

        def queueDto = SubscriptionQueueDto
                .builder()
                .userId(entity.userId)
                .productId(entity.productId)
                .subscriptionType(WEEKLY)
                .subscriptionStatus(EXPIRED)
                .build()

        def subscriptionQueueDtoList = Arrays.asList(queueDto)

        when:
        subscriptionService.subscriptionsStatusChange()

        then:
        1 * subscriptionRepository.findAllByExpireDateEnded() >> expiredSubscriptionsList
        1 * subscriptionRepository.saveAll(expiredSubscriptionsList)
        1 * queueSender.sendToQueue(queueName, subscriptionQueueDtoList)
    }
}
