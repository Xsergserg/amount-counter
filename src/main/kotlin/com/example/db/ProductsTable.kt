package com.example.db

import org.jetbrains.exposed.sql.Table

private const val VARCHAR_LENGTH = 256

object ProductsTable : Table("Products") {
    val id = long("id").uniqueIndex().autoIncrement()
    val name = varchar("name", VARCHAR_LENGTH)
    val amount = long("amount")

    override val primaryKey = PrimaryKey(id, name = "PK_Products_Id")
}
