package com.example.dto

import com.example.model.Product
import kotlinx.serialization.Serializable

@Serializable
data class ProductDto(
    val id: String,
    val name: String,
    val amount: Long,
)

fun Product.toProductDto(): ProductDto {
    return ProductDto(
        id = uuid.toString(),
        name = name,
        amount = amount,
    )
}
