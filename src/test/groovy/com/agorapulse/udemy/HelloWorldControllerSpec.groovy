package com.agorapulse.udemy

import io.micronaut.http.client.RxHttpClient
import io.micronaut.http.client.annotation.Client
import io.micronaut.runtime.EmbeddedApplication
import io.micronaut.test.extensions.spock.annotation.MicronautTest
import spock.lang.Specification
import javax.inject.Inject

@MicronautTest
class HelloWorldControllerSpec extends Specification {

    @Inject
    EmbeddedApplication application

    @Inject
    @Client("/") RxHttpClient client

    void 'test it works'() {
        expect:
        application.running
    }

    void 'get hello'() {
        when:
        String result = client.toBlocking().retrieve("/hello")
        then:
        result == 'Hello from service'
    }

    void 'get hello in german'() {
        when:
        String result = client.toBlocking().retrieve("/hello/de")
        then:
        result == 'Hallo!'
    }

    void 'get0 hello in english'() {
        when:
        String result = client.toBlocking().retrieve("/hello/en")
        then:
        result == 'Hello!'
    }

}
