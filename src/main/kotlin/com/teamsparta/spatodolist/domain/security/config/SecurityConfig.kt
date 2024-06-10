package com.teamsparta.spatodolist.domain.security.config

import com.teamsparta.spatodolist.domain.security.JwtAuthenticationFilter
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

@Configuration
@EnableWebSecurity
class SecurityConfig (
    private val jwtAuthenticationFilter: JwtAuthenticationFilter
){

    @Bean
    fun filterChain(http : HttpSecurity) : SecurityFilterChain {
        return http
            .httpBasic{it.disable()}
            .formLogin{it.disable()}
            .csrf{it.disable()}
            .authorizeHttpRequests{
                it
                    .requestMatchers("/swagger-ui/**").permitAll()
                    .requestMatchers("/v3/api-docs/**").permitAll()
                    .requestMatchers(HttpMethod.POST, "/api/v1/users/sign-up").permitAll()
                    .requestMatchers(HttpMethod.POST, "/api/v1/users/login").permitAll()
                    .anyRequest().authenticated()
            }
            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter::class.java)
            .build()
    }
}