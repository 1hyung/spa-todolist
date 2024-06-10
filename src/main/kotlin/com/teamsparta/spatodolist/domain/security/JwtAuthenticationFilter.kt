package com.teamsparta.spatodolist.domain.security

import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.HttpHeaders
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter

@Component
class JwtAuthenticationFilter(
    private val jwtPlugin: JwtPlugin,
) : OncePerRequestFilter() {

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain,
    ) {
        val jwt = request.getBearerToken()

        jwt?.let { token ->
            jwtPlugin.validateToken(token)
                .onSuccess { decoded ->
                    val userId = decoded.payload.subject.toLong()
                    val userName = decoded.payload.get("username", String::class.java)

                    val userPrincipal = UserPrincipal(userId, userName)
                    val detail = WebAuthenticationDetailsSource().buildDetails(request)

                    val auth = JwtAuthenticationToken(userPrincipal, detail)

                    SecurityContextHolder.getContext().authentication = auth

                }
        }
        filterChain.doFilter(request, response)
    }


    //헤더에서 요청받은 값을 꺼낸 후 Bearer 글자를 제거하고 순수 토큰 값만 받아올 것이다!
    private fun HttpServletRequest.getBearerToken(): String? {
        val headerValue = this.getHeader(HttpHeaders.AUTHORIZATION) ?: return null
        return Regex("^Bearer (.+?)$").find(headerValue)?.groupValues?.get(1)
    }
}