package com.agorapulse.udemy.broker.account

import com.agorapulse.udemy.broker.model.WatchList
import com.agorapulse.udemy.broker.store.InMemoryAccountStore
import groovy.util.logging.Slf4j
import io.micronaut.http.MediaType
import io.micronaut.http.annotation.*
import io.micronaut.scheduling.TaskExecutors
import io.micronaut.scheduling.annotation.ExecuteOn
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers

import javax.inject.Named
import java.util.concurrent.ExecutorService

@Slf4j
@Controller('/account/watchlist-reactive')
class WatchListReactiveController {

    static final UUID ACCOUNT_ID = UUID.randomUUID()
    private final InMemoryAccountStore store
    private final scheduler

    WatchListReactiveController(
            @Named(TaskExecutors.IO) ExecutorService executorService,
            InMemoryAccountStore store
    ) {
        this.store = store
        this.scheduler = Schedulers.from(executorService)
    }

    @Get(produces = MediaType.APPLICATION_JSON ) // Not really required since default, for demo purpose
    @ExecuteOn(TaskExecutors.IO) // IO event loop should never be blocked, so for blocking operations, we should use a separate thread from the pool
    WatchList get() {
        log.debug("Get watchlist - ${Thread.currentThread().name}") // Will return io-executor-thread-1 instead of default-nioEventLoopGroup-1-3
        store.getWatchList(ACCOUNT_ID)
    }

    // Alternative to @ExecuteOn annotation, by using injected executorService scheduler
    @Get(
            value = '/single',
            produces = MediaType.APPLICATION_JSON // Not really required since default, for demo purpose
    )
    Single<WatchList> getAsSingle() { // Could be Flowable as well to return a stream of elements (instead of a single one)
        log.debug("Get watchlist as single - ${Thread.currentThread().name}") // Will return default-nioEventLoopGroup-1-3 (which should never be blocked)
        Single.fromCallable(() -> {
                log.debug("Get watchlist as single from callable - ${Thread.currentThread().name}")
                store.getWatchList(ACCOUNT_ID)
        }).subscribeOn(scheduler) // Specify which thread pool to execute on
    }

    @Put(
            consumes = MediaType.APPLICATION_JSON,
            produces = MediaType.APPLICATION_JSON
    ) // Not really required since default, for demo purpose
    @ExecuteOn(TaskExecutors.IO)
    WatchList update(@Body WatchList watchList) {
        store.updateWatchList(ACCOUNT_ID, watchList)
    }

    @Delete(
            value = '/{accountId}',
            consumes = MediaType.APPLICATION_JSON,
            produces = MediaType.APPLICATION_JSON
    )
    @ExecuteOn(TaskExecutors.IO)
    void delete(@PathVariable UUID accountId) {
        store.deleteWatchList(accountId)
    }

}
