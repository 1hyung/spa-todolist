package com.teamsparta.spatodolist.domain.todo.reply.dtos

data class UpdateReplyArgument(
    val content: String,
    val authorName: String,
    val password: String,
)
