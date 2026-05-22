package nl.jjkester.crt.api.renderer

import nl.jjkester.crt.api.annotations.InternalRendererApi
import nl.jjkester.crt.api.model.*
import org.junit.jupiter.api.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify

@OptIn(InternalRendererApi::class)
class SpanTransformerKtTest {

    private val spanTransformer = mock<SpanTransformer<Unit>>()

    @Test
    fun code() {
        val node = Code("", null, null)

        spanTransformer.transformSpan(node)

        verify(spanTransformer).code(node)
    }

    @Test
    fun emphasis() {
        val node = Emphasis(emptyList(), null)

        spanTransformer.transformSpan(node)

        verify(spanTransformer).emphasis(node)
    }

    @Test
    fun link() {
        val node = Link(Link.Destination(""), emptyList(), null)

        spanTransformer.transformSpan(node)

        verify(spanTransformer).link(node)
    }

    @Test
    fun strongEmphasis() {
        val node = StrongEmphasis(emptyList(), null)

        spanTransformer.transformSpan(node)

        verify(spanTransformer).strongEmphasis(node)
    }

    @Test
    fun text() {
        val node = Text("", null)

        spanTransformer.transformSpan(node)

        verify(spanTransformer).text(node)
    }
}
