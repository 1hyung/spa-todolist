package com.teamsparta.spatodolist.domain.todo.todocard.service

import com.teamsparta.courseregistration.domain.exception.ModelNotFoundException
import com.teamsparta.spatodolist.domain.security.UserPrincipal
import com.teamsparta.spatodolist.domain.todo.todocard.dtos.CreateTodoCardArguments
import com.teamsparta.spatodolist.domain.todo.todocard.dtos.RetrieveTodoCardDto
import com.teamsparta.spatodolist.domain.todo.todocard.dtos.TodoCardDto
import com.teamsparta.spatodolist.domain.todo.todocard.dtos.UpdateTodoCardArguments
import com.teamsparta.spatodolist.domain.todo.todocard.model.TodoCards
import com.teamsparta.spatodolist.domain.todo.todocard.repository.TodoCardRepository
import com.teamsparta.spatodolist.domain.users.model.Users
import com.teamsparta.spatodolist.domain.users.repository.UserRepository
import jakarta.transaction.Transactional
import org.hibernate.query.Page
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import java.awt.print.Pageable

@Service
class TodoCardService(
    private val userRepository: UserRepository,
    private val todoCardRepository: TodoCardRepository,
) {

    // DB로 데이터를 저장
    @Transactional
    fun createTodoCard(createTodoCardArguments: CreateTodoCardArguments, userPrincipal: UserPrincipal): TodoCardDto {

        val users =
            userRepository.findByIdOrNull(userPrincipal.id) ?: throw ModelNotFoundException("User", userPrincipal.id)

        //DTO를 Entity 변환
        val todo = TodoCards(
            createTodoCardArguments.title,
            createTodoCardArguments.content,
            createTodoCardArguments.authorName,
            users
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

    fun findAll(sort: String?, authorName: String?): List<TodoCardDto> {
        authorName?.let {
            // 입력받은 사용자 이름을 가지고 있는 데이터를 꺼내와야함
            return todoCardRepository.findAllByAuthorName(authorName).map { TodoCardDto.from(it) }
        }
        // sort ==> desc(내림차순) / asc(오름차순)
        return if (sort == "desc") {
            todoCardRepository.findAllByOrderByCreatedAtDesc()
        } else {
            todoCardRepository.findAllByOrderByCreatedAtAsc()
        }.map { TodoCardDto.from(it) }
    }

    @Transactional
    fun updateTodoCard(todoCardId: Long, updateTodoCardArguments: UpdateTodoCardArguments, users: Users): TodoCardDto {
        // DB에서 todoCardId 를 통해 수정할 todoCard를 조회한다
        val foundTodoCard =
            todoCardRepository.findByIdOrNull(todoCardId) ?: throw ModelNotFoundException("TodoCard", todoCardId)

        foundTodoCard.checkAuthorization(users)

        // 값을 수정한다
        foundTodoCard.updateTodoCardField(updateTodoCardArguments)

        //todoCardRepository.save(foundTodoCard)
        return TodoCardDto.from(foundTodoCard)
    }

    fun deleteTodoCard(todoCardId: Long, userPrincipal: UserPrincipal) {
        val foundTodoCard =
            todoCardRepository.findByIdOrNull(todoCardId) ?: throw ModelNotFoundException("TodoCard", todoCardId)
        foundTodoCard.checkAuthorization(userPrincipal.to())

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

    fun findAllWithReply(pageable: Pageable): Page<ReplyWithTodoCardDto>? {
        val findAll = todoCardRepository.findAllFetchJPQL(pageable)
        //val findAll = todoCardRepository.findAll(pageable)
        return findAll.map { ReplyWithTodoCardDto.from(it) }
    }
}