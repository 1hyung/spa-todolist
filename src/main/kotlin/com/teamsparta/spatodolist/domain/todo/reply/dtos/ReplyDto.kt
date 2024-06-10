package com.teamsparta.spatodolist.domain.todo.reply.dtos

import com.teamsparta.spatodolist.domain.todo.reply.model.Reply

data class ReplyDto(
    val content: String,
    val authorName: String,
) {
    companion object {
        fun from(reply: Reply): ReplyDto {
            return ReplyDto(
                reply.content,
                reply.authorName
            )
        }
    }
}