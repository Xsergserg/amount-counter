package com.example.plugins

import com.example.db.ProductsTable
import com.example.getTestDb
import com.example.initTestDb
import com.example.service.ProductsService
import io.kotest.assertions.assertSoftly
import io.kotest.matchers.shouldBe
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsText
import io.ktor.http.HttpStatusCode
import io.ktor.server.testing.ApplicationTestBuilder
import io.ktor.server.testing.testApplication
import kotlinx.serialization.json.Json
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.Test

class RoutingTest {
    private val db = getTestDb().also { initTestDb(it) }
    private val service = ProductsService(db)

    @AfterAll
    fun tearDown() {
        transaction(db) {
            SchemaUtils.drop(ProductsTable)
        }
    }

    @Test
    fun `get products endpoint should return correct JSON and status code OK(200)`() {
        testApplication {
            val expectedJson =
                Json.parseToJsonElement(
                    """
                [
                {
                "id": 1,
                "name": "Diablo IV",
                "amount": 10
                },
                {
                "id": 2,
                "name": "Overwatch 2",
                "amount": 1
                },
                {
                "id": 3,
                "name": "Half-Life 3",
                "amount": 10000
                },
                {
                "id": 4,
                "name": "CyberPunk 2077",
                "amount": 50
                },
                {
                "id": 5,
                "name": "God of War: Ragnarok",
                "amount": 5
                }
                ]
                """,
                )

            assertSoftly(testHttpClient().get("/api/getProducts")) {
                Json.parseToJsonElement(bodyAsText()) shouldBe expectedJson
                status shouldBe HttpStatusCode.OK
            }
        }
    }

    @Test
    fun `get summary endpoint should return status code OK(200)`() {
        testApplication {
            testHttpClient().get("/api/getSummary?ids=1,2,3").status shouldBe HttpStatusCode.OK
        }
    }

    private fun ApplicationTestBuilder.testHttpClient(): HttpClient {
        environment {
            module {
                configureSerialization()
                configureRouting(productsService = service)
            }
        }
        return createClient {}
    }
}
