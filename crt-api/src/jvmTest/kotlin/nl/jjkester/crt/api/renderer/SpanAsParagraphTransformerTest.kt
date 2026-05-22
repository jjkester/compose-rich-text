package nl.jjkester.crt.api.renderer

import assertk.all
import assertk.assertThat
import assertk.assertions.containsExactly
import assertk.assertions.isNull
import nl.jjkester.crt.api.annotations.InternalRendererApi
import nl.jjkester.crt.api.model.*
import org.junit.jupiter.api.Test
import org.mockito.kotlin.argumentCaptor
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify

@OptIn(InternalRendererApi::class)
class SpanAsParagraphTransformerTest {

    private val blockTransformer = mock<BlockTransformer<Unit>>()

    private val systemUnderTest = SpanAsParagraphTransformer(blockTransformer)

    @Test
    fun code() {
        val node = Code("", null, null)

        systemUnderTest.code(node)

        argumentCaptor<Paragraph> {
            verify(blockTransformer).paragraph(capture())

            assertThat(firstValue).all {
                transform { it.children }.containsExactly(node)
                transform { it.metadata }.isNull()
            }
        }
    }

    @Test
    fun emphasis() {
        val node = Emphasis(emptyList(), null)

        systemUnderTest.emphasis(node)

        argumentCaptor<Paragraph> {
            verify(blockTransformer).paragraph(capture())

            assertThat(firstValue).all {
                transform { it.children }.containsExactly(node)
                transform { it.metadata }.isNull()
            }
        }
    }

    @Test
    fun link() {
        val node = Link(Link.Destination(""), emptyList(), null)

        systemUnderTest.link(node)

        argumentCaptor<Paragraph> {
            verify(blockTransformer).paragraph(capture())

            assertThat(firstValue).all {
                transform { it.children }.containsExactly(node)
                transform { it.metadata }.isNull()
            }
        }
    }

    @Test
    fun strongEmphasis() {
        val node = StrongEmphasis(emptyList(), null)

        systemUnderTest.strongEmphasis(node)

        argumentCaptor<Paragraph> {
            verify(blockTransformer).paragraph(capture())

            assertThat(firstValue).all {
                transform { it.children }.containsExactly(node)
                transform { it.metadata }.isNull()
            }
        }
    }

    @Test
    fun text() {
        val node = Text("", null)

        systemUnderTest.text(node)

        argumentCaptor<Paragraph> {
            verify(blockTransformer).paragraph(capture())

            assertThat(firstValue).all {
                transform { it.children }.containsExactly(node)
                transform { it.metadata }.isNull()
            }
        }
    }
}
