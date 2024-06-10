package com.teamsparta.spatodolist.domain.users.dtos

import com.teamsparta.spatodolist.domain.users.model.Users

data class UserDto(
    val id: Long?,
    val username: String,
) {

    var token: String? = null

    companion object {
        fun from(saveUser: Users): UserDto {
            return UserDto(
                saveUser.id,
                saveUser.userName
            )

        }

        fun from(saveUser: Users, token: String): UserDto {
            val userDto = UserDto(
                saveUser.id,
                saveUser.userName
            )

            userDto.token = token

            return userDto
        }
    }
}