package com.agorapulse.udemy.data.jdbc

import groovy.util.logging.Slf4j
import io.micronaut.scheduling.annotation.Scheduled

import javax.inject.Singleton
import java.util.concurrent.ThreadLocalRandom

@Slf4j
@Singleton
class DummyDataProvider {

    private final TransactionsRepository transactionsRepository

    DummyDataProvider(TransactionsRepository transactionsRepository) {
        this.transactionsRepository = transactionsRepository
    }

    @Scheduled(fixedDelay = '1s')
    void generate() {
        transactionsRepository.save(new TransactionEntity(
                user: UUID.randomUUID().toString(),
                symbol: 'SYMBOL',
                modification: randomValue()
        ))
        log.info("Content ${transactionsRepository.count()}")
    }

    private BigDecimal randomValue() {
        BigDecimal.valueOf(ThreadLocalRandom.current().nextDouble(1, 100))
    }
}
