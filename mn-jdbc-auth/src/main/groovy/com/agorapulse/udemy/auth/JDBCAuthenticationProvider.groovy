package com.agorapulse.udemy.auth

import com.agorapulse.udemy.auth.persistence.UserEntity
import com.agorapulse.udemy.auth.persistence.UserRepository
import edu.umd.cs.findbugs.annotations.Nullable
import groovy.util.logging.Slf4j
import io.micronaut.http.HttpRequest
import io.micronaut.security.authentication.AuthenticationException
import io.micronaut.security.authentication.AuthenticationFailed
import io.micronaut.security.authentication.AuthenticationProvider
import io.micronaut.security.authentication.AuthenticationRequest
import io.micronaut.security.authentication.AuthenticationResponse
import io.micronaut.security.authentication.UserDetails
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import org.reactivestreams.Publisher

import javax.inject.Singleton

@Slf4j
@Singleton
class JDBCAuthenticationProvider implements AuthenticationProvider {

    private final UserRepository users;

    JDBCAuthenticationProvider(final UserRepository users) {
        this.users = users
    }

    @Override
    Publisher<AuthenticationResponse> authenticate(@Nullable HttpRequest<?> httpRequest, AuthenticationRequest<?, ?> authenticationRequest) {
        Flowable.create(emitter -> {
            final String identity = authenticationRequest.identity
            log.debug("User ${identity} tries to login...")

            final Optional<UserEntity> maybeUser = users.findByEmail(identity)
            if (maybeUser.present) {
                log.debug("Found user: ${maybeUser.get().email}")
                final String secret = authenticationRequest.secret
                if (maybeUser.get().password == secret) {
                    log.debug('User logged in.')
                    Map customAttributes = [hairColor: 'brown', language: 'en']
                    final UserDetails userDetails = new UserDetails(
                            identity,
                            ['ROLE_USER'],
                            customAttributes
                    )
                    emitter.onNext(userDetails)
                    emitter.onComplete()
                    return
                } else {
                    log.debug("Wrong password provided for user ${identity}.")
                }
            } else {
                log.debug("No user found with email: ${identity}")
            }

            emitter.onError(new AuthenticationException(new AuthenticationFailed('Wrong username or password.')))
        }, BackpressureStrategy.ERROR)
    }
}
