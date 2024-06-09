package com.teamsparta.spatodolist.domain.todo.controller

import com.teamsparta.spatodolist.domain.todo.dtos.CreateTodoCardArguments
import com.teamsparta.spatodolist.domain.todo.dtos.TodoCardDto
import com.teamsparta.spatodolist.domain.todo.service.TodoCardService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RequestMapping("/api/v1/todos")
@RestController
class TodoCardController(
    private val todoCardService: TodoCardService,
) {
    //TODOS 생성
    @PostMapping
    fun createTodo(@RequestBody createTodoCardArguments: CreateTodoCardArguments): ResponseEntity<TodoCardDto> {
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(todoCardService.createTodo(createTodoCardArguments))
    }
}