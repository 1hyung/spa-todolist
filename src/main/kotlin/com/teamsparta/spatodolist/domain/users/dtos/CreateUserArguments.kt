package com.teamsparta.spatodolist.domain.users.dtos

data class CreateUserArguments(
    val userName: String,
    val password: String,
)