package com.teamsparta.spatodolist.domain.todo.todocard.dtos

data class CreateTodoCardArguments(
    //할 일 제목, 할일 내용, 작성일, 작성자 이름
    val title: String,
    val content: String,
    val authorName: String,
)