package com.agorapulse.udemy

import com.agorapulse.udemy.auth.persistence.UserEntity
import com.agorapulse.udemy.auth.persistence.UserRepository
import io.micronaut.context.event.StartupEvent
import io.micronaut.runtime.event.annotation.EventListener

import javax.inject.Singleton

@Singleton
class TestDataProvider {

    private final UserRepository userRepository

    TestDataProvider(UserRepository userRepository) {
        this.userRepository = userRepository
    }

    @EventListener
    init(StartupEvent event) {
        if (!userRepository.findByEmail('alice@example.com')) {
            final UserEntity alice = new UserEntity(
                    email: 'alice@example.com',
                    password: 'secret'
            )
            userRepository.save(alice)
        }
    }

}
