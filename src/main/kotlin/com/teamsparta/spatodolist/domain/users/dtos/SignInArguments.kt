package com.teamsparta.spatodolist.domain.users.dtos

data class SignInArguments(
    val userName: String,
    val password: String,
)