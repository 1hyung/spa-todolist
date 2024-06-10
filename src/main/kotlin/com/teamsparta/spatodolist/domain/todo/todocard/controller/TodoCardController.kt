package com.teamsparta.spatodolist.domain.todo.todocard.controller

import com.teamsparta.spatodolist.domain.security.UserPrincipal
import com.teamsparta.spatodolist.domain.todo.todocard.dtos.CreateTodoCardArguments
import com.teamsparta.spatodolist.domain.todo.todocard.dtos.RetrieveTodoCardDto
import com.teamsparta.spatodolist.domain.todo.todocard.dtos.TodoCardDto
import com.teamsparta.spatodolist.domain.todo.todocard.dtos.UpdateTodoCardArguments
import com.teamsparta.spatodolist.domain.todo.todocard.service.TodoCardService
import org.apache.tomcat.util.net.openssl.ciphers.Authentication
import org.hibernate.query.Page
import org.springdoc.core.service.RequestBodyService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.awt.print.Pageable

@RequestMapping("/api/v1/todo-cards")
@RestController
class TodoCardController(
    private val todoCardService: TodoCardService,
) {
    //TODOCARDS 생성
    @PostMapping
    fun createTodoCard(
        @RequestBody @Valid createTodoCardArguments: CreateTodoCardArguments,
        authentication: Authentication,
    ): ResponseEntity<TodoCardDto> {

        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(todoCardService.createTodoCard(createTodoCardArguments, authentication.principal as UserPrincipal))

    }

    //선택한 할일 조회 기능
    @GetMapping("/{todoCardId}")
    fun findTodoCard(@PathVariable todoCardId: Long): ResponseEntity<RetrieveTodoCardDto> {
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(todoCardService.findById(todoCardId))
    }

    @GetMapping
    fun findAllTodoCard(
        @RequestParam sort: String?,
        @RequestParam authorName: String?,
    ): ResponseEntity<List<TodoCardDto>> {
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(todoCardService.findAll(sort, authorName))
    }

    @GetMapping("/with-reply")
    fun findAllTodoCardWithReply(pageable: Pageable): ResponseEntity<Page<ReplyWithTodoCardDto>> {
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(todoCardService.findAllWithReply(pageable))
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
        authentication: Authentication,
    ): ResponseEntity<TodoCardDto> {

        val userPrincipal = authentication.principal as UserPrincipal

        return ResponseEntity
            .status(HttpStatus.OK)
            .body(todoCardService.updateTodoCard(todoCardId, updateTodoCardArguments, userPrincipal.to()))
    }

    @DeleteMapping("/{todoCardId}")
    fun deleteTodoCard(
        @PathVariable todoCardId: Long,
        authentication: Authentication,
    ): ResponseEntity<Unit> {
        todoCardService.deleteTodoCard(todoCardId, authentication.principal as UserPrincipal)
        return ResponseEntity
            .status(HttpStatus.NO_CONTENT)
            .body(null)
    }
}