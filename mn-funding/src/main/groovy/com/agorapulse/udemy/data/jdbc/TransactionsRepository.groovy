package com.agorapulse.udemy.data.jdbc

import io.micronaut.data.annotation.Repository
import io.micronaut.data.jdbc.annotation.JdbcRepository
import io.micronaut.data.model.query.builder.sql.Dialect
import io.micronaut.data.repository.CrudRepository


@Repository
@JdbcRepository(dialect = Dialect.MYSQL)
interface TransactionsRepository extends CrudRepository<TransactionEntity, Long> {
}
