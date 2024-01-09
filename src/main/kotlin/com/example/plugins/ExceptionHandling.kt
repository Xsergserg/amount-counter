import com.example.exception.BadRequestException
import com.example.exception.ItemsNotFoundException
import com.example.exception.PdfWriterException
import io.ktor.http.HttpStatusCode.Companion.BadRequest
import io.ktor.http.HttpStatusCode.Companion.InternalServerError
import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.plugins.statuspages.StatusPages
import io.ktor.server.response.respond
import mu.KotlinLogging

val log = KotlinLogging.logger {}

fun Application.configureExceptionHandling() {
    install(StatusPages) {
        exception<Throwable> { call, e ->
            when (e) {
                is BadRequestException -> {
                    log.info(e) { "Bad request" }
                    call.respond(BadRequest)
                }

                is ItemsNotFoundException -> {
                    log.info(e) { "Items not found" }
                    call.respond(BadRequest)
                }

                is PdfWriterException -> {
                    log.info(e) { "Pdf writer error" }
                    call.respond(InternalServerError)
                }

                else -> {
                    log.error(e) { "Uncaught exception was thrown by service" }
                    call.respond(InternalServerError)
                }
            }
        }
    }
}
