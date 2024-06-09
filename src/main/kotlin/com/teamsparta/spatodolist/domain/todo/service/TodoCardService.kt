package com.teamsparta.spatodolist.domain.todo.service

import com.teamsparta.courseregistration.domain.exception.ModelNotFoundException
import com.teamsparta.spatodolist.domain.todo.dtos.CreateTodoCardArguments
import com.teamsparta.spatodolist.domain.todo.dtos.TodoCardDto
import com.teamsparta.spatodolist.domain.todo.dtos.UpdateTodoCardArguments
import com.teamsparta.spatodolist.domain.todo.model.TodoCards
import com.teamsparta.spatodolist.domain.todo.repository.TodoCardRepository
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
        // 사용자한테 입력받은 DTO를 Entity로 변환
        val todo = TodoCards.from(createTodoCardArguments)

        // Entity를 저장
        val todoCard = todoCardRepository.save(todo)
        return TodoCardDto.from(todoCard)
    }

    fun findById(todoCardId: Long): TodoCardDto? {
        //todoCardId로 DB에서 todoCard 찾기
        val foundTodoCard = todoCardRepository.findByIdOrNull(todoCardId)
        return foundTodoCard?.let { TodoCardDto.from(it) }
    }

    fun findAll(): List<TodoCardDto> {
        val foundTodoCards = todoCardRepository.findAllByOrderByCreatedAtDesc()
        return foundTodoCards.map { TodoCardDto.from(it) }
    }

    @Transactional
    fun updateTodoCard(todoCardId: Long, updateTodoCardArguments: UpdateTodoCardArguments): TodoCardDto {
        //DB에서 todoCardId를 통해 수정할 todoCard를 조회한다
        val foundTodoCard = todoCardRepository.findByIdOrNull(todoCardId) ?: throw ModelNotFoundException("TodoCard", todoCardId)
        //값을 수정한다.
        foundTodoCard.updateTodoCardField(updateTodoCardArguments)
        return TodoCardDto.from(foundTodoCard)
    }

    fun deleteTodoCard(todoCardId: Long) {
        todoCardRepository.deleteById(todoCardId)
    }
}