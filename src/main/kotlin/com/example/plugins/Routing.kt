package com.example.plugins

import com.example.service.ProductsService
import io.ktor.server.application.Application
import io.ktor.server.routing.get
import io.ktor.server.routing.routing

fun Application.configureRouting(productsService: ProductsService) {
    routing {
        get("/getProducts") {
            TODO("Not yet implemented")
        }

        get("/getSummary") {
            TODO("Not yet implemented")
        }
    }
}
