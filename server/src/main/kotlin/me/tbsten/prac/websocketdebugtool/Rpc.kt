package me.tbsten.prac.websocketdebugtool

import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.routing.routing
import kotlinx.rpc.krpc.ktor.server.RPC
import kotlinx.rpc.krpc.ktor.server.rpc
import kotlinx.rpc.krpc.serialization.json.json
import kotlinx.serialization.modules.SerializersModule
import me.tbsten.prac.websocketdebugtool.DebugService.Message.Companion.debugServiceMessageSerializer

internal fun Application.rpcModule() {
    install(RPC)

    routing {
        rpc("/debug") {
            rpcConfig {
                serialization {
                    json {
                        serializersModule = SerializersModule {
                            debugServiceMessageSerializer()
                        }
                    }
                }
            }

            registerService<DebugService> { ctx -> DebugServiceImpl(ctx) }
        }
    }
}
