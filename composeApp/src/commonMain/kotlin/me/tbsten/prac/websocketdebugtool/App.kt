package me.tbsten.prac.websocketdebugtool

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.withContext
import kotlinx.rpc.krpc.streamScoped
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {
    MaterialTheme {
        DebugMenu(
            onStart = { println("DebugMenu.onStart") },
            onStop = { println("DebugMenu.onStop") },
            modifier = Modifier.windowInsetsPadding(WindowInsets.systemBars),
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun DebugMenu(onStart: () -> Unit, onStop: () -> Unit, modifier: Modifier = Modifier) {
    var show by rememberSaveable { mutableStateOf(false) }
    var debugService by remember { mutableStateOf<DebugService?>(null) }
    val messages = remember { mutableStateListOf<DebugService.Message>() }

    LazyColumn(modifier.fillMaxWidth()) {
        stickyHeader {
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                Button(onClick = { show = !show }) {
                    Text(
                        when {
                            !show -> "debug: disabled"
                            debugService == null -> "debug: enabled (loading)"
                            debugService != null -> "debug: enabled"
                            else -> "Illegal State"
                        },
                    )
                }
            }
        }
        items(messages) { message ->
            Row(Modifier.fillMaxWidth().padding(16.dp)) {
                Text("$message")
            }
        }
    }

    LaunchedEffect(show) {
        if (show) {
            onStart()
            debugService?.start()
        } else {
            onStop()
            debugService?.stop()
        }
    }

    if (show) {
        LaunchedEffect(Unit) {
            streamScoped {
                withContext(Dispatchers.IO) {
                    debugService = debugService().apply {
                        start().collect { message ->
                            messages.add(message)
                        }
                    }
                }
            }
        }
    }
}
