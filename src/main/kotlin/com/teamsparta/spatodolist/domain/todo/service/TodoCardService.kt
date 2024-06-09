package com.teamsparta.spatodolist.domain.todo.service

import com.teamsparta.spatodolist.domain.todo.dtos.CreateTodoCardArguments
import com.teamsparta.spatodolist.domain.todo.dtos.TodoCardDto
import com.teamsparta.spatodolist.domain.todo.model.TodoCards
import com.teamsparta.spatodolist.domain.todo.repository.TodoCardRepository
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service

@Service
class TodoCardService(
    private val todoCardRepository: TodoCardRepository
) {

    // DB로 데이터를 저장
    @Transactional
    fun createTodo(createTodoCardArguments: CreateTodoCardArguments): TodoCardDto {
        // 사용자한테 입력받은 DTO를 Entity로 변환
        val todo = TodoCards(createTodoCardArguments.title, createTodoCardArguments.content, createTodoCardArguments.authorName)

        // Entity를 저장
        val todoCard: TodoCards = todoCardRepository.save(todo)
        return TodoCardDto.from(todoCard)
    }
}