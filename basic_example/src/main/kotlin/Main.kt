import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.routing.*
import io.ktor.server.response.*

fun main() {
    embeddedServer(Netty, port = 9590) {
        routing {
            get("/") {
                call.respondText("¡Ktorx funciona! 🌐")
            }
        }
    }.start(wait = true)
}