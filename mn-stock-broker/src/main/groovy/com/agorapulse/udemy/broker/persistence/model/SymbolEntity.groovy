package com.agorapulse.udemy.broker.persistence.model

import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Entity(name = 'symbol')
@Table(name= 'symbols')
class SymbolEntity {

    @Id
    String value

}
