package com.agorapulse.udemy

import groovy.util.logging.Slf4j
import io.micronaut.websocket.WebSocketSession
import io.micronaut.websocket.annotation.ClientWebSocket
import io.micronaut.websocket.annotation.OnMessage
import io.micronaut.websocket.annotation.OnOpen
import io.reactivex.Single

import java.util.concurrent.ConcurrentLinkedQueue

@Slf4j
@ClientWebSocket('/ws/simple/prices')
abstract class SimpleWebSocketClient implements AutoCloseable {

    private final Collection<String> observedMessages = new ConcurrentLinkedQueue<>()
    private WebSocketSession session

    @OnOpen
    void onOpen(WebSocketSession session) {
        log.debug "onOpen: ${session.id}"
        this.session = session
    }

    @OnMessage
    void onMessage(String message) {
        observedMessages.add(message)
        log.debug "onMessage: ${message} (total: ${observedMessages.size()})"
    }

    abstract void send(String message)

    abstract Single<String> sendReactive(String message)

    Collection<String> getObservedMessages() {
        observedMessages
    }

    void clearMessages() {
        observedMessages.clear()
    }

    WebSocketSession getSession() {
        return session;
    }

}
