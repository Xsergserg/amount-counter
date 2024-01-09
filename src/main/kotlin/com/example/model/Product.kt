package com.example.model

import com.example.db.ProductsTable
import org.jetbrains.exposed.sql.ResultRow
import java.util.UUID

data class Product(
    val id: Long,
    val uuid: UUID,
    val name: String,
    val amount: Long,
)

fun ResultRow.toProduct(): Product {
    return Product(
        id = get(ProductsTable.id),
        uuid = get(ProductsTable.uuid),
        name = get(ProductsTable.name),
        amount = get(ProductsTable.amount),
    )
}
