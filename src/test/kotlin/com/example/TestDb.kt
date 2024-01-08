package com.example

import org.jetbrains.exposed.sql.Database

fun getTestDb(): Database {
    return Database.connect(
        url = "jdbc:h2:mem:test;DB_CLOSE_DELAY=-1",
        user = "test",
        driver = "org.h2.Driver",
        password = "test",
    )
}
