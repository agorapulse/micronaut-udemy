package com.agorapulse.udemy.broker

import com.agorapulse.udemy.broker.model.Symbol
import io.micronaut.http.client.RxHttpClient
import io.micronaut.http.client.annotation.Client
import io.micronaut.runtime.EmbeddedApplication
import io.micronaut.test.extensions.spock.annotation.MicronautTest
import spock.lang.Specification

import javax.inject.Inject

@MicronautTest
class MarketsControllerSpec extends Specification {

    @Inject
    EmbeddedApplication application

    @Inject
    @Client("/") RxHttpClient client

    void 'test symbols'() {
        when:
        List<Symbol> result = client.toBlocking().retrieve("/markets", List<Symbol>)
        then:
        result.size() == 3
        result.collect({it.value}) == ['AAPL', 'AMZN', 'GOOG']
    }

}
