package com.teamsparta.spatodolist.domain.users.repository

import com.teamsparta.spatodolist.domain.users.model.Users
import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository : JpaRepository<Users, Long> {
    fun findByUserName(userName: String): Users?
}