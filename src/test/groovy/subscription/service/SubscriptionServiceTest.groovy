package subscription.service

import com.ingress.ms.subscription.dao.entity.SubscriptionEntity
import com.ingress.ms.subscription.dao.repository.SubscriptionRepository
import com.ingress.ms.subscription.exception.NotFoundException
import com.ingress.ms.subscription.mapper.SubscriptionMapper
import com.ingress.ms.subscription.model.enums.SubscriptionType
import com.ingress.ms.subscription.model.queue.SubscriptionQueueDto
import com.ingress.ms.subscription.model.reuqest.CreateSubscriptionRequestDto
import com.ingress.ms.subscription.model.reuqest.SubscriptionTypeRequestDto
import com.ingress.ms.subscription.queue.QueueSender
import com.ingress.ms.subscription.service.SubscriptionService
import io.github.benas.randombeans.EnhancedRandomBuilder
import io.github.benas.randombeans.api.EnhancedRandom
import spock.lang.Specification

import java.time.LocalDateTime

import static com.ingress.ms.subscription.model.enums.ErrorCode.SUBSCRIPTION_NOT_FOUND_EXCEPTION
import static com.ingress.ms.subscription.model.enums.SubscriptionStatus.ACTIVE

class SubscriptionServiceTest extends Specification {
    private EnhancedRandom random = EnhancedRandomBuilder. aNewEnhancedRandom()
    private SubscriptionRepository subscriptionRepository
    private QueueSender queueSender
    private SubscriptionService subscriptionService

    def setup(){
        queueSender = Mock()
        subscriptionRepository = Mock()
        subscriptionService = new SubscriptionService(subscriptionRepository, queueSender)
    }


    def "TestSubscriptionsStatusChange success case" () {
        given:
        def expiredSubscriptionsList = random.nextObject(List<SubscriptionEntity>)
        def expiredSubscription = random.nextObject(SubscriptionEntity)
        expiredSubscriptionsList.add(expiredSubscription)

        def queueName = "SUBSCRIPTION_Q"

        when:
        subscriptionService.subscriptionsStatusChange()

        then:
        1 * subscriptionRepository.findAllByExpireDateEnded() >> Optional.of(expiredSubscriptionsList)
        1 * subscriptionRepository.saveAll(expiredSubscriptionsList)
    }

    def "TestSubscriptionsStatusChange subscription not found case" () {
        when:
        subscriptionService.subscriptionsStatusChange()

        then:
        1 * subscriptionRepository.findAllByExpireDateEnded() >> Optional.empty()
        NotFoundException ex = thrown()
        ex.message == SUBSCRIPTION_NOT_FOUND_EXCEPTION.description
    }


    def "TestExpireDate success case" () {
        given:
        def request = random.nextObject(SubscriptionTypeRequestDto)
        def response = random.nextObject(LocalDateTime)

        when:
        subscriptionService.expireDate(request)

        then:
        response instanceof LocalDateTime
    }

}
