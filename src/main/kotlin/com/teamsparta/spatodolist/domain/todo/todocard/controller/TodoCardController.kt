package com.teamsparta.spatodolist.domain.todo.todocard.controller

import com.teamsparta.spatodolist.domain.todo.todocard.dtos.CreateTodoCardArguments
import com.teamsparta.spatodolist.domain.todo.todocard.dtos.RetrieveTodoCardDto
import com.teamsparta.spatodolist.domain.todo.todocard.dtos.TodoCardDto
import com.teamsparta.spatodolist.domain.todo.todocard.dtos.UpdateTodoCardArguments
import com.teamsparta.spatodolist.domain.todo.todocard.service.TodoCardService
import org.springdoc.core.service.RequestBodyService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RequestMapping("/api/v1/todo-cards")
@RestController
class TodoCardController(
    private val todoCardService: TodoCardService,
    private val body: RequestBodyService,
) {
    //TODOCARDS 생성
    @PostMapping
    fun createTodoCard(@RequestBody createTodoCardArguments: CreateTodoCardArguments): ResponseEntity<TodoCardDto> {
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(todoCardService.createTodoCard(createTodoCardArguments))
    }

    //선택한 할일 조회 기능
    @GetMapping("/{todoCardId}")
    fun findTodoCard(@PathVariable todoCardId: Long): ResponseEntity<RetrieveTodoCardDto> {
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(todoCardService.findById(todoCardId))
    }

    @PatchMapping("/{todoCardId}/complete")
    fun completeTodoCard(@PathVariable todoCardId: Long): ResponseEntity<Unit> {

        todoCardService.completeTodoCard(todoCardId)

        return ResponseEntity
            .status(HttpStatus.OK)
            .body(null)
    }

    @PutMapping("/{todoCardId}")
    fun updateTodoCard(
        @PathVariable todoCardId: Long,
        @RequestBody updateTodoCardArguments: UpdateTodoCardArguments,
    ): ResponseEntity<TodoCardDto> {
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(todoCardService.updateTodoCard(todoCardId, updateTodoCardArguments))
    }

    @DeleteMapping("/{todoCardId}")
    fun deleteTodoCard(@PathVariable todoCardId: Long): ResponseEntity<Unit> {
        todoCardService.deleteTodoCard(todoCardId)
        return ResponseEntity
            .status(HttpStatus.NO_CONTENT)
            .body(null)
    }
}