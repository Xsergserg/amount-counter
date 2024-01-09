package com.example.dto

import kotlinx.serialization.Serializable

@Serializable
data class IdsRequestDto(
    val ids: List<String>,
)
