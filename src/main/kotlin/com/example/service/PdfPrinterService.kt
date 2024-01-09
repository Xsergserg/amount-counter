package com.example.service

import com.example.model.ProductsSummary
import com.lowagie.text.Document
import com.lowagie.text.FontFactory
import com.lowagie.text.PageSize
import com.lowagie.text.Phrase
import com.lowagie.text.Rectangle
import com.lowagie.text.pdf.PdfPCell
import com.lowagie.text.pdf.PdfPTable
import com.lowagie.text.pdf.PdfWriter
import java.io.ByteArrayOutputStream

private const val COLUMN_RATIO = 33.33f
private const val PAGE_INDENTATION = 72
private const val FONT_SIZE = 8f

class PdfPrinterService {
    fun printProductSummaryToByteArray(productsSummary: ProductsSummary): ByteArrayOutputStream {
        val document = Document(PageSize.A4)
        val outputStream = ByteArrayOutputStream()
        PdfWriter.getInstance(document, outputStream)
        document.open()
        document.add(createPdfTable(document.pageSize.width, productsSummary))
        document.close()
        return outputStream
    }

    private fun createPdfTable(
        pageWidth: Float,
        productsSummary: ProductsSummary,
    ): PdfPTable {
        val columnDefinitionSize = floatArrayOf(COLUMN_RATIO, COLUMN_RATIO, COLUMN_RATIO)
        return PdfPTable(columnDefinitionSize).apply {
            defaultCell.border = Rectangle.BOX
            horizontalAlignment = 0
            totalWidth = pageWidth - PAGE_INDENTATION
            isLockedWidth = true
            addCells(productsSummary, columnDefinitionSize.size)
        }
    }

    private fun PdfPTable.addCells(
        productsSummary: ProductsSummary,
        size: Int,
    ) {
        addCell(
            PdfPCell(Phrase("Summary table")).apply {
                colspan = size
            },
        )
        productsSummary.products.map {
            addTextCell(it.id)
            addTextCell(it.name)
            addTextCell(it.amount)
        }
        addTextCell("")
        addTextCell("Total")
        addTextCell(productsSummary.total)
    }

    private fun PdfPTable.addTextCell(value: Any) {
        return addCell(Phrase(value.toString(), FontFactory.getFont(FontFactory.HELVETICA, FONT_SIZE)))
    }
}
