package subscription.mapper

import com.ingress.ms.subscription.dao.entity.SubscriptionEntity
import com.ingress.ms.subscription.mapper.SubscriptionMapper
import com.ingress.ms.subscription.model.reuqest.CreateSubscriptionRequestDto
import io.github.benas.randombeans.EnhancedRandomBuilder
import io.github.benas.randombeans.api.EnhancedRandom
import spock.lang.Specification


class SubscriptionMapperTest extends Specification {
    EnhancedRandom random = EnhancedRandomBuilder.aNewEnhancedRandom()

    def "TestBuildSubscriptionEntity" () {

        given:
        def source = random.nextObject(CreateSubscriptionRequestDto)

        when:
        def actual = SubscriptionMapper.buildSubscriptionEntity(source)

        then:
        actual.userId == source.userId
        actual.productId == source.productId
        actual.type == source.subscriptionType
    }

    def "TestBuildSubscriptionQueueDto" (){
        given:
        def source = random.nextObject(SubscriptionEntity)

        when:
        def actual = SubscriptionMapper.buildSubscriptionQueueDto(source)

        then:
        actual.userId == source.userId
        actual.productId == source.productId
        actual.subscriptionType == source.type
        actual.subscriptionStatus == source.status
    }

    def "TestBuildSubscriptionQueueDto " (){
        given:
        def source = random.nextObject(List<SubscriptionEntity>)

        when:
        def actual = SubscriptionMapper.buildSubscriptionQueueDto(source)

        then:
        actual.userId == source.userId
        actual.productId == source.productId
        actual.subscriptionType == source.type
        actual.subscriptionStatus == source.status
    }
}
