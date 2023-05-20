package nl.jjkester.crt.compose.renderer

import nl.jjkester.crt.api.annotations.InternalRendererApi
import nl.jjkester.crt.api.model.Node
import nl.jjkester.crt.api.renderer.SpanTransformer
import nl.jjkester.crt.compose.text.AnnotatedStringWithExtras

/**
 * Transformer for rendering span nodes into annotated strings with extras.
 */
@OptIn(InternalRendererApi::class)
interface AnnotatedStringSpanTransformer : SpanTransformer<AnnotatedStringWithExtras> {

    /**
     * Renders the list of nodes into a single result.
     *
     * @param nodes Nodes to render.
     * @return Rendered nodes.
     */
    @InternalRendererApi
    fun transformAll(nodes: List<Node.Span>): AnnotatedStringWithExtras
}
