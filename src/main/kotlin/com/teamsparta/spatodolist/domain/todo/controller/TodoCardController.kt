package com.teamsparta.spatodolist.domain.todo.controller

import com.teamsparta.spatodolist.domain.todo.dtos.CreateTodoCardArguments
import com.teamsparta.spatodolist.domain.todo.dtos.TodoCardDto
import com.teamsparta.spatodolist.domain.todo.dtos.UpdateTodoCardArguments
import com.teamsparta.spatodolist.domain.todo.service.TodoCardService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RequestMapping("/api/v1/todo-cards")
@RestController
class TodoCardController(
    private val todoCardService: TodoCardService,
) {
    @PostMapping
    fun createTodoCard(@RequestBody createTodoCardArguments: CreateTodoCardArguments): ResponseEntity<TodoCardDto> {
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(todoCardService.createTodoCard(createTodoCardArguments))
    }

    @GetMapping("/{todoCardId}")
    fun findTodoCard(@PathVariable todoCardId: Long): ResponseEntity<TodoCardDto> {
        val todoCard = todoCardService.findById(todoCardId) ?: return ResponseEntity.notFound().build()
        return ResponseEntity.ok(todoCard)
    }

    @GetMapping
    fun findAllTodoCard(): ResponseEntity<List<TodoCardDto>> {
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(todoCardService.findAll())
    }

    @PutMapping("/{todoCardId}")
    fun updateTodoCard(
        @PathVariable todoCardId: Long,
        @RequestBody updateTodoCardArguments: UpdateTodoCardArguments,
    ): ResponseEntity<TodoCardDto> {
        return ResponseEntity.status(HttpStatus.OK)
            .body(todoCardService.updateTodoCard(todoCardId, updateTodoCardArguments))
    }

    @DeleteMapping("/{todoCardId}")
    fun deleteTodoCard(@PathVariable todoCardId: Long): ResponseEntity<Unit> {
        todoCardService.deleteTodoCard(todoCardId)
        return ResponseEntity.noContent().build()
    }
}