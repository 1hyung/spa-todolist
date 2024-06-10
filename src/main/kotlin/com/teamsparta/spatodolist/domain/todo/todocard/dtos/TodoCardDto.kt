package com.teamsparta.spatodolist.domain.todo.todocard.dtos

import com.teamsparta.spatodolist.domain.todo.todocard.model.TodoCards

data class TodoCardDto(
    //id isCompleted createdAt
    val title: String,
    val content: String,
    val authorName: String,
) {
    companion object {
        fun from(todoCards: TodoCards): TodoCardDto {
            return TodoCardDto(
                todoCards.title,
                todoCards.content,
                todoCards.authorName
            )
        }
    }
}
