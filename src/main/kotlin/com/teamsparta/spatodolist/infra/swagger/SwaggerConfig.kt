package com.teamsparta.spatodolist.infra.swagger

import io.swagger.v3.oas.models.Components
import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Info
import org.springframework.context.annotation.Configuration

@Configuration
class SwaggerConfig {

    fun openAPI(): OpenAPI = OpenAPI()
        .components(Components())
        .info(
            Info()
                .title("Todolist API")
                .description("Todolist API schema")
                .version("v1.0.0"))
}