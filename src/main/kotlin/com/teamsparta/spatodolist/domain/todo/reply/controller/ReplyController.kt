package com.teamsparta.spatodolist.domain.todo.reply.controller

import com.teamsparta.spatodolist.domain.security.UserPrincipal
import com.teamsparta.spatodolist.domain.todo.reply.dtos.CreateReplyArgument
import com.teamsparta.spatodolist.domain.todo.reply.dtos.DeleteReplyArgument
import com.teamsparta.spatodolist.domain.todo.reply.dtos.ReplyDto
import com.teamsparta.spatodolist.domain.todo.reply.dtos.UpdateReplyArgument
import com.teamsparta.spatodolist.domain.todo.reply.service.ReplyService
import org.apache.tomcat.util.net.openssl.ciphers.Authentication
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RequestMapping("/api/v1/todo-cards/{todoCardId}/replies")
@RestController
class ReplyController(
    private val replyService: ReplyService,
) {

    @PostMapping
    fun createReply(
        @RequestBody createReplyArgument: CreateReplyArgument,
        @PathVariable todoCardId: Long,
        authentication: Authentication,
    ): ResponseEntity<ReplyDto> {
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(replyService.createReply(todoCardId, createReplyArgument, authentication.principal as UserPrincipal))
    }

    @PutMapping("/{replyId}")
    fun updateReply(
        @PathVariable replyId: Long,
        @PathVariable todoCardId: Long,
        @RequestBody updateReplyArgument: UpdateReplyArgument,
        authentication: Authentication,
    ): ResponseEntity<ReplyDto> {

        return ResponseEntity
            .status(HttpStatus.OK)
            .body(
                replyService.updateReply(
                    replyId,
                    todoCardId,
                    updateReplyArgument,
                    authentication.principal as UserPrincipal
                )
            )
    }

    @DeleteMapping("/{replyId}")
    fun deleteReply(
        @PathVariable replyId: Long,
        @PathVariable todoCardId: Long,
        @RequestBody deleteReplyArgument: DeleteReplyArgument,
        authentication: Authentication,
    ): ResponseEntity<Unit> {
        replyService.deleteReply(replyId, todoCardId, deleteReplyArgument, authentication.principal as UserPrincipal)
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(null)
    }
}