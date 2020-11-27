package com.agorapulse.udemy.broker.model

import io.swagger.v3.oas.annotations.media.Schema

@Schema(name = 'Symbol', description = 'Abbreviation to uniquely identify public trades shares of a stock.')
class Symbol {

    @Schema(description = 'Symbol value', minLength = 1, maxLength = 5)
    String value

}
