package com.teamsparta.spatodolist.domain.security

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jws
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import org.springframework.stereotype.Component
import java.nio.charset.StandardCharsets
import java.time.Instant
import java.time.Duration
import java.util.*

@Component
class JwtPlugin {

    companion object {
        const val ISSUER = "team.sparta.com"
        const val SECRET = "PO4c8z41Hia5gJG3oeuFJMRYBB4Ws4aZ"
        const val ACCESS_TOKEN_EXPIRATION_HOUR: Long = 168
    }

    fun validateToken(token: String): Result<Jws<Claims>> {
        return kotlin.runCatching {
            val key = Keys.hmacShaKeyFor(SECRET.toByteArray(StandardCharsets.UTF_8))
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token)
        }
    }

    fun generateAccessToken(subject: String, userName: String): String {
        return generateToken(subject, userName, Duration.ofHours(ACCESS_TOKEN_EXPIRATION_HOUR))
    }

    private fun generateToken(subject: String, userName: String, expirationPeriod: Duration): String {
        val now = Instant.now()
        val key = Keys.hmacShaKeyFor(SECRET.toByteArray(StandardCharsets.UTF_8))

        val claims = Jwts.claims().apply {
            this.subject = subject
            this.issuer = ISSUER
            this.issuedAt = Date.from(now)
            this.expiration = Date.from(now.plus(expirationPeriod))
            this["username"] = userName
        }

        return Jwts.builder()
            .setClaims(claims)
            .signWith(key)
            .compact()
    }
}
