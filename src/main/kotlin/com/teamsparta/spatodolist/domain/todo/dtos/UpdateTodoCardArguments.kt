package com.teamsparta.spatodolist.domain.todo.dtos

data class UpdateTodoCardArguments(
    //할 일 제목, 할일 내용, 작성일, 작성자 이름
    val title: String,
    val content: String,
    val authorName: String,
)
