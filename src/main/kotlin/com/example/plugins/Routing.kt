package com.example.plugins

import com.example.routing.configureProductRouting
import com.example.service.ProductsService
import io.ktor.server.application.Application
import org.jetbrains.exposed.sql.Database

fun Application.configureRouting(
    db: Database,
) {
    configureProductRouting(ProductsService(db))
}
