package me.tbsten.prac.websocketdebugtool

import kotlinx.coroutines.flow.Flow
import kotlinx.rpc.RemoteService
import kotlinx.rpc.annotations.Rpc
import kotlinx.serialization.Serializable
import kotlinx.serialization.modules.SerializersModuleBuilder
import kotlinx.serialization.modules.polymorphic

const val SERVER_IP = "192.168.0.9"
const val SERVER_PORT = 8080

@Rpc
interface DebugService : RemoteService {
    suspend fun start(): Flow<Message>

    suspend fun stop()

    @Serializable
    sealed interface Message {
        @Serializable
        data class Test(val message: String) : Message

        companion object {
            fun SerializersModuleBuilder.debugServiceMessageSerializer() {
                polymorphic(Message::class) {
                    subclass(Test::class, Test.serializer())
                }
            }
        }
    }
}
