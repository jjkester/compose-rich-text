package nl.jjkester.crt.api.renderer

import nl.jjkester.crt.api.model.tree.Code
import nl.jjkester.crt.api.model.tree.Emphasis
import nl.jjkester.crt.api.model.tree.Link
import nl.jjkester.crt.api.model.tree.Node
import nl.jjkester.crt.api.model.tree.StrongEmphasis
import nl.jjkester.crt.api.model.tree.Text

public interface SpanTransformer<out T> {

    public fun code(node: Code): T

    public fun emphasis(node: Emphasis): T

    public fun link(node: Link): T

    public fun strongEmphasis(node: StrongEmphasis): T

    public fun text(node: Text): T
}

public fun <T> SpanTransformer<T>.transform(node: Node.Span): T = when (node) {
    is Code -> code(node)
    is Emphasis -> emphasis(node)
    is Link -> link(node)
    is StrongEmphasis -> strongEmphasis(node)
    is Text -> text(node)
}
