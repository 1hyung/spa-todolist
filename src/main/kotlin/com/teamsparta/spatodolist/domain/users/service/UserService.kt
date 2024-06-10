package com.teamsparta.spatodolist.domain.users.service

import com.teamsparta.spatodolist.domain.security.JwtPlugin
import com.teamsparta.spatodolist.domain.users.dtos.CreateUserArguments
import com.teamsparta.spatodolist.domain.users.dtos.SignInArguments
import com.teamsparta.spatodolist.domain.users.dtos.UserDto
import com.teamsparta.spatodolist.domain.users.model.Users
import com.teamsparta.spatodolist.domain.users.repository.UserRepository
import jakarta.transaction.Transactional
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class UserService (
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder,
    private val jwtPlugin: JwtPlugin
){

    @Transactional
    fun createUser(createUserArguments: CreateUserArguments) : UserDto {
        val user = Users(
            userName = createUserArguments.userName,
            password = passwordEncoder.encode(createUserArguments.password)
        )

        val saveUser = userRepository.save(user)

        return UserDto.from(saveUser)

    }

    fun signIn(signInArguments: SignInArguments): UserDto {
        //TODO(로그인의 흐름 - 아이디가 존재하는가?)
        val user = userRepository.findByUserName(signInArguments.userName) ?: throw  Exception("user is not found")

        //TODO(로그인의 흐름 - 패스워드가 올바른가? 사용자 아이디까지 올바른다?)
        if(user.userName != signInArguments.userName ||
            !passwordEncoder.matches(signInArguments.password, user.password)){
            throw Exception("authentication fail")
        }
        //TODO(로그인의 흐름 - 조건을 통과했다면 로그인(토큰발급) )
        val token = jwtPlugin.generateAccessToken(
            subject = user.id.toString(),
            userName = user.userName
        )

        return UserDto.from(user, token)

    }
}