package com.teamsparta.spatodolist.domain.todo.model

import com.teamsparta.spatodolist.domain.todo.dtos.CreateTodoCardArguments
import com.teamsparta.spatodolist.domain.todo.dtos.UpdateTodoCardArguments
import jakarta.persistence.*
import org.hibernate.annotations.CreationTimestamp
import java.time.LocalDateTime

@Entity
class TodoCards(
    @Column
    var title: String,

    @Column
    var content: String,

    @Column
    var authorName: String,

    ) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null

    @CreationTimestamp
    @Column(updatable = false)
    val createdAt: LocalDateTime = LocalDateTime.now()

    fun updateTodoCardField(updateTodoCardArguments: UpdateTodoCardArguments) {
        title = updateTodoCardArguments.title
        content = updateTodoCardArguments.content
        authorName = updateTodoCardArguments.authorName
    }

    companion object {
        fun from(createTodoCardArguments: CreateTodoCardArguments): TodoCards {
            return TodoCards(
                createTodoCardArguments.title,
                createTodoCardArguments.content,
                createTodoCardArguments.authorName
            )
        }
    }
}
