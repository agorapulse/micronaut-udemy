package com.agorapulse.udemy

import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get

@Controller('${hello.controller.path:/hello}')
class HelloWorldController {

    private HelloWorldService helloWorldService
    private GreetingConfig greetingConfig

    HelloWorldController(final GreetingConfig greetingConfig,
                         final HelloWorldService helloWorldService) {
        this.greetingConfig = greetingConfig
        this.helloWorldService = helloWorldService
    }

    @Get('/')
    String index() {
        helloWorldService.sayHi()
    }

    @Get('/de')
    String greetingsInGerman() {
        greetingConfig.de
    }

    @Get('/en')
    String greetingsInEnglish() {
        greetingConfig.en
    }

}
