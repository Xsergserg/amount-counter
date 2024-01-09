package com.example.plugins

import com.example.db.ProductsTable
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.transactions.transaction

fun configureDatabases(): Database {
    return Database.connect(
        url = "jdbc:h2:mem:test;DB_CLOSE_DELAY=-1",
        user = "root",
        driver = "org.h2.Driver",
        password = "",
    ).also { initDb(it) }
}

@Suppress("MagicNumber")
fun initDb(db: Database) {
    transaction(db) {
        SchemaUtils.create(ProductsTable)
        ProductsTable.insert {
            it[name] = "Diablo IV"
            it[amount] = 10L
        }
        ProductsTable.insert {
            it[name] = "Overwatch 2"
            it[amount] = 1L
        }
        ProductsTable.insert {
            it[name] = "Half-Life 3"
            it[amount] = 10000L
        }
        ProductsTable.insert {
            it[name] = "CyberPunk 2077"
            it[amount] = 50L
        }
        ProductsTable.insert {
            it[name] = "God of War: Ragnarok"
            it[amount] = 5L
        }
    }
}
