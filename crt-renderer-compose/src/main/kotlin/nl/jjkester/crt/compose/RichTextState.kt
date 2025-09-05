package nl.jjkester.crt.compose

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import nl.jjkester.crt.api.annotations.InternalRendererApi
import nl.jjkester.crt.api.model.Node
import nl.jjkester.crt.api.parser.Parser
import nl.jjkester.crt.api.parser.ParserResult
import nl.jjkester.crt.api.parser.parse
import nl.jjkester.crt.compose.renderer.ComposableBlockTransformer
import nl.jjkester.crt.compose.renderer.ComposeRenderer
import java.io.InputStream

/**
 * Encapsulates the state for the [RichText] and [LazyRichText] composables.
 */
@OptIn(InternalRendererApi::class)
public interface RichTextState {

    public val result: ComposeRenderer.Result?

    /**
     * Whether a new state is loading. This value may be used to display a loading indication while the rich text is
     * parsed and pre-rendered.
     */
    public val isLoading: Boolean
}

@OptIn(InternalRendererApi::class)
@Stable
public class ParserRichTextState internal constructor(
    private val parser: Parser<*>,
    private val renderer: ComposeRenderer
) : RichTextState {

    public override var result: ComposeRenderer.Result? by mutableStateOf<ComposeRenderer.Result?>(null)
        private set

    public override var isLoading: Boolean by mutableStateOf(false)
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

    private suspend inline fun updateSource(crossinline body: suspend () -> ParserResult) {
        try {
            isLoading = true
            result = renderer.render(body().rootNode)
        } catch (e: Exception) {
            throw e
        } finally {
            isLoading = false
        }
    }
}

@OptIn(InternalRendererApi::class)
@Stable
public class NodeRichTextState internal constructor(
    rootNode: Node,
    renderer: ComposeRenderer
) : RichTextState {

    override val result: ComposeRenderer.Result? by lazy {
        renderer.render(rootNode)
    }

    override val isLoading: Boolean
        get() = false
}

/**
 * Parses and renders rich text and remembers it in the composition. A [parserFactory] to match the [source] must be
 * provided. Optionally a custom [renderer] can be passed to customize the rendering of the parsed text.
 *
 * @param source Source text to render.
 * @param parserFactory Factory function for the specific rich text parser to parse the [source].
 * @param renderer Compose rich text renderer to use.
 * @return State holder for the rich text composables.
 */
@Composable
public fun rememberRichTextState(
    source: String,
    parserFactory: () -> Parser<*>,
    renderer: ComposeRenderer = rememberRichTextRenderer()
): RichTextState = rememberRichTextState(parserFactory, renderer) { updateSource(source) }

/**
 * Parses and renders rich text and remembers it in the composition. A [parserFactory] to match the [source] must be
 * provided. Optionally a custom [renderer] can be passed to customize the rendering of the parsed text.
 *
 * @param source Source text to render.
 * @param parserFactory Factory function for the specific rich text parser to parse the [source].
 * @param renderer Compose rich text renderer to use.
 * @return State holder for the rich text composables.
 */
@Composable
public fun rememberRichTextState(
    source: InputStream,
    parserFactory: () -> Parser<*>,
    renderer: ComposeRenderer = rememberRichTextRenderer()
): RichTextState = rememberRichTextState(parserFactory, renderer) { updateSource(source) }

@Composable
private fun rememberRichTextState(
    parserFactory: () -> Parser<*>,
    renderer: ComposeRenderer,
    updateSource: suspend ParserRichTextState.() -> Unit
): RichTextState = remember(parserFactory, renderer) { ParserRichTextState(parserFactory(), renderer) }.also { state ->
    LaunchedEffect(state, updateSource) {
        state.updateSource()
    }
}

/**
 * Renders rich text and remembers it in the composition. Optionally a custom [renderer] can be passed to customize the
 * rendering of the parsed text.
 *
 * @param rootNode Node to render.
 * @param renderer Compose rich text renderer to use.
 * @return State holder for the rich text composables.
 */
@Composable
public fun rememberRichTextState(
    rootNode: Node,
    renderer: ComposeRenderer = rememberRichTextRenderer()
): RichTextState = remember(rootNode, renderer) { NodeRichTextState(rootNode, renderer) }

/**
 * Creates a default Compose Rich Text renderer.
 *
 * @return Default Compose Rich Text renderer.
 */
@Composable
public fun rememberRichTextRenderer(): ComposeRenderer = ComposeRenderer.Default

/**
 * Creates a Compose Rich Text renderer with customized rendering. The [ComposableBlockTransformer] returned by the
 * [transformerFactory] is used to determine the Composables for each parsed node.
 *
 * @param transformerFactory Factory function for a custom [ComposableBlockTransformer].
 * @return Compose Rich Text renderer using the provided [ComposableBlockTransformer].
 */
@Composable
public fun rememberRichTextRenderer(transformerFactory: () -> ComposableBlockTransformer): ComposeRenderer =
    remember(transformerFactory) {
        ComposeRenderer(transformerFactory())
    }
