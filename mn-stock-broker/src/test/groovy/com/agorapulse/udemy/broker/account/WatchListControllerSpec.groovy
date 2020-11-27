package com.agorapulse.udemy.broker.account

import com.agorapulse.udemy.broker.model.Symbol
import com.agorapulse.udemy.broker.model.WatchList
import com.agorapulse.udemy.broker.store.InMemoryAccountStore
import groovy.util.logging.Slf4j
import io.micronaut.http.HttpResponse
import io.micronaut.http.HttpStatus
import io.micronaut.http.MediaType
import io.micronaut.http.client.RxHttpClient
import io.micronaut.http.client.annotation.Client
import io.micronaut.http.client.exceptions.HttpClientResponseException
import io.micronaut.runtime.EmbeddedApplication
import io.micronaut.security.authentication.UsernamePasswordCredentials
import io.micronaut.security.token.jwt.render.BearerAccessRefreshToken
import io.micronaut.test.extensions.spock.annotation.MicronautTest
import spock.lang.Specification

import javax.inject.Inject

import static io.micronaut.http.HttpRequest.*

@Slf4j
@MicronautTest
class WatchListControllerSpec extends Specification {

    private static final UUID TEST_ACCOUNT_ID = WatchListController.ACCOUNT_ID
    private static final List TEST_SYMBOLS = ['AAPL', 'AMZN', 'GOOG'].collect({new Symbol(value: it)})

    @Inject
    EmbeddedApplication application

    @Inject
    @Client('/') RxHttpClient client

    @Inject InMemoryAccountStore store

    void 'get unauthorized()'() {
        when:
        client.toBlocking().retrieve('/account/watchlist')
        then:
        final HttpClientResponseException exception = thrown()
        exception.status == HttpStatus.UNAUTHORIZED
    }

    void 'get empty watchlist'() {
        given:
        BearerAccessRefreshToken token = givenMyUserIsLoggedIn()

        when:
        def request = GET('/account/watchlist')
                .accept(MediaType.APPLICATION_JSON)
                .bearerAuth(token.accessToken)
        WatchList result = client.toBlocking().retrieve(request, WatchList)
        then:
        result.symbols.size() == 0
    }

    void 'get watchlist'() {
        given:
        BearerAccessRefreshToken token = givenMyUserIsLoggedIn()
        WatchList watchList = new WatchList(symbols: TEST_SYMBOLS)
        store.updateWatchList(TEST_ACCOUNT_ID, watchList)
        when:
        def request = GET('/account/watchlist')
                .accept(MediaType.APPLICATION_JSON)
                .bearerAuth(token.accessToken)
        WatchList result = client.toBlocking().retrieve(request, WatchList)
        then:
        result.symbols.size() == 3
    }

    void 'update watchlist'() {
        given:
        BearerAccessRefreshToken token = givenMyUserIsLoggedIn()
        WatchList watchList = new WatchList(symbols: TEST_SYMBOLS)
        when:
        def request = PUT('/account/watchlist', watchList)
                .accept(MediaType.APPLICATION_JSON)
                .bearerAuth(token.accessToken)
        HttpResponse response = client.toBlocking().exchange(request)
        then:
        response.status == HttpStatus.OK
        store.getWatchList(TEST_ACCOUNT_ID).symbols*.value == TEST_SYMBOLS*.value
    }

    void 'delete watchlist'() {
        given:
        BearerAccessRefreshToken token = givenMyUserIsLoggedIn()
        WatchList watchList = new WatchList(symbols: TEST_SYMBOLS)
        store.updateWatchList(TEST_ACCOUNT_ID, watchList)
        when:
        def request = DELETE("/account/watchlist/${TEST_ACCOUNT_ID}")
                .accept(MediaType.APPLICATION_JSON)
                .bearerAuth(token.accessToken)
        HttpResponse response = client.toBlocking().exchange(request)
        then:
        response.status == HttpStatus.OK
        store.getWatchList(TEST_ACCOUNT_ID).symbols.size() == 0
    }

    private BearerAccessRefreshToken givenMyUserIsLoggedIn() {
        final UsernamePasswordCredentials credentials = new UsernamePasswordCredentials(
                'my-user',
                'secret'
        )
        // Fetch valid JWT access token
        def login = POST('/login', credentials)
        def response = client.toBlocking().exchange(login, BearerAccessRefreshToken)
        assert response.getStatus() == HttpStatus.OK
        final BearerAccessRefreshToken token = response.body() as BearerAccessRefreshToken
        assert token
        assert token.username == 'my-user'
        log.debug("Bearer token: ${token.accessToken} expires in ${token.expiresIn}")
        token
    }

}
