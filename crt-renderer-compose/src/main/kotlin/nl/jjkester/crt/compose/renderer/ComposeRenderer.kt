package nl.jjkester.crt.compose.renderer

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.toMutableStateList
import nl.jjkester.crt.api.annotations.InternalRendererApi
import nl.jjkester.crt.api.model.Container
import nl.jjkester.crt.api.model.Node
import nl.jjkester.crt.api.renderer.Renderer
import nl.jjkester.crt.api.renderer.SpanAsParagraphTransformer
import nl.jjkester.crt.api.renderer.transform
import nl.jjkester.crt.api.renderer.transformBlock
import nl.jjkester.crt.compose.style.LocalRichTextStyle

/**
 * Renderer to render rich text in Jetpack Compose.
 */
@OptIn(InternalRendererApi::class)
public class ComposeRenderer internal constructor(
    private val blockTransformer: ComposableBlockTransformer
) : Renderer<ComposeRenderer.Result> {

    private val transformer = SpanAsParagraphTransformer(blockTransformer)

    override fun render(node: Node): Result = if (node is Container) {
        node.children.map { transformer.transformBlock(it) }
            .let { children -> Result(blockTransformer.container(children), children.toMutableStateList()) }
    } else {
        transformer.transform(node).let { Result(it, mutableStateListOf(it)) }
    }

    internal companion object {
        /**
         * Default Compose renderer.
         */
        internal val Default = ComposeRenderer(
            DefaultComposableBlockTransformer {
                DefaultAnnotatedStringSpanTransformer(LocalRichTextStyle.current)
            }
        )
    }

    /**
     * Result of rendering rich text in Compose.
     *
     * @property single Single composable containing the rendered result for the whole rendered text.
     * @property lazy List of composables containing the rendered result for one or more parts of the rendered text.
     * This list is suitable for lazy rendering since the outer container, if one were present, is removed.
     */
    @InternalRendererApi
    @Immutable
    public class Result internal constructor(
        @Stable internal val single: @Composable () -> Unit,
        @Stable internal val lazy: List<@Composable () -> Unit>
    )
}
