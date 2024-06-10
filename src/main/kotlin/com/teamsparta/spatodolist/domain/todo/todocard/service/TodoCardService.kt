package com.teamsparta.spatodolist.domain.todo.todocard.service

import com.teamsparta.courseregistration.domain.exception.ModelNotFoundException
import com.teamsparta.spatodolist.domain.todo.todocard.dtos.CreateTodoCardArguments
import com.teamsparta.spatodolist.domain.todo.todocard.dtos.RetrieveTodoCardDto
import com.teamsparta.spatodolist.domain.todo.todocard.dtos.TodoCardDto
import com.teamsparta.spatodolist.domain.todo.todocard.dtos.UpdateTodoCardArguments
import com.teamsparta.spatodolist.domain.todo.todocard.model.TodoCards
import com.teamsparta.spatodolist.domain.todo.todocard.repository.TodoCardRepository
import jakarta.transaction.Transactional
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class TodoCardService(
    private val todoCardRepository: TodoCardRepository,
) {
    // DB로 데이터를 저장
    @Transactional
    fun createTodoCard(createTodoCardArguments: CreateTodoCardArguments): TodoCardDto {
        //DTO를 Entity 변환
        val todo = TodoCards(
            createTodoCardArguments.title,
            createTodoCardArguments.authorName,
            createTodoCardArguments.content
        )
        //Entity를 저장합니다!
        val todoCard = todoCardRepository.save(todo)
        return TodoCardDto.from(todoCard)
    }

    fun findById(todoCardId: Long): RetrieveTodoCardDto {
        //todoCardId로 db에서 todoCard 찾기
        val foundTodoCard =
            todoCardRepository.findByIdOrNull(todoCardId) ?: throw ModelNotFoundException("TodoCard", todoCardId)

        return RetrieveTodoCardDto.from(foundTodoCard)
    }

    fun findAll(): List<TodoCardDto> {
        val foundTodoCards = todoCardRepository.findAllByOrderByCreatedAtDesc()
        return foundTodoCards.map { TodoCardDto.from(it) }
    }

    @Transactional
    fun updateTodoCard(todoCardId: Long, updateTodoCardArguments: UpdateTodoCardArguments): TodoCardDto {
        // DB에서 todoCardId 를 통해 수정할 todoCard를 조회한다
        val foundTodoCard =
            todoCardRepository.findByIdOrNull(todoCardId) ?: throw ModelNotFoundException("TodoCard", todoCardId)
        // 값을 수정한다
        foundTodoCard.updateTodoCardField(updateTodoCardArguments)
        //todoCardRepository.save(foundTodoCard)
        return TodoCardDto.from(foundTodoCard)
    }

    fun deleteTodoCard(todoCardId: Long) {
        todoCardRepository.deleteById(todoCardId)
    }

    @Transactional
    fun completeTodoCard(todoCardId: Long) {
        //저장되어 있는 todoCard를 찾는다
        val todoCard =
            todoCardRepository.findByIdOrNull(todoCardId) ?: throw ModelNotFoundException("TodoCard", todoCardId)
        //상태를 변경한다
        todoCard.complete()
    }
}