package nl.jjkester.crt.markdown

import nl.jjkester.crt.api.parser.AbstractParserFactory

public class MarkdownParserFactory : AbstractParserFactory<MarkdownParserModule, MarkdownParserResult>() {

    public var textFallback: Boolean = true

    override fun create(): MarkdownParser {
        return MarkdownParser(collectModules())
    }

    private fun collectModules(): List<MarkdownParserModule> {
        if (modules.isEmpty()) {
            return listOf(DefaultMarkdownParserModule(textFallback = textFallback))
        } else {
            val fallbackModules = if (textFallback) {
                listOf(DefaultMarkdownParserModule(textFallback = true))
            } else {
                emptyList()
            }

            return sequenceOf(modules, fallbackModules).flatten().toList()
        }
    }

    public companion object : () -> MarkdownParser {

        public fun create(block: MarkdownParserFactory.() -> Unit = {}): MarkdownParser {
            return MarkdownParserFactory().apply(block).create()
        }

        public override operator fun invoke(): MarkdownParser = create()
    }
}
