package com.example.db

import org.jetbrains.exposed.sql.Table

object ProductsTable : Table() {
    val id = integer("id").autoIncrement()
    val name = varchar("name", 256)
    val amount = long("age")

    override val primaryKey = PrimaryKey(id)
}
