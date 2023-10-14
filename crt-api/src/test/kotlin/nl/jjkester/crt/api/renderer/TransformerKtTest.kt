package nl.jjkester.crt.api.renderer

import nl.jjkester.crt.api.annotations.InternalRendererApi
import nl.jjkester.crt.api.model.*
import org.junit.jupiter.api.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify

@OptIn(InternalRendererApi::class)
class TransformerKtTest {

    private val transformer = mock<Transformer<Unit>>()
    
    @Test
    fun `transform blockquote`() {
        val node = Blockquote(emptyList(), null)

        transformer.transformBlock(node)

        verify(transformer).blockquote(node)
    }

    @Test
    fun `transform code block`() {
        val node = CodeBlock("", null, null)

        transformer.transformBlock(node)

        verify(transformer).codeBlock(node)
    }

    @Test
    fun `transform container`() {
        val node = Container(emptyList(), null)

        transformer.transformBlock(node)

        verify(transformer).container(node)
    }

    @Test
    fun `transform divider`() {
        val node = Divider(null)

        transformer.transformBlock(node)

        verify(transformer).divider(node)
    }

    @Test
    fun `transform heading`() {
        val node = Heading(Heading.Level.One, emptyList(), null)

        transformer.transformBlock(node)

        verify(transformer).heading(node)
    }

    @Test
    fun `transform list item`() {
        val node = ListItem(emptyList(), null)

        transformer.transformBlock(node)

        verify(transformer).listItem(node)
    }

    @Test
    fun `transform ordered list`() {
        val node = OrderedList(emptyList(), null)

        transformer.transformBlock(node)

        verify(transformer).orderedList(node)
    }

    @Test
    fun `transform paragraph`() {
        val node = Paragraph(emptyList(), null)

        transformer.transformBlock(node)

        verify(transformer).paragraph(node)
    }

    @Test
    fun `transform unordered list`() {
        val node = UnorderedList(emptyList(), null)

        transformer.transformBlock(node)

        verify(transformer).unorderedList(node)
    }

    @Test
    fun code() {
        val node = Code("", null, null)

        transformer.transformSpan(node)

        verify(transformer).code(node)
    }

    @Test
    fun emphasis() {
        val node = Emphasis(emptyList(), null)

        transformer.transformSpan(node)

        verify(transformer).emphasis(node)
    }

    @Test
    fun link() {
        val node = Link(Link.Destination(""), emptyList(), null)

        transformer.transformSpan(node)

        verify(transformer).link(node)
    }

    @Test
    fun strongEmphasis() {
        val node = StrongEmphasis(emptyList(), null)

        transformer.transformSpan(node)

        verify(transformer).strongEmphasis(node)
    }

    @Test
    fun text() {
        val node = Text("", null)

        transformer.transformSpan(node)

        verify(transformer).text(node)
    }
}
