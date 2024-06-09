package com.teamsparta.spatodolist.domain.todo.repository

import com.teamsparta.spatodolist.domain.todo.model.TodoCards
import org.springframework.data.jpa.repository.JpaRepository

interface TodoCardRepository: JpaRepository<TodoCards, Long> {
    fun findAllByOrderByCreatedAtDesc(): List<TodoCards>
}