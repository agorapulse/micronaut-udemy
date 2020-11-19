package com.agorapulse.udemy

import io.micronaut.runtime.Micronaut
import groovy.transform.CompileStatic
import io.swagger.v3.oas.annotations.OpenAPIDefinition
import io.swagger.v3.oas.annotations.info.Contact
import io.swagger.v3.oas.annotations.info.Info
import io.swagger.v3.oas.annotations.info.License

@OpenAPIDefinition(
        info = @Info(
                title = 'mn-stock-broker',
                version = '0.1',
                description = 'Udemy Micronaut Course',
                license = @License(name = 'MIT'),
                contact = @Contact(url = "https://www.agorapulse.com", name = "Ben", email = "ben@agorapulse.com")
        )
)
@CompileStatic
class Application {
    static void main(String[] args) {
        Micronaut.run(Application, args)
    }
}
