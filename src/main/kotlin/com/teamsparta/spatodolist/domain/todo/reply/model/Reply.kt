package com.teamsparta.spatodolist.domain.todo.reply.model

import com.teamsparta.spatodolist.domain.todo.todocard.model.TodoCards
import com.teamsparta.spatodolist.domain.users.model.Users
import jakarta.persistence.*

@Entity
class Reply(
    @Column
    var content: String,
    @Column
    var authorName: String,
    @Column
    var password: String,

    @ManyToOne
    val todoCards: TodoCards,

    @ManyToOne
    val author: Users,

    ) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null

    fun checkAuthorization(authorName: String, password: String) {
        if (authorName != this.authorName) {
            throw Exception("아이디가 맞지 않습니다.")
        }
        if (password != this.password) {
            throw Exception("비밀번호가 맞지 않습니다.")
        }
    }

    fun checkAuthorization(requestUser: Users) {
        if (requestUser.id != author.id) {
            throw Exception("not permitted")
        }

    }

    fun changeContent(content: String) {
        this.content = content
    }
}