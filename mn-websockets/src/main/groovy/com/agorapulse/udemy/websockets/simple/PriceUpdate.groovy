package com.agorapulse.udemy.websockets.simple

import groovy.transform.Immutable

@Immutable
class PriceUpdate {

    String symbol
    BigDecimal price

}
