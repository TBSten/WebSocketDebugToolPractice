package me.tbsten.prac.websocketdebugtool

import io.ktor.client.HttpClient
import io.ktor.client.request.url
import kotlinx.rpc.krpc.ktor.client.installRPC
import kotlinx.rpc.krpc.ktor.client.rpc
import kotlinx.rpc.krpc.ktor.client.rpcConfig
import kotlinx.rpc.krpc.serialization.json.json
import kotlinx.rpc.withService
import kotlinx.serialization.modules.SerializersModule
import me.tbsten.prac.websocketdebugtool.DebugService.Message.Companion.debugServiceMessageSerializer

suspend fun debugService() = rpcClient().withService<DebugService>()

private suspend fun rpcClient() = HttpClient { installRPC() }
    .run {
        println(":rpcClient.test-1")
        rpc {
            println(":rpcClient.test-2")
            url("ws://${SERVER_IP}:${SERVER_PORT}/debug")

            rpcConfig {
                serialization {
                    json {
                        serializersModule = SerializersModule {
                            debugServiceMessageSerializer()
                        }
                    }
                }
            }.also { println(":rpcClient.test-3") }
        }.also { println(":rpcClient.test-3") }
    }
