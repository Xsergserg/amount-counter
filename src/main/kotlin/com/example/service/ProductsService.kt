package com.example.service

import com.example.db.ProductsTable
import com.example.exception.ItemsNotFoundException
import com.example.model.Product
import com.example.model.ProductsSummary
import com.example.model.toProduct
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction

class ProductsService(private val db: Database) {
    fun getAllProducts(): List<Product> {
        return transaction(db) { ProductsTable.selectAll().map { it.toProduct() }.sortedBy { it.id } }
    }

    fun getProductsSummary(ids: List<Long>): ProductsSummary {
        val products =
            transaction(db) {
                ProductsTable.select { ProductsTable.id inList ids }.map { it.toProduct() }.sortedBy { it.id }
            }
        if (products.size != ids.size) {
            throw ItemsNotFoundException(ids.filter { it !in products.map { product -> product.id } })
        }
        return ProductsSummary(
            products = products,
            total = products.sumOf { it.amount },
        )
    }
}
