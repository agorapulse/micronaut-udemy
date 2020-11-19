package com.agorapulse.udemy.broker

import com.agorapulse.udemy.broker.error.CustomError
import com.agorapulse.udemy.broker.model.Quote
import com.agorapulse.udemy.broker.store.InMemoryQuoteStore
import io.micronaut.http.client.RxHttpClient
import io.micronaut.http.client.annotation.Client
import io.micronaut.http.client.exceptions.HttpClientResponseException
import io.micronaut.runtime.EmbeddedApplication
import io.micronaut.test.extensions.spock.annotation.MicronautTest
import spock.lang.Specification

import javax.inject.Inject

@MicronautTest
class QuotesControllerSpec extends Specification {

    @Inject
    EmbeddedApplication application

    @Inject
    @Client("/") RxHttpClient client

    @Inject InMemoryQuoteStore store

    void 'get quote'() {
        given:
        String appleSymbol = 'AAPL'
        Optional<Quote> optionalQuote = store.fetchQuote(appleSymbol)
        when:
        Quote result = client.toBlocking().retrieve("/quotes/${appleSymbol}", Quote)
        then:
        result.symbol.value == appleSymbol
        result.ask == optionalQuote.get().ask
    }

    void 'get unsupported quote'() {
        given:
        String unsupportedSymbol = 'UNSUPPORTED'
        when:
        client.toBlocking().retrieve("/quotes/${unsupportedSymbol}", Quote, CustomError)
        then:
        thrown(HttpClientResponseException)
    }

}
