package com.agorapulse.udemy.websockets.simple

import groovy.util.logging.Slf4j
import io.micronaut.websocket.CloseReason
import io.micronaut.websocket.WebSocketSession
import io.micronaut.websocket.annotation.OnClose
import io.micronaut.websocket.annotation.OnMessage
import io.micronaut.websocket.annotation.OnOpen
import io.micronaut.websocket.annotation.ServerWebSocket
import io.reactivex.Flowable
import org.reactivestreams.Publisher

@Slf4j
@ServerWebSocket('/ws/simple/prices')
class SimpleWebSocketServer {

    @OnOpen
    Publisher<String> onOpen(WebSocketSession session) {
        session.send('Connected!')
    }

    @OnMessage
    Publisher<String> onMessage(String message, WebSocketSession session) {
        log.info("Received message: ${message} from session ${session.id}")
        if (message == 'disconnected me') {
            log.info('Client close request')
            session.close(CloseReason.NORMAL)
            return Flowable.empty()
        }
        session.send("Not supported => (${message})")
    }

    @OnClose
    void onClose(WebSocketSession session) {
        log.info("Session closed ${session.id}")
    }

}
