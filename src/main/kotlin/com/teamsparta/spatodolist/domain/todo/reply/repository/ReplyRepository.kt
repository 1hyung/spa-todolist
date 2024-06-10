package com.teamsparta.spatodolist.domain.todo.reply.repository

import com.teamsparta.spatodolist.domain.todo.reply.model.Reply
import org.springframework.data.jpa.repository.JpaRepository

interface ReplyRepository : JpaRepository<Reply, Long>