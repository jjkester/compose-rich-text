package nl.jjkester.crt.markdown

import assertk.all
import assertk.assertThat
import assertk.assertions.*
import nl.jjkester.crt.api.annotations.InternalParserApi
import org.commonmark.node.Node
import org.junit.jupiter.api.Test

class MarkdownParserFactoryTest {

    @Test
    fun `initial values`() {
        assertThat(MarkdownParserFactory()).all {
            transform { it.modules }.isEmpty()
            transform { it.collectDebugInfo }.isFalse()
            transform { it.textFallback }.isTrue()
        }
    }

    @Test
    fun `default settings`() {
        assertThat(MarkdownParserFactory().create()).all {
            transform { it.parserModules }
                .single()
                .isInstanceOf<DefaultMarkdownParserModule>()
                .transform { it.textFallback }.isTrue()
        }
    }

    @Test
    fun `without text fallback`() {
        assertThat(MarkdownParserFactory { textFallback = false }).all {
            transform { it.parserModules }
                .single()
                .isInstanceOf<DefaultMarkdownParserModule>()
                .transform { it.textFallback }.isFalse()
        }
    }

    @Test
    fun `custom module`() {
        val module = object : MarkdownParserModule {
            @InternalParserApi
            override fun parse(
                value: Node,
                parseNext: (Node) -> nl.jjkester.crt.api.model.Node?
            ): nl.jjkester.crt.api.model.Node? = null
        }

        val result = MarkdownParserFactory {
            modules = listOf(module)
        }

        assertThat(result).all {
            transform { it.parserModules }
                .all {
                    hasSize(2)
                    first().isSameAs(module)
                    transform { it.last() }
                        .isInstanceOf<DefaultMarkdownParserModule>()
                        .transform { it.textFallback }.isTrue()
                }
        }
    }

    @Test
    fun `custom module without text fallback`() {
        val module = object : MarkdownParserModule {
            @InternalParserApi
            override fun parse(
                value: Node,
                parseNext: (Node) -> nl.jjkester.crt.api.model.Node?
            ): nl.jjkester.crt.api.model.Node? = null
        }

        val result = MarkdownParserFactory {
            modules = listOf(module)
            textFallback = false
        }

        assertThat(result).all {
            transform { it.parserModules }.single().isSameAs(module)
        }
    }
}
