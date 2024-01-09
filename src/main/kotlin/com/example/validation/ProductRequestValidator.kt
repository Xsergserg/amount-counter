package com.example.validation

import com.example.exception.BadRequestException

class ProductRequestValidator {
    @Suppress("ThrowsCount")
    fun validateAndExecute(
        ids: String?,
        execute: String.() -> List<Long>,
    ): List<Long> {
        if (ids == null) {
            throw BadRequestException("'ids' parameter is required")
        }
        return runCatching { ids.execute() }
            .onFailure { ex ->
                throw BadRequestException(
                    "'ids' parameter has wrong format. Parameter should be formatted as '%d,%d,..'",
                    ex,
                )
            }
            .onSuccess { parsedIds -> if (parsedIds.isEmpty()) throw BadRequestException("'ids' list is empty") }
            .getOrThrow()
    }
}
