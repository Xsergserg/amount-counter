package com.example.service

import com.example.model.Product
import org.jetbrains.exposed.sql.Database
import java.io.File

class ProductsService(private val db: Database) {
    fun getProductList(ids: List<Int>): List<Product> {
        TODO("Not yet implemented")
    }

    fun getSummaryPdf(ids: List<Int>): File {
        TODO("Not yet implemented")
    }
}
