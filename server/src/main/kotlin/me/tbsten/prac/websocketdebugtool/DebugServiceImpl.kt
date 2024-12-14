package me.tbsten.prac.websocketdebugtool

import kotlin.coroutines.CoroutineContext
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class DebugServiceImpl(override val coroutineContext: CoroutineContext) : DebugService {
    override suspend fun start(): Flow<DebugService.Message> {
        println("⭐️ start debug session")
        return flow {
            repeat(Int.MAX_VALUE) {
                emit(DebugService.Message.Test("Test-Message-$it"))
                println("⭐️ send test message")
                delay(500)
            }
        }
    }

    override suspend fun stop() {
        println("⭐️ stop debug session")
    }
}
