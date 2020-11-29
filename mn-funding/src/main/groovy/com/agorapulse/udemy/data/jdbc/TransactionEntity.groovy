package com.agorapulse.udemy.data.jdbc

import groovy.transform.ToString
import io.micronaut.data.annotation.Id
import io.micronaut.data.annotation.MappedEntity
import io.micronaut.data.model.naming.NamingStrategies

@ToString
@MappedEntity(value = 'transactions', namingStrategy = NamingStrategies.UnderScoreSeparatedLowerCase)
class TransactionEntity {

    @Id
    long transactionId
    String user
    String symbol
    BigDecimal modification

}
