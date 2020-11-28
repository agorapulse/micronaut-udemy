package com.agorapulse.udemy.broker

import com.agorapulse.udemy.broker.error.CustomError
import com.agorapulse.udemy.broker.model.Quote
import com.agorapulse.udemy.broker.persistence.jpa.QuotesRepository
import com.agorapulse.udemy.broker.persistence.model.QuoteDTO
import com.agorapulse.udemy.broker.persistence.model.QuoteEntity
import com.agorapulse.udemy.broker.persistence.model.SymbolEntity
import com.agorapulse.udemy.broker.store.InMemoryQuoteStore
import groovy.util.logging.Slf4j
import io.micronaut.data.model.Pageable
import io.micronaut.http.HttpResponse
import io.micronaut.http.HttpStatus
import io.micronaut.http.MediaType
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.PathVariable
import io.micronaut.http.annotation.QueryValue
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
    private final QuotesRepository quotesRepository

    QuotesController(InMemoryQuoteStore store, QuotesRepository quotesRepository) {
        this.store = store
        this.quotesRepository = quotesRepository
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

    @Get('/jpa')
    List<QuoteEntity> getAllQuotesViaJPA() {
        quotesRepository.findAll()
    }

    @Operation(summary = 'Returns a quote for the given symbol. Fetched from the database via JPA')
    @ApiResponse(
            content = @Content(mediaType =  MediaType.APPLICATION_JSON)
    )
    @ApiResponse(responseCode = '400', description = 'Invalid symbol specified')
    @Tag(name = 'quotes')
    @Get('/{symbol}/jpa')
    HttpResponse getQuoteViaJPA(@PathVariable String symbol) {
        Optional<QuoteEntity> maybeQuote = quotesRepository.findBySymbol(new SymbolEntity(value: symbol))
        log.debug('Get quote', maybeQuote)
        if (!maybeQuote.present) {
            CustomError error = new CustomError(
                    error: HttpStatus.NOT_FOUND,
                    path: "/quote/$symbol/jpa",
                    message: 'Unknown quote',
                    status: HttpStatus.NOT_FOUND.code
            )
            return HttpResponse.notFound(error)
        }
        HttpResponse.ok(maybeQuote.get())
    }

    @Get('/jpa/ordered/desc')
    List<QuoteDTO> orderedDesc() {
        quotesRepository.listOrderByVolumeDesc()
    }

    @Get('/jpa/ordered/asc')
    List<QuoteDTO> orderedAsc() {
        quotesRepository.listOrderByVolumeAsc()
    }

    @Get('/jpa/volume/{volume}')
    List<QuoteDTO> volumeFilter(@PathVariable BigDecimal volume) {
        quotesRepository.findByVolumeGreaterThanOrderByVolumeAsc(volume)
    }

    @Get('/jpa/pagination{?page,volume}')
    List<QuoteDTO> volumeFilterPagination(@QueryValue Optional<Integer> page, @QueryValue Optional<BigDecimal> volume) {
        int myPage = page.present ? page.get() : 0
        BigDecimal myVolume = volume.present ? volume.get() : BigDecimal.ZERO
        quotesRepository.findByVolumeGreaterThan(myVolume, Pageable.from(myPage, 2))
    }

    @Get('/jpa/pagination/{page}')
    List<QuoteDTO> allWithPagination(@PathVariable int page) {
        quotesRepository.list(Pageable.from(page, 5)).getContent()
    }


}
