package com.agorapulse.udemy.broker.persistence.model

import io.micronaut.core.annotation.Introspected

@Introspected
class QuoteDTO {

    Integer id
    BigDecimal volume

}
