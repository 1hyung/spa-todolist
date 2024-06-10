package com.teamsparta.spatodolist.domain.todo.todocard.model

import com.teamsparta.spatodolist.domain.todo.reply.model.Reply
import com.teamsparta.spatodolist.domain.todo.todocard.dtos.UpdateTodoCardArguments
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

    @Column(name = "is_completed")
    private var _isCompleted: Boolean = false

    @OneToMany(mappedBy = "todoCards", fetch = FetchType.LAZY, cascade = [CascadeType.REMOVE])
    val reply: List<Reply> = emptyList()


    fun complete() {
        _isCompleted = true
    }

    fun getIsCompleted(): Boolean {
        return _isCompleted
    }

    fun updateTodoCardField(arguments: UpdateTodoCardArguments) {
        title = arguments.title
        content = arguments.content
        authorName = arguments.authorName
    }
}
