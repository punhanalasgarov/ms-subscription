package subscription.mapper

import com.ingress.ms.subscription.mapper.SubscriptionMapper
import com.ingress.ms.subscription.model.reuqest.CreateSubscriptionRequestDto
import io.github.benas.randombeans.EnhancedRandomBuilder
import io.github.benas.randombeans.api.EnhancedRandom
import spock.lang.Specification

import java.time.LocalDateTime

class SubscriptionMapperTest extends Specification {
    private EnhancedRandom random = EnhancedRandomBuilder.aNewEnhancedRandom();

    def "TestBuildSubscriptionEntity" () {

        given:
        def source = random.nextObject(CreateSubscriptionRequestDto)
        def expireDate = random.nextObject(LocalDateTime)

        when:
        def actual = SubscriptionMapper.buildSubscriptionEntity(source, expireDate)

        then:
        actual.userId == source.userId
        actual.productId == source.productId
        actual.type.name() == source.subscriptionType.name()
    }

    def "TestBuildSubscriptionQueueDto" (){
        given:
        def source = random.nextObject(CreateSubscriptionRequestDto)

        when:
        def actual = SubscriptionMapper.buildSubscriptionQueueDto(source)

        then:
        actual.userId == source.userId
        actual.productId == source.productId
        actual.subscriptionType == source.subscriptionType.name()
    }
}
