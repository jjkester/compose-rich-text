package nl.jjkester.crt.compose.renderer

import nl.jjkester.crt.api.model.Node
import nl.jjkester.crt.api.renderer.SpanTransformer
import nl.jjkester.crt.compose.text.AnnotatedStringWithExtras

interface AnnotatedStringSpanTransformer : SpanTransformer<AnnotatedStringWithExtras> {

    fun transformAll(nodes: List<Node.Span>): AnnotatedStringWithExtras
}
