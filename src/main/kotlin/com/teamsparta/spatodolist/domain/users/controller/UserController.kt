package com.teamsparta.spatodolist.domain.users.controller

import com.teamsparta.spatodolist.domain.users.dtos.CreateUserArguments
import com.teamsparta.spatodolist.domain.users.dtos.SignInArguments
import com.teamsparta.spatodolist.domain.users.dtos.UserDto
import com.teamsparta.spatodolist.domain.users.service.UserService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RequestMapping("/api/v1/users")
@RestController
class UserController(
    private val userService: UserService,
) {
    //회원가입 == 등록
    @PostMapping("/sign-up")
    fun createUser(
        @RequestBody createUserArguments: CreateUserArguments,
    ): ResponseEntity<UserDto> {
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(userService.createUser(createUserArguments))
    }

    //로그인
    @PostMapping("/login")
    fun signIn(@RequestBody signInArguments: SignInArguments): ResponseEntity<UserDto> {
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(userService.signIn(signInArguments))
    }

}