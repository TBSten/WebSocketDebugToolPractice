package me.tbsten.prac.websocketdebugtool

import io.ktor.server.application.Application
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import io.ktor.server.response.respondText
import io.ktor.server.routing.get
import io.ktor.server.routing.routing

fun main() {
    embeddedServer(Netty, port = SERVER_PORT, host = SERVER_IP, module = Application::module)
        .start(wait = true)
}

private fun Application.module() {
    routing {
        get("/health") {
            call.respondText("Hello")
        }
    }
    rpcModule()
}
