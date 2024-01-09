package com.example.plugins

import io.ktor.server.application.Application
import io.ktor.server.plugins.swagger.swaggerUI
import io.ktor.server.routing.routing

fun Application.configureSwagger() {
    routing {
        swaggerUI(path = "/docs/swagger", swaggerFile = "openapi/documentation.yaml")
    }
}
