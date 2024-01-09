package com.example.service

import com.example.db.ProductsTable
import com.example.exception.ItemsNotFoundException
import com.example.getTestDb
import com.example.initTestDb
import com.example.model.Product
import com.example.model.ProductsSummary
import io.kotest.matchers.shouldBe
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class ProductsServiceTest {
    private val db = getTestDb().also { initTestDb(it) }
    private val service = ProductsService(db)

    @AfterAll
    fun tearDown() {
        transaction(db) {
            SchemaUtils.drop(ProductsTable)
        }
    }

    @Test
    fun `get product list fun returns correct list of values`() {
        val expected =
            listOf(
                Product(
                    id = 1,
                    name = "Diablo IV",
                    amount = 10L,
                ),
                Product(
                    id = 2,
                    name = "Overwatch 2",
                    amount = 1L,
                ),
                Product(
                    id = 3,
                    name = "Half-Life 3",
                    amount = 10000L,
                ),
                Product(
                    id = 4,
                    name = "CyberPunk 2077",
                    amount = 50L,
                ),
                Product(
                    id = 5,
                    name = "God of War: Ragnarok",
                    amount = 5L,
                ),
            )
        service.getAllProducts() shouldBe expected
    }

    @Test
    fun `get products summary fun returns correct list of values`() {
        val expected =
            ProductsSummary(
                products =
                listOf(
                    Product(
                        id = 1,
                        name = "Diablo IV",
                        amount = 10L,
                    ),
                    Product(
                        id = 2,
                        name = "Overwatch 2",
                        amount = 1L,
                    ),
                    Product(
                        id = 4,
                        name = "CyberPunk 2077",
                        amount = 50L,
                    ),
                ),
                total = 61L,
            )
        service.getProductsSummary(listOf(1, 2, 4)) shouldBe expected
    }

    @Test
    fun `get products summary fun returns empty product summary in case of empty list as argument`() {
        service.getProductsSummary(emptyList()) shouldBe
            ProductsSummary(products = emptyList(), total = 0L)
                .also { it.isEmpty() shouldBe true }
    }

    @Test
    fun `get products summary fun throw exception in case of not exists values provided`() {
        assertThrows<ItemsNotFoundException> {
            service.getProductsSummary(
                listOf(
                    1,
                    2,
                    6,
                    8,
                ),
            )
        }.apply { message shouldBe "Requested ids [6, 8] not found" }
    }
}
