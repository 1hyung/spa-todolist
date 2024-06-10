package com.teamsparta.spatodolist.domain.todo.reply.service

import com.teamsparta.courseregistration.domain.exception.ModelNotFoundException
import com.teamsparta.spatodolist.domain.security.UserPrincipal
import com.teamsparta.spatodolist.domain.todo.reply.dtos.CreateReplyArgument
import com.teamsparta.spatodolist.domain.todo.reply.dtos.DeleteReplyArgument
import com.teamsparta.spatodolist.domain.todo.reply.dtos.ReplyDto
import com.teamsparta.spatodolist.domain.todo.reply.dtos.UpdateReplyArgument
import com.teamsparta.spatodolist.domain.todo.reply.model.Reply
import com.teamsparta.spatodolist.domain.todo.reply.repository.ReplyRepository
import com.teamsparta.spatodolist.domain.todo.todocard.repository.TodoCardRepository
import com.teamsparta.spatodolist.domain.users.repository.UserRepository
import jakarta.transaction.Transactional
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class ReplyService(
    private val replyRepository: ReplyRepository,
    private val todoRepository: TodoCardRepository,
    private val userRepository: UserRepository,
) {

    @Transactional
    fun createReply(todoCardId: Long, argument: CreateReplyArgument, userPrincipal: UserPrincipal): ReplyDto {
        val foundTodoCard =
            todoRepository.findByIdOrNull(todoCardId) ?: throw ModelNotFoundException("TodoCard", todoCardId)
        val users =
            userRepository.findByIdOrNull(userPrincipal.id) ?: throw ModelNotFoundException("User", userPrincipal.id)


        val reply = Reply(
            argument.content,
            argument.authorName,
            argument.password,
            foundTodoCard,
            users
        )

        val savedReply = replyRepository.save(reply)
        return ReplyDto.from(savedReply)
    }

    @Transactional
    fun updateReply(
        replyId: Long,
        todoCardId: Long,
        argument: UpdateReplyArgument,
        userPrincipal: UserPrincipal,
    ): ReplyDto {
        val foundTodoCard =
            todoRepository.findByIdOrNull(todoCardId) ?: throw ModelNotFoundException("TodoCard", todoCardId)
        val foundReply = replyRepository.findByIdOrNull(replyId) ?: throw ModelNotFoundException("Reply", replyId)

        foundReply.checkAuthorization(userPrincipal.to())

        //foundReply.checkAuthorization(argument.authorName, argument.password)
        foundReply.changeContent(argument.content)

        return ReplyDto.from(foundReply)
    }

    fun deleteReply(replyId: Long, todoCardId: Long, argument: DeleteReplyArgument, userPrincipal: UserPrincipal) {
        val foundTodoCard =
            todoRepository.findByIdOrNull(todoCardId) ?: throw ModelNotFoundException("TodoCard", todoCardId)
        val foundReply = replyRepository.findByIdOrNull(replyId) ?: throw ModelNotFoundException("Reply", replyId)

        foundReply.checkAuthorization(userPrincipal.to())

        //foundReply.checkAuthorization(argument.authorName, argument.password)

        replyRepository.deleteById(replyId)
    }
}