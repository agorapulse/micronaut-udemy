package com.agorapulse.udemy.broker.persistence.model

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne
import javax.persistence.Table

@Entity(name = 'quote')
@Table(name = 'quotes')
class QuoteEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id
    @ManyToOne(targetEntity = SymbolEntity)
    @JoinColumn(name = 'symbol', referencedColumnName = 'value')
    SymbolEntity symbol
    BigDecimal bid
    BigDecimal ask
    @Column(name = 'last_price')
    BigDecimal lastPrice
    BigDecimal volume

}
