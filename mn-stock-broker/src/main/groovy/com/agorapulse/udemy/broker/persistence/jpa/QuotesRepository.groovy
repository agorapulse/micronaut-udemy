package com.agorapulse.udemy.broker.persistence.jpa

import com.agorapulse.udemy.broker.persistence.model.QuoteDTO
import com.agorapulse.udemy.broker.persistence.model.QuoteEntity
import com.agorapulse.udemy.broker.persistence.model.SymbolEntity
import io.micronaut.data.annotation.Repository
import io.micronaut.data.model.Pageable
import io.micronaut.data.model.Slice
import io.micronaut.data.repository.CrudRepository

@Repository
interface QuotesRepository extends CrudRepository<QuoteEntity, Integer> {

    @Override
    List<QuoteEntity> findAll()

    Optional<QuoteEntity> findBySymbol(SymbolEntity s)

    // Ordering
    List<QuoteDTO> listOrderByVolumeDesc()
    List<QuoteDTO> listOrderByVolumeAsc()

    // Filter
    List<QuoteDTO> findByVolumeGreaterThanOrderByVolumeAsc(BigDecimal volume)

    // Pagination
    List<QuoteDTO> findByVolumeGreaterThan(BigDecimal volume, Pageable pageable)

    Slice<QuoteDTO> list(Pageable pageable)

}
