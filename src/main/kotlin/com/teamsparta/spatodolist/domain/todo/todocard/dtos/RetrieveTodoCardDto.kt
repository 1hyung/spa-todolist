package com.teamsparta.spatodolist.domain.todo.todocard.dtos

import com.teamsparta.spatodolist.domain.todo.reply.dtos.ReplyDto
import com.teamsparta.spatodolist.domain.todo.todocard.model.TodoCards

data class RetrieveTodoCardDto(
    val title: String,
    val content: String,
    val authorName: String,
    val reply: List<ReplyDto>,
) {
    companion object {
        fun from(todoCards: TodoCards): RetrieveTodoCardDto {
            return RetrieveTodoCardDto(
                todoCards.title,
                todoCards.content,
                todoCards.authorName,
                todoCards.reply.map { ReplyDto.from(it) }
            )
        }
    }
}
