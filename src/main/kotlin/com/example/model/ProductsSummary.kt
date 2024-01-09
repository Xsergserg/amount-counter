package com.example.model

data class ProductsSummary(val products: List<Product>, val total: Long) {
    fun isEmpty(): Boolean {
        return products.isEmpty()
    }
}
