package nl.jjkester.crt.api.renderer

import nl.jjkester.crt.api.model.tree.Code
import nl.jjkester.crt.api.model.tree.Emphasis
import nl.jjkester.crt.api.model.tree.Link
import nl.jjkester.crt.api.model.tree.Node
import nl.jjkester.crt.api.model.tree.Paragraph
import nl.jjkester.crt.api.model.tree.StrongEmphasis
import nl.jjkester.crt.api.model.tree.Text

public class SpanAsParagraphTransformer<B>(
    blockTransformer: BlockTransformer<B>
) : Transformer<B>, BlockTransformer<B> by blockTransformer {

    override fun code(node: Code): B = paragraph(node.wrap())

    override fun emphasis(node: Emphasis): B = paragraph(node.wrap())

    override fun link(node: Link): B = paragraph(node.wrap())

    override fun strongEmphasis(node: StrongEmphasis): B = paragraph(node.wrap())

    override fun text(node: Text): B = paragraph(node.wrap())

    private fun Node.Span.wrap(): Paragraph = Paragraph(listOf(this), null)
}
