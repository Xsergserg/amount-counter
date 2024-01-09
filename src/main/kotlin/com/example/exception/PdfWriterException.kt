package com.example.exception

class PdfWriterException(override val cause: Throwable? = null) :
    RuntimeException("Pdf writer exception", cause)
