package com.agorapulse.udemy.broker

import com.agorapulse.udemy.broker.model.Symbol
import com.agorapulse.udemy.broker.store.InMemoryQuoteStore
import io.micronaut.http.MediaType
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.security.annotation.Secured
import io.micronaut.security.rules.SecurityRule
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.tags.Tag

@Secured(SecurityRule.IS_ANONYMOUS)
@Controller('/markets')
class MarketsController {

    private final InMemoryQuoteStore store

    MarketsController(InMemoryQuoteStore store) {
        this.store = store
    }

    @Operation(summary = 'Returns all available markets')
    @ApiResponse(
            content = @Content(mediaType =  MediaType.APPLICATION_JSON)
    )
    @Tag(name = 'markets')
    @Get('/')
    List<Symbol> all() {
        store.getAllSymbols()
    }

}

