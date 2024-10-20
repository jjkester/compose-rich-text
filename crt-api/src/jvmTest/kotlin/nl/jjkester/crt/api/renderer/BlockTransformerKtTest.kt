package nl.jjkester.crt.api.renderer

import nl.jjkester.crt.api.annotations.InternalRendererApi
import nl.jjkester.crt.api.model.*
import org.junit.jupiter.api.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify

@OptIn(InternalRendererApi::class)
class BlockTransformerKtTest {

    private val blockTransformer = mock<BlockTransformer<Unit>>()

    @Test
    fun `transform blockquote`() {
        val node = Blockquote(emptyList(), null)

        blockTransformer.transformBlock(node)

        verify(blockTransformer).blockquote(node)
    }

    @Test
    fun `transform code block`() {
        val node = CodeBlock("", null, null)

        blockTransformer.transformBlock(node)

        verify(blockTransformer).codeBlock(node)
    }

    @Test
    fun `transform container`() {
        val node = Container(emptyList(), null)

        blockTransformer.transformBlock(node)

        verify(blockTransformer).container(node)
    }

    @Test
    fun `transform divider`() {
        val node = Divider(null)

        blockTransformer.transformBlock(node)

        verify(blockTransformer).divider(node)
    }

    @Test
    fun `transform heading`() {
        val node = Heading(Heading.Level.One, emptyList(), null)

        blockTransformer.transformBlock(node)

        verify(blockTransformer).heading(node)
    }

    @Test
    fun `transform list item`() {
        val node = ListItem(emptyList(), null)

        blockTransformer.transformBlock(node)

        verify(blockTransformer).listItem(node)
    }

    @Test
    fun `transform ordered list`() {
        val node = OrderedList(emptyList(), null)

        blockTransformer.transformBlock(node)

        verify(blockTransformer).orderedList(node)
    }

    @Test
    fun `transform paragraph`() {
        val node = Paragraph(emptyList(), null)

        blockTransformer.transformBlock(node)

        verify(blockTransformer).paragraph(node)
    }

    @Test
    fun `transform unordered list`() {
        val node = UnorderedList(emptyList(), null)

        blockTransformer.transformBlock(node)

        verify(blockTransformer).unorderedList(node)
    }
}
