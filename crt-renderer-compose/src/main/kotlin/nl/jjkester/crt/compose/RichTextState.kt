package nl.jjkester.crt.compose

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import nl.jjkester.crt.api.model.Node
import nl.jjkester.crt.api.parser.Parser
import nl.jjkester.crt.api.parser.ParserResult
import java.io.InputStream

class RichTextState internal constructor(private val parser: Parser<*>) {

    internal var rootNode by mutableStateOf<Node?>(null)
        private set

    var isLoading by mutableStateOf(false)
        private set

    internal suspend fun updateSource(string: String) {
        updateSource {
            withContext(Dispatchers.Default) {
                parser.parse(string)
            }
        }
    }

    internal suspend fun updateSource(inputStream: InputStream) {
        updateSource {
            withContext(Dispatchers.IO) {
                parser.parse(inputStream)
            }
        }
    }

    private suspend fun updateSource(body: suspend () -> ParserResult) {
        try {
            isLoading = true
            rootNode = body().rootNode
        } catch (e: Exception) {
            throw e
        } finally {
            isLoading = false
        }
    }
}

@Composable
fun rememberRichTextState(source: String, parserFactory: () -> Parser<*>): RichTextState {
    val state = remember(parserFactory) { RichTextState(parserFactory()) }
    LaunchedEffect(source) {
        state.updateSource(source)
    }
    return state
}

@Composable
fun rememberRichTextState(source: InputStream, parserFactory: () -> Parser<*>): RichTextState {
    val state = remember(parserFactory) { RichTextState(parserFactory()) }
    LaunchedEffect(source) {
        state.updateSource(source)
    }
    return state
}
