package com.agorapulse.udemy.broker.persistence.jpa

import com.agorapulse.udemy.broker.persistence.model.SymbolEntity
import io.micronaut.data.annotation.Repository
import io.micronaut.data.repository.CrudRepository

@Repository
interface SymbolsRepository extends CrudRepository<SymbolEntity, String> {

    @Override
    List<SymbolEntity> findAll()

}
