package nl.jjkester.crt.compose.renderer

import androidx.compose.runtime.Composable
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

@OptIn(InternalRendererApi::class)
class ComposeRenderer internal constructor(
    private val blockTransformer: ComposableBlockTransformer
) : Renderer<ComposeRenderer.Result> {

    private val transformer = SpanAsParagraphTransformer(blockTransformer)

    override fun render(node: Node): Result = if (node is Container) {
        node.children.map { transformer.transformBlock(it) }
            .let { children -> Result(blockTransformer.container(children), children.toMutableStateList()) }
    } else {
        transformer.transform(node).let { Result(it, mutableStateListOf(it)) }
    }

    companion object {
        internal val Default = ComposeRenderer(
            DefaultComposableBlockTransformer {
                DefaultAnnotatedStringSpanTransformer(LocalRichTextStyle.current)
            }
        )
    }

    @InternalRendererApi
    class Result internal constructor(
        internal val single: @Composable () -> Unit,
        internal val lazy: List<@Composable () -> Unit>
    )
}
