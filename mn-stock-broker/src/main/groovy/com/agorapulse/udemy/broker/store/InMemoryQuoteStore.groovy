package com.agorapulse.udemy.broker.store

import com.agorapulse.udemy.broker.model.Quote
import com.agorapulse.udemy.broker.model.Symbol

import javax.inject.Singleton
import java.util.concurrent.ThreadLocalRandom

@Singleton
class InMemoryQuoteStore {

    private List<Symbol> symbols
    Map<String, Quote> cachedQuotes = [:]
    private ThreadLocalRandom current = ThreadLocalRandom.current()

    InMemoryQuoteStore() {
        this.symbols = ['AAPL', 'AMZN', 'GOOG'].collect({new Symbol(value: it)})
        this.symbols.each {
            cachedQuotes[it.value] = randomQuote(it)
        }
    }

    List<Symbol> getAllSymbols() {
        symbols
    }

    Optional<Quote> fetchQuote(final String symbol) {
        Optional.ofNullable(cachedQuotes[symbol])
    }

    void update(Quote quote) {
        cachedQuotes[quote.symbol.value] = quote
    }

    // PRIVATE

    private Quote randomQuote(Symbol symbol) {
        new Quote(
                symbol: symbol,
                ask: randomValue(),
                bid: randomValue(),
                lastPrice: randomValue(),
                volume: randomValue()
        )
    }

    private BigDecimal randomValue() {
        current.nextDouble(1, 100) as BigDecimal
    }
}
