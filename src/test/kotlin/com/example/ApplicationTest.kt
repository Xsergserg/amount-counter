package com.example

import com.example.plugins.configureLogging
import com.example.plugins.configureMonitoring
import com.example.plugins.configureRouting
import com.example.plugins.configureSerialization
import com.example.plugins.configureSwagger
import com.example.service.ProductsService
import io.kotest.assertions.throwables.shouldNotThrowAny
import io.ktor.server.testing.testApplication
import org.junit.jupiter.api.Test

class ApplicationTest {
    @Test
    fun `app should be configured without any exceptions`() {
        shouldNotThrowAny {
            testApplication {
                application {
                    val db = getTestDb()

                    configureLogging()
                    configureMonitoring()
                    configureSerialization()
                    configureRouting(ProductsService(db))
                    configureSwagger()
                }
            }
        }
    }
}
