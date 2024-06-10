package com.teamsparta.spatodolist.domain.security

import com.teamsparta.spatodolist.domain.users.model.Users

data class UserPrincipal(
    val id: Long,
    val userName: String,
) {
    fun to(): Users {
        return Users(
            id = id,
            userName = userName,
            password = "",
        )
    }
}