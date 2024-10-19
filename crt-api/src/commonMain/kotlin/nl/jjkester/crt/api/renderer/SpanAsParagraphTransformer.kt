package nl.jjkester.crt.api.renderer

import nl.jjkester.crt.api.annotations.InternalRendererApi
import nl.jjkester.crt.api.model.Code
import nl.jjkester.crt.api.model.Emphasis
import nl.jjkester.crt.api.model.Link
import nl.jjkester.crt.api.model.Node
import nl.jjkester.crt.api.model.Paragraph
import nl.jjkester.crt.api.model.StrongEmphasis
import nl.jjkester.crt.api.model.Text

/**
 * Transformer implementation that delegates the transformation of block nodes to the wrapped block transformer. Span
 * nodes are transformed by wrapping them in a paragraph node and rendering the paragraph with the wrapped block
 * transformer.
 *
 * In some cases it may be beneficial to write the transformation directly to avoid the creation of unnecessary nodes.
 *
 * @param T Type of visual output.
 * @param blockTransformer Block transformer to wrap.
 */
@InternalRendererApi
public class SpanAsParagraphTransformer<T>(
    blockTransformer: BlockTransformer<T>
) : Transformer<T>, BlockTransformer<T> by blockTransformer {

    override fun code(node: Code): T = paragraph(node.wrap())

    override fun emphasis(node: Emphasis): T = paragraph(node.wrap())

    override fun link(node: Link): T = paragraph(node.wrap())

    override fun strongEmphasis(node: StrongEmphasis): T = paragraph(node.wrap())

    override fun text(node: Text): T = paragraph(node.wrap())

    private fun Node.Span.wrap(): Paragraph = Paragraph(listOf(this), null)
}
