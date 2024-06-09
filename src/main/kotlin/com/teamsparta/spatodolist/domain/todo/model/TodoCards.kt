package com.teamsparta.spatodolist.domain.todo.model

import com.teamsparta.spatodolist.domain.todo.dtos.CreateTodoCardArguments
import jakarta.persistence.*
import org.hibernate.annotations.CreationTimestamp
import java.time.LocalDateTime

@Entity
class TodoCards(
    //할 일 제목, 할일 내용, 작성일, 작성자 이름
    @Column
    var title: String,

    @Column
    var content: String,

    @Column
    var authorName: String

) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null

    @CreationTimestamp
    @Column(updatable = false)
    val createdAt: LocalDateTime = LocalDateTime.now()


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
