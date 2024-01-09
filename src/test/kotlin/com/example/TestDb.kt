package com.example

import com.example.db.ProductsTable
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.transactions.transaction
import java.util.UUID

fun getTestDb(): Database {
    return Database.connect(
        url = "jdbc:h2:mem:test;DB_CLOSE_DELAY=-1",
        user = "test",
        driver = "org.h2.Driver",
        password = "test",
    )
}

fun initTestDb(db: Database) {
    transaction(db) {
        SchemaUtils.create(ProductsTable)
        ProductsTable.insert {
            it[id] = 1L
            it[uuid] = UUID.fromString("bfcfaa0a-e878-48b5-85c8-a4f20e52b3e4")
            it[name] = "Diablo IV"
            it[amount] = 10L
        }
        ProductsTable.insert {
            it[id] = 2L
            it[uuid] = UUID.fromString("bf70093e-a4d4-461b-86ff-6515feee9bb3")
            it[name] = "Overwatch 2"
            it[amount] = 1L
        }
        ProductsTable.insert {
            it[id] = 3L
            it[uuid] = UUID.fromString("d2c02017-61f7-4609-9fa0-da6887aff9c6")
            it[name] = "Half-Life 3"
            it[amount] = 10000L
        }
        ProductsTable.insert {
            it[id] = 4L
            it[uuid] = UUID.fromString("a4d5e97a-e2c6-46c6-aec0-ddc2a6ca5bfc")
            it[name] = "CyberPunk 2077"
            it[amount] = 50L
        }
        ProductsTable.insert {
            it[id] = 5L
            it[uuid] = UUID.fromString("1603b0c2-952e-4400-89b4-49d2e3ee0e62")
            it[name] = "God of War: Ragnarok"
            it[amount] = 5L
        }
    }
}
