package com.agorapulse.udemy.auth

import groovy.util.logging.Slf4j
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.security.annotation.Secured
import io.micronaut.security.authentication.Authentication
import io.micronaut.security.rules.SecurityRule

import java.security.Principal

@Slf4j
@Secured(SecurityRule.IS_AUTHENTICATED)
@Controller('/secured')
class SecuredEndoint {

    @Get('/status')
    List<Object> status(Principal principal) {
        Authentication details = (Authentication) principal
        log.debug("User details: ${details.attributes}")
        [
                principal.name,
                details.attributes['hairColor'],
                details.attributes['language'],
        ]
    }

}
