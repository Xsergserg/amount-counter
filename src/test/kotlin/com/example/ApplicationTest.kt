package com.example

import com.example.plugins.configureMonitoring
import com.example.plugins.configureRouting
import com.example.plugins.configureSerialization
import com.example.plugins.configureSwagger
import io.kotest.assertions.throwables.shouldNotThrowAny
import io.kotest.matchers.shouldBe
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.http.HttpStatusCode.Companion.OK
import io.ktor.server.testing.ApplicationTestBuilder
import io.ktor.server.testing.testApplication
import org.junit.jupiter.api.Test

class ApplicationTest {
    @Test
    fun `app should be configured without any exceptions`() {
        shouldNotThrowAny {
            testApplication {
                application {
                    val db = getTestDb()
                    configurePlugins()
                    configureRouting(db)
                }
            }
        }
    }

    @Test
    fun `monitoring request returns OK(200) response`() {
        testApplication {
            testHttpClient().get("/metrics-micrometer").status shouldBe OK
        }
    }

    @Test
    fun `swagger request returns OK(200) response`() {
        testApplication {
            testHttpClient().get("/docs/swagger").status shouldBe OK
        }
    }

    private fun ApplicationTestBuilder.testHttpClient(): HttpClient {
        environment {
            module {
                configureMonitoring()
                configureSwagger()
                configureSerialization()
            }
        }
        return createClient {}
    }
}
