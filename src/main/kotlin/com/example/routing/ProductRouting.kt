package com.example.routing

import com.example.dto.toProductDto
import com.example.exception.PdfWriterException
import com.example.service.PdfPrinterService
import com.example.service.ProductsService
import com.example.validation.ProductRequestValidator
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.server.application.Application
import io.ktor.server.application.call
import io.ktor.server.response.header
import io.ktor.server.response.respond
import io.ktor.server.response.respondOutputStream
import io.ktor.server.routing.get
import io.ktor.server.routing.post
import io.ktor.server.routing.route
import io.ktor.server.routing.routing

fun Application.configureProductRouting(
    productsService: ProductsService,
    pdfPrinterService: PdfPrinterService = PdfPrinterService(),
    validator: ProductRequestValidator = ProductRequestValidator(),
) {
    routing {
        route("/api") {
            get("/getProducts") {
                call.respond(productsService.getAllProducts().map { it.toProductDto() })
            }

            post("/getSummary") {
                val uuids = validator.validateRequestBodyAndReturnUuids(call)
                val productsSummary = productsService.getProductsSummary(uuids)
                runCatching {
                    val outputStream = pdfPrinterService.printProductSummaryToByteArray(productsSummary)
                    call.response.header(HttpHeaders.ContentDisposition, "attachment; filename=SummaryTable.pdf")
                    call.respondOutputStream(ContentType.Application.OctetStream) {
                        outputStream.use { it.writeTo(this) }
                    }
                }
                    .onFailure { ex -> throw PdfWriterException(ex) }
                    .getOrThrow()
            }
        }
    }
}
