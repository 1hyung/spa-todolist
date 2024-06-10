package com.teamsparta.spatodolist.domain.todo.reply.dtos

data class CreateReplyArgument(
    val content: String,
    val authorName: String,
    val password: String,
)