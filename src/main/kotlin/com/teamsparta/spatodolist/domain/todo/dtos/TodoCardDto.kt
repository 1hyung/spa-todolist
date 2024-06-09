package com.teamsparta.spatodolist.domain.todo.dtos

import com.teamsparta.spatodolist.domain.todo.model.TodoCards

data class TodoCardDto(
    val title: String,
    val content: String,
    val authorName: String,
) {
    companion object {
        fun from(todoCards: TodoCards): TodoCardDto {
            return TodoCardDto(
                todoCards.title,
                todoCards.content,
                todoCards.authorName,
            )
        }
    }
}
