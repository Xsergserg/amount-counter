package com.example.validation

import com.example.dto.IdsRequestDto
import com.example.exception.BadRequestException
import io.ktor.server.application.ApplicationCall
import io.ktor.server.request.receive
import java.util.UUID

class ProductRequestValidator {
    suspend fun validateRequestBodyAndReturnUuids(call: ApplicationCall): List<UUID> {
        return runCatching {
            call.receive<IdsRequestDto>().ids.takeUnless { it.isEmpty() }?.map(UUID::fromString)
                ?: throw BadRequestException("'ids' list is empty")
        }
            .onFailure { ex ->
                throw BadRequestException("Request body should contain ids list in form '{\"ids\":[\"id1\",\"id2\"]}'", ex)
            }
            .getOrThrow()
    }
}
