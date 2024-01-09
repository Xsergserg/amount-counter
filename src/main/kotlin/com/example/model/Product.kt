package com.example.model

import com.example.db.ProductsTable
import org.jetbrains.exposed.sql.ResultRow

data class Product(
    val id: Long,
    val name: String,
    val amount: Long,
)

fun ResultRow.toProduct(): Product {
    return Product(
        id = get(ProductsTable.id),
        name = get(ProductsTable.name),
        amount = get(ProductsTable.amount),
    )
}
