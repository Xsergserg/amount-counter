package com.example.exception

class BadRequestException(override val message: String, override val cause: Throwable? = null) :
    RuntimeException(message, cause)
