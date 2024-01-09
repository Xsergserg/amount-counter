package com.example.plugins

import com.example.db.ProductsTable
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.transactions.transaction
import java.util.UUID

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
            it[uuid] = UUID.fromString("5d6aa284-0f05-49f5-b563-f83c3d2ffd90")
            it[name] = "Diablo IV"
            it[amount] = 10L
        }
        ProductsTable.insert {
            it[uuid] = UUID.fromString("77ac3a82-6a9c-427e-b281-a0a73b8a5847")
            it[name] = "Overwatch 2"
            it[amount] = 1L
        }
        ProductsTable.insert {
            it[uuid] = UUID.fromString("1e0b7134-cfd1-4a0b-89b2-9c2616478f36")
            it[name] = "Half-Life 3"
            it[amount] = 10000L
        }
        ProductsTable.insert {
            it[uuid] = UUID.fromString("d8806e47-5aa1-4dc1-8aba-f0c98d063422")
            it[name] = "CyberPunk 2077"
            it[amount] = 50L
        }
        ProductsTable.insert {
            it[uuid] = UUID.fromString("56795b27-73e5-4d59-b579-15bf14452302")
            it[name] = "God of War: Ragnarok"
            it[amount] = 5L
        }
    }
}
