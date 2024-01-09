package com.example.service

import com.example.model.ProductsSummary
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow

class PdfPrinterServiceTest {
    private val service = PdfPrinterService()

    @Test
    fun `pdf printer service print product summary should not throw any exceptions`() {
        assertDoesNotThrow { service.printProductSummaryToByteArray(ProductsSummary(emptyList(), 10)) }
    }
}
