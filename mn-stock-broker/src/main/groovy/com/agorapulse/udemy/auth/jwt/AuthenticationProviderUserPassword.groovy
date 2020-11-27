package com.agorapulse.udemy.auth.jwt

import edu.umd.cs.findbugs.annotations.Nullable
import groovy.util.logging.Slf4j
import io.micronaut.http.HttpRequest
import io.micronaut.security.authentication.*
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import org.reactivestreams.Publisher

import javax.inject.Singleton

@Slf4j
@Singleton
class AuthenticationProviderUserPassword implements AuthenticationProvider {
    @Override
    Publisher<AuthenticationResponse> authenticate(@Nullable HttpRequest<?> httpRequest, AuthenticationRequest<?, ?> authenticationRequest) {
        Flowable.create(emitter -> {
            final String identity = authenticationRequest.identity
            final String secret = authenticationRequest.secret
            log.debug("User ${identity} tries to login..." )
            if (identity == 'my-user' && secret == 'secret') {
                log.debug("User logged in." )
                emitter.onNext(new UserDetails((String) identity, []))
                emitter.onComplete()
            } else {
                emitter.onError(new AuthenticationException(new AuthenticationFailed()))
            }
        }, BackpressureStrategy.ERROR)
    }
}
