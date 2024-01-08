package com.example.plugins

import com.example.db.ProductsTable
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction

fun configureDatabases(): Database {
    return Database.connect(
        url = "jdbc:h2:mem:test;DB_CLOSE_DELAY=-1",
        user = "root",
        driver = "org.h2.Driver",
        password = "",
    ).also { initDb(it) }
}

fun initDb(db: Database) {
    transaction(db) {
        SchemaUtils.create(ProductsTable)
    }
}
