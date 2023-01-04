package nl.jjkester.crt.compose.renderer

import androidx.compose.runtime.Composable
import nl.jjkester.crt.api.model.Node
import nl.jjkester.crt.api.renderer.Renderer
import nl.jjkester.crt.api.renderer.SpanAsParagraphTransformer
import nl.jjkester.crt.api.renderer.transform

class ComposeRenderer(composableBlockTransformer: ComposableBlockTransformer) : Renderer<@Composable () -> Unit> {

    private val transformer = SpanAsParagraphTransformer(composableBlockTransformer)

    override fun render(node: Node): @Composable () -> Unit = transformer.transform(node)
}
