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
import java.util.UUID

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
                    uuid = UUID.fromString("bfcfaa0a-e878-48b5-85c8-a4f20e52b3e4"),
                    name = "Diablo IV",
                    amount = 10L,
                ),
                Product(
                    id = 2,
                    uuid = UUID.fromString("bf70093e-a4d4-461b-86ff-6515feee9bb3"),
                    name = "Overwatch 2",
                    amount = 1L,
                ),
                Product(
                    id = 3,
                    uuid = UUID.fromString("d2c02017-61f7-4609-9fa0-da6887aff9c6"),
                    name = "Half-Life 3",
                    amount = 10000L,
                ),
                Product(
                    id = 4,
                    uuid = UUID.fromString("a4d5e97a-e2c6-46c6-aec0-ddc2a6ca5bfc"),
                    name = "CyberPunk 2077",
                    amount = 50L,
                ),
                Product(
                    id = 5,
                    uuid = UUID.fromString("1603b0c2-952e-4400-89b4-49d2e3ee0e62"),
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
                        uuid = UUID.fromString("bfcfaa0a-e878-48b5-85c8-a4f20e52b3e4"),
                        name = "Diablo IV",
                        amount = 10L,
                    ),
                    Product(
                        id = 2,
                        uuid = UUID.fromString("bf70093e-a4d4-461b-86ff-6515feee9bb3"),
                        name = "Overwatch 2",
                        amount = 1L,
                    ),
                    Product(
                        id = 4,
                        uuid = UUID.fromString("a4d5e97a-e2c6-46c6-aec0-ddc2a6ca5bfc"),
                        name = "CyberPunk 2077",
                        amount = 50L,
                    ),
                ),
                total = 61L,
            )
        service.getProductsSummary(
            listOf(
                UUID.fromString("bfcfaa0a-e878-48b5-85c8-a4f20e52b3e4"),
                UUID.fromString("bf70093e-a4d4-461b-86ff-6515feee9bb3"),
                UUID.fromString("a4d5e97a-e2c6-46c6-aec0-ddc2a6ca5bfc"),
            ),
        ) shouldBe expected
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
                    UUID.fromString("bfcfaa0a-e878-48b5-85c8-a4f20e52b3e4"),
                    UUID.fromString("bf70093e-a4d4-461b-86ff-6515feee9bb3"),
                    UUID.fromString("bfcfaa0a-e878-48b5-85c8-a4f20e52b3e5"),
                    UUID.fromString("bf70093e-a4d4-461b-86ff-6515feee9bb6"),
                ),
            )
        }.apply {
            message shouldBe "Requested ids [bfcfaa0a-e878-48b5-85c8-a4f20e52b3e5, " +
                "bf70093e-a4d4-461b-86ff-6515feee9bb6] not found"
        }
    }
}
