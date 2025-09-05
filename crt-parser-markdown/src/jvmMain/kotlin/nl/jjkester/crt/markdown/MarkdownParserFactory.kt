package nl.jjkester.crt.markdown

import nl.jjkester.crt.api.annotations.InternalParserApi
import nl.jjkester.crt.api.parser.AbstractParserFactory
import org.commonmark.Extension
import org.commonmark.parser.Parser

/**
 * Factory for configuring and creating instances of [MarkdownParser].
 */
@OptIn(InternalParserApi::class)
public class MarkdownParserFactory : AbstractParserFactory<MarkdownParserModule, MarkdownParserResult>() {

    /**
     * Whether to fall back to plain text when a node type is not supported.
     */
    public var textFallback: Boolean = true

    override fun create(): MarkdownParser {
        return MarkdownParser(collectModules(), ::buildCommonMarkParser)
    }

    private fun collectModules(): List<MarkdownParserModule> = if (modules.isEmpty()) {
        listOf(DefaultMarkdownParserModule(textFallback = textFallback))
    } else {
        val fallbackModules = if (textFallback) {
            listOf(DefaultMarkdownParserModule(textFallback = true))
        } else {
            emptyList()
        }

        listOf(modules, fallbackModules).flatten()
    }

    private fun buildCommonMarkParser(extensions: List<Extension>) = Parser.Builder()
        .extensions(extensions)
        .build()

    public companion object {

        /**
         * Creates a new Markdown parser with the provided configuration [block].
         */
        public fun create(block: MarkdownParserFactory.() -> Unit = {}): MarkdownParser {
            return MarkdownParserFactory().apply(block).create()
        }

        /**
         * Creates a new Markdown parser with the provided configuration [block].
         *
         * @see create
         */
        public operator fun invoke(block: MarkdownParserFactory.() -> Unit = {}): MarkdownParser = create(block)
    }
}
