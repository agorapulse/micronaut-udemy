package com.agorapulse.udemy.broker.account

import com.agorapulse.udemy.broker.model.WatchList
import com.agorapulse.udemy.broker.store.InMemoryAccountStore
import groovy.util.logging.Slf4j
import io.micronaut.http.MediaType
import io.micronaut.http.annotation.*
import io.micronaut.security.annotation.Secured
import io.micronaut.security.rules.SecurityRule

@Slf4j
@Secured(SecurityRule.IS_AUTHENTICATED)
@Controller('/account/watchlist')
class WatchListController {

    static final UUID ACCOUNT_ID = UUID.randomUUID()
    private final InMemoryAccountStore store

    WatchListController(InMemoryAccountStore store) {
        this.store = store
    }

    @Get(produces = MediaType.APPLICATION_JSON ) // Not really required since default, for demo purpose
    WatchList get() {
        log.debug("Get watchlist - ${Thread.currentThread().name}") // Will return default-nioEventLoopGroup-1-3 (which should never be blocked)
        store.getWatchList(ACCOUNT_ID)
    }

    @Put(
            consumes = MediaType.APPLICATION_JSON,
            produces = MediaType.APPLICATION_JSON
    ) // Not really required since default, for demo purpose
    WatchList update(@Body WatchList watchList) {
        store.updateWatchList(ACCOUNT_ID, watchList)
    }

    @Delete(
            value = '/{accountId}',
            consumes = MediaType.APPLICATION_JSON,
            produces = MediaType.APPLICATION_JSON
    )
    void delete(@PathVariable UUID accountId) {
        store.deleteWatchList(accountId)
    }

}
