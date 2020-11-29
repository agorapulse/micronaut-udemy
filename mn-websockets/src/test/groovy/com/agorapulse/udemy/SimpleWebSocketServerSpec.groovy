package com.agorapulse.udemy

import io.micronaut.http.client.annotation.Client
import io.micronaut.test.extensions.spock.annotation.MicronautTest
import io.micronaut.websocket.RxWebSocketClient
import spock.lang.Specification

import javax.inject.Inject

@MicronautTest
class SimpleWebSocketServerSpec extends Specification {

    @Inject
    @Client('http://localhost:8180')
    RxWebSocketClient client

    SimpleWebSocketClient webSocketClient

    def setup() {
        webSocketClient = client.connect(SimpleWebSocketClient, '/ws/simple/prices').blockingFirst()
    }

    void 'receive message with client'() {
        when:
        webSocketClient.send('hello')
        final List messages = webSocketClient.observedMessages.toArray()
        then:
        // Did not check to use Awaitility
        messages.size() > 0
        messages.first() == 'Connected!'
    }

    void 'receive message with client reactively'() {
        given:
        final SimpleWebSocketClient webSocketClient = client.connect(SimpleWebSocketClient, '/ws/simple/prices')
                .blockingFirst()
        when:
        webSocketClient.sendReactive('hello').blockingGet()
        final List messages = webSocketClient.observedMessages.toArray()
        then:
        // Did not check to use Awaitility
        messages.size() > 0
        messages.first() == 'Connected!'
    }

}
