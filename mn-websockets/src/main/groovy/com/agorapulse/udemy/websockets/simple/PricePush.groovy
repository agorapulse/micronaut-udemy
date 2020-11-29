package com.agorapulse.udemy.websockets.simple

import io.micronaut.scheduling.annotation.Scheduled
import io.micronaut.websocket.WebSocketBroadcaster

import javax.inject.Singleton
import java.util.concurrent.ThreadLocalRandom

@Singleton
class PricePush {

    private final WebSocketBroadcaster broadcaster

    PricePush(WebSocketBroadcaster broadcaster) {
        this.broadcaster = broadcaster
    }

    @Scheduled(fixedDelay =  '5s')
    void push() {
        broadcaster.broadcastSync(
                new PriceUpdate(symbol: 'AMZN', price: randomValue())
        )
    }

    private BigDecimal randomValue() {
        BigDecimal.valueOf(ThreadLocalRandom.current().nextDouble(1500, 2000))
    }
}
