package com.agorapulse.udemy.broker.persistence.jpa

import com.agorapulse.udemy.broker.persistence.model.QuoteEntity
import com.agorapulse.udemy.broker.persistence.model.SymbolEntity
import groovy.util.logging.Slf4j
import io.micronaut.context.annotation.Requires
import io.micronaut.context.env.Environment
import io.micronaut.context.event.StartupEvent
import io.micronaut.runtime.event.annotation.EventListener

import javax.inject.Singleton
import java.util.concurrent.ThreadLocalRandom

/**
 * Used to insert data into db on startup
 */
@Slf4j
@Singleton
@Requires(notEnv = Environment.TEST)
class TestDataProvider {

    private static final ThreadLocalRandom RANDOM = ThreadLocalRandom.current()
    private final SymbolsRepository symbolsRepository
    private final QuotesRepository quotesRepository

    TestDataProvider(SymbolsRepository symbolsRepository, QuotesRepository quotesRepository) {
        this.symbolsRepository = symbolsRepository
        this.quotesRepository = quotesRepository
    }

    @EventListener
    void init(StartupEvent event) {
        if (!symbolsRepository.findAll()) {
            log.info('Adding test symbol data as empty database was found!!!')
            ['AAPL', 'AMZN', 'FB', 'TSLA'].each {
                symbolsRepository.save(new SymbolEntity(value: it))
            }
        }
        if (!quotesRepository.findAll()) {
            log.info('Adding test quotes data as empty database was found!!!')
            symbolsRepository.findAll().each {symbol ->
                QuoteEntity quote = new QuoteEntity(
                        symbol: symbol,
                        ask: randomValue(),
                        bid: randomValue(),
                        lastPrice: randomValue(),
                        volume: randomValue()
                )
                quotesRepository.save(quote)
            }
        }
    }

    private BigDecimal randomValue() {
        BigDecimal.valueOf(RANDOM.nextDouble(1, 100))
    }
}
