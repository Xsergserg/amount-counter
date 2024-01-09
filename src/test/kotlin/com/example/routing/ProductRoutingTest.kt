package com.example.routing

import com.example.db.ProductsTable
import com.example.getTestDb
import com.example.initTestDb
import com.example.plugins.configureSerialization
import com.example.service.PdfPrinterService
import com.example.service.ProductsService
import configureExceptionHandling
import io.kotest.assertions.assertSoftly
import io.kotest.matchers.shouldBe
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsText
import io.ktor.http.HttpStatusCode
import io.ktor.server.testing.ApplicationTestBuilder
import io.ktor.server.testing.testApplication
import io.mockk.every
import io.mockk.mockk
import kotlinx.serialization.json.Json
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.Test

class ProductRoutingTest {
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
                "id": "bfcfaa0a-e878-48b5-85c8-a4f20e52b3e4",
                "name": "Diablo IV",
                "amount": 10
                },
                {
                "id": "bf70093e-a4d4-461b-86ff-6515feee9bb3",
                "name": "Overwatch 2",
                "amount": 1
                },
                {
                "id": "d2c02017-61f7-4609-9fa0-da6887aff9c6",
                "name": "Half-Life 3",
                "amount": 10000
                },
                {
                "id": "a4d5e97a-e2c6-46c6-aec0-ddc2a6ca5bfc",
                "name": "CyberPunk 2077",
                "amount": 50
                },
                {
                "id": "1603b0c2-952e-4400-89b4-49d2e3ee0e62",
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
            testHttpClient().get(
                "/api/getSummary?ids=bfcfaa0a-e878-48b5-85c8-a4f20e52b3e4," +
                    "bf70093e-a4d4-461b-86ff-6515feee9bb3,d2c02017-61f7-4609-9fa0-da6887aff9c6",
            ).status shouldBe HttpStatusCode.OK
        }
    }

    @Test
    fun `get summary endpoint should return status code BadRequest(400) in case of not existed ids requested`() {
        testApplication {
            testHttpClient().get(
                "/api/getSummary?ids=bfcfaa0a-e878-48b5-85c8-a4f20e52b3e4," +
                    "bf70093e-a4d4-461b-86ff-6515feee9bb3,bf70093e-a4d4-461b-86ff-6515feee9bb4",
            ).status shouldBe HttpStatusCode.BadRequest
        }
    }

    @Test
    fun `get summary endpoint should return status code BadRequest(400) in case ids not provided`() {
        testApplication {
            testHttpClient().get("/api/getSummary").status shouldBe HttpStatusCode.BadRequest
        }
    }

    @Test
    fun `get summary endpoint should return status code internal server error (500) in case ids not provided`() {
        testApplication {
            val pdfPrinterService = mockk<PdfPrinterService>().also {
                every { it.printProductSummaryToByteArray(any()) } throws NumberFormatException()
            }
            testHttpClient(pdfPrinterService = pdfPrinterService).get(
                "/api/getSummary?ids=1,2,3",
            ).status shouldBe HttpStatusCode.InternalServerError
        }
    }

    @Test
    fun `get summary endpoint should return status code internal server error (500) in case of uncaught exception`() {
        testApplication {
            val productsService = mockk<ProductsService>().also {
                every { it.getProductsSummary(any()) } throws NullPointerException()
            }
            testHttpClient(productsService).get(
                "/api/getSummary?ids=1,2,3",
            ).status shouldBe HttpStatusCode.InternalServerError
        }
    }

    private fun ApplicationTestBuilder.testHttpClient(
        productsService: ProductsService = service,
        pdfPrinterService: PdfPrinterService = PdfPrinterService(),
    ): HttpClient {
        environment {
            module {
                configureExceptionHandling()
                configureSerialization()
                configureProductRouting(productsService, pdfPrinterService)
            }
        }
        return createClient {}
    }
}
