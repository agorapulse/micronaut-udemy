package com.agorapulse.udemy.auth.persistence

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.Table
import javax.validation.constraints.Email
import javax.validation.constraints.NotEmpty

@Entity
@Table(name = 'users')
class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id

    @NotEmpty
    @Email
    @Column(nullable = false, unique = true)
    String email

    @NotEmpty
    String password

}
