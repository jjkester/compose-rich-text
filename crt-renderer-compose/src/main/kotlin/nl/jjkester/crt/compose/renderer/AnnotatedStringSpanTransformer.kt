package nl.jjkester.crt.compose.renderer

import nl.jjkester.crt.api.annotations.InternalRendererApi
import nl.jjkester.crt.api.model.Node
import nl.jjkester.crt.api.renderer.SpanTransformer
import nl.jjkester.crt.compose.text.AnnotatedStringWithExtras

@OptIn(InternalRendererApi::class)
interface AnnotatedStringSpanTransformer : SpanTransformer<AnnotatedStringWithExtras> {

    @InternalRendererApi
    fun transformAll(nodes: List<Node.Span>): AnnotatedStringWithExtras
}
