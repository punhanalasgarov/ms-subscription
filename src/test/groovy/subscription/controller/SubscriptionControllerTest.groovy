package com.ingress.ms.subscription.controller

import com.ingress.ms.subscription.model.enums.SubscriptionType
import com.ingress.ms.subscription.model.reuqest.CreateSubscriptionRequestDto
import org.springframework.http.HttpStatus
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import com.ingress.ms.subscription.exception.GeneralExceptionHandler
import com.ingress.ms.subscription.service.SubscriptionService
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import spock.lang.Specification

import static org.springframework.http.MediaType.APPLICATION_JSON


class SubscriptionControllerTest extends Specification {

    SubscriptionService subscriptionService
    MockMvc mockMvc

    void setup(){
        subscriptionService = Mock()
        def subscriptionController = new SubscriptionController(subscriptionService)
        mockMvc = MockMvcBuilders.standaloneSetup(subscriptionController)
                .setControllerAdvice(new GeneralExceptionHandler())
                .build()

    }

    def "TestCreateSubscription" () {
        given:
        def url = "/v1/subscriptions"
        def subscriptionRequest = new CreateSubscriptionRequestDto(1, 2, SubscriptionType.WEEKLY)
        def jsonRequest = '''
                                     {
                                        "userId" : "1",
                                        "productId" : "2",
                                        "subscriptionType" : "WEEKLY"
                                      }
                                  '''

        when:
        def result = mockMvc.perform(MockMvcRequestBuilders.post(url)
                .contentType(APPLICATION_JSON)
                .content(jsonRequest))
                .andReturn()

        then:
        1 * subscriptionService.createSubscription(subscriptionRequest)
        result.response.status == HttpStatus.CREATED.value()
    }

}


