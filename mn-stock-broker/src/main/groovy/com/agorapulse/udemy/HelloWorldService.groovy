package com.agorapulse.udemy

import groovy.util.logging.Slf4j
import io.micronaut.context.annotation.Property
import io.micronaut.context.event.StartupEvent
import io.micronaut.runtime.event.annotation.EventListener

import javax.inject.Singleton

@Slf4j
@Singleton
class HelloWorldService {

    //@Value('${hello.service.greeting}')
    @Property(name='hello.service.greeting', defaultValue = "default value")
    private String greetings

    @EventListener
    void onStartup(StartupEvent event) {
        log.debug('Startup from hello service')
    }

    String sayHi() {
        greetings
    }

}
