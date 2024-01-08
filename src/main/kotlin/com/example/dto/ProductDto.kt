package com.example.dto

import kotlinx.serialization.Serializable

@Serializable
data class ProductDto(
    val name: String,
    val amount: Int,
)
