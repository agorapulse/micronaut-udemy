package com.agorapulse.udemy.broker

import com.agorapulse.udemy.broker.model.Symbol
import com.agorapulse.udemy.broker.persistence.model.SymbolEntity
import com.agorapulse.udemy.broker.persistence.jpa.SymbolsRepository
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
    private final SymbolsRepository symbolsRepository

    MarketsController(InMemoryQuoteStore store, SymbolsRepository symbolsRepository) {
        this.store = store
        this.symbolsRepository = symbolsRepository
    }

    @Operation(summary = 'Returns all available markets')
    @ApiResponse(
            content = @Content(mediaType =  MediaType.APPLICATION_JSON)
    )
    @Tag(name = 'markets')
    @Get('/')
    List<Symbol> index() {
        store.getAllSymbols()
    }

    @Operation(summary = 'Returns all available markets from database via JPA')
    @ApiResponse(
            content = @Content(mediaType =  MediaType.APPLICATION_JSON)
    )
    @Tag(name = 'markets')
    @Get('/jpa')
    List<SymbolEntity> indexJPA() {
        symbolsRepository.findAll()
    }

}

