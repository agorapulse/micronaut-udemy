package com.agorapulse.udemy.broker

import com.agorapulse.udemy.broker.error.CustomError
import com.agorapulse.udemy.broker.model.Quote
import com.agorapulse.udemy.broker.store.InMemoryQuoteStore
import groovy.util.logging.Slf4j
import io.micronaut.http.HttpResponse
import io.micronaut.http.HttpStatus
import io.micronaut.http.MediaType
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.PathVariable
import io.micronaut.security.annotation.Secured
import io.micronaut.security.rules.SecurityRule
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.tags.Tag

@Slf4j
@Secured(SecurityRule.IS_ANONYMOUS)
@Controller('/quotes')
class QuotesController {

    private final InMemoryQuoteStore store

    QuotesController(InMemoryQuoteStore store) {
        this.store = store
    }

    @Operation(summary = 'Returns a quote for the given symbol.')
    @ApiResponse(
            content = @Content(mediaType =  MediaType.APPLICATION_JSON)
    )
    @ApiResponse(responseCode = '400', description = 'Invalid symbol specified')
    @Tag(name = 'quotes')
    @Get('/{symbol}')
    HttpResponse getQuote(@PathVariable String symbol) {
        Optional<Quote> maybeQuote = store.fetchQuote(symbol)
        log.debug('Get quote', maybeQuote)
        if (!maybeQuote.present) {
            CustomError error = new CustomError(
                    error: HttpStatus.NOT_FOUND,
                    path: "/quote/$symbol",
                    message: 'Unknown quote',
                    status: HttpStatus.NOT_FOUND.code
            )
            return HttpResponse.notFound(error)
        }
        HttpResponse.ok(maybeQuote.get())
    }
}
