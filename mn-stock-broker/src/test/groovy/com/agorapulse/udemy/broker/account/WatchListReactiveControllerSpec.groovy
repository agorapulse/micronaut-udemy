package com.agorapulse.udemy.broker.account

import com.agorapulse.udemy.broker.model.Symbol
import com.agorapulse.udemy.broker.model.WatchList
import com.agorapulse.udemy.broker.store.InMemoryAccountStore
import io.micronaut.http.HttpResponse
import io.micronaut.http.HttpStatus
import io.micronaut.http.client.annotation.Client
import io.micronaut.runtime.EmbeddedApplication
import io.micronaut.security.authentication.UsernamePasswordCredentials
import io.micronaut.security.token.jwt.render.BearerAccessRefreshToken
import io.micronaut.test.extensions.spock.annotation.MicronautTest
import io.reactivex.Single
import spock.lang.Specification

import javax.inject.Inject

import static io.micronaut.http.HttpRequest.*

@MicronautTest
class WatchListReactiveControllerSpec extends Specification {

    private static final UUID TEST_ACCOUNT_ID = WatchListReactiveController.ACCOUNT_ID
    private static final List TEST_SYMBOLS = ['AAPL', 'AMZN', 'GOOG'].collect({new Symbol(value: it)})

    @Inject
    EmbeddedApplication application

    @Inject
    @Client('') JWTWatchListClient client

    @Inject InMemoryAccountStore store

    void 'get empty watchlist'() {
        when:
        WatchList result = client.retrieveWatchList(getAuthorizationHeader()).singleOrError().blockingGet()
        then:
        result.symbols.size() == 0
    }

    void 'get watchlist'() {
        given:
        WatchList watchList = new WatchList(symbols: TEST_SYMBOLS)
        store.updateWatchList(TEST_ACCOUNT_ID, watchList)
        when:
        WatchList result = client.retrieveWatchList(getAuthorizationHeader()).singleOrError().blockingGet()
        then:
        result.symbols.size() == 3
    }

    void 'get watchlist as single'() {
        given:
        WatchList watchList = new WatchList(symbols: TEST_SYMBOLS)
        store.updateWatchList(TEST_ACCOUNT_ID, watchList)
        when:
        WatchList result = client.retrieveWatchListAsSingle(getAuthorizationHeader()).blockingGet()
        then:
        result.symbols.size() == 3
    }

    void 'update watchlist'() {
        given:
        WatchList watchList = new WatchList(symbols: TEST_SYMBOLS)
        when:
        HttpResponse<WatchList> response = client.updateWatchList(getAuthorizationHeader(), watchList)
        then:
        response.status == HttpStatus.OK
        store.getWatchList(TEST_ACCOUNT_ID).symbols*.value == TEST_SYMBOLS*.value
    }

    void 'delete watchlist'() {
        given:
        WatchList watchList = new WatchList(symbols: TEST_SYMBOLS)
        store.updateWatchList(TEST_ACCOUNT_ID, watchList)
        when:
        HttpResponse response = client.deleteWatchList(getAuthorizationHeader(), TEST_ACCOUNT_ID)
        then:
        response.status == HttpStatus.OK
        store.getWatchList(TEST_ACCOUNT_ID).symbols.size() == 0
    }

    private String getAuthorizationHeader() {
        "Bearer ${givenMyUserIsLoggedIn().accessToken}"
    }

    private BearerAccessRefreshToken givenMyUserIsLoggedIn() {
        client.login(new UsernamePasswordCredentials('my-user', 'secret'))
    }

}
