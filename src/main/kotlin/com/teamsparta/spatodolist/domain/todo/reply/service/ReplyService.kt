package com.teamsparta.spatodolist.domain.todo.reply.service

import com.teamsparta.courseregistration.domain.exception.ModelNotFoundException
import com.teamsparta.spatodolist.domain.todo.reply.dtos.CreateReplyArgument
import com.teamsparta.spatodolist.domain.todo.reply.dtos.DeleteReplyArgument
import com.teamsparta.spatodolist.domain.todo.reply.dtos.ReplyDto
import com.teamsparta.spatodolist.domain.todo.reply.dtos.UpdateReplyArgument
import com.teamsparta.spatodolist.domain.todo.reply.model.Reply
import com.teamsparta.spatodolist.domain.todo.reply.repository.ReplyRepository
import com.teamsparta.spatodolist.domain.todo.todocard.repository.TodoCardRepository
import jakarta.transaction.Transactional
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class ReplyService(
    private val replyRepository: ReplyRepository,
    private val todoRepository: TodoCardRepository,
) {

    @Transactional
    fun createReply(todoCardId: Long, argument: CreateReplyArgument): ReplyDto {
        val foundTodoCard =
            todoRepository.findByIdOrNull(todoCardId) ?: throw ModelNotFoundException("TodoCard", todoCardId)

        val reply = Reply(
            argument.content,
            argument.authorName,
            argument.password,
            foundTodoCard
        )

        val savedReply = replyRepository.save(reply)

        return ReplyDto.from(savedReply)
    }

    @Transactional
    fun updateReply(replyId: Long, todoCardId: Long, argument: UpdateReplyArgument): ReplyDto {
        val foundTodoCard =
            todoRepository.findByIdOrNull(todoCardId) ?: throw ModelNotFoundException("TodoCard", todoCardId)
        val foundReply = replyRepository.findByIdOrNull(replyId) ?: throw ModelNotFoundException("Reply", replyId)

        foundReply.checkAuthentication(argument.authorName, argument.password)
        foundReply.changeContent(argument.content)

        return ReplyDto.from(foundReply)
    }

    fun deleteReply(replyId: Long, todoCardId: Long, argument: DeleteReplyArgument) {
        val foundTodoCard =
            todoRepository.findByIdOrNull(todoCardId) ?: throw ModelNotFoundException("TodoCard", todoCardId)
        val foundReply = replyRepository.findByIdOrNull(replyId) ?: throw ModelNotFoundException("Reply", replyId)

        foundReply.checkAuthentication(argument.authorName, argument.password)

        replyRepository.deleteById(replyId)
    }
}