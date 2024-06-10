package com.teamsparta.spatodolist.domain.users.model

import jakarta.persistence.*

@Entity
class Users(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column
    var userName: String,

    @Column
    var password: String,
)