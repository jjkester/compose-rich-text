package nl.jjkester.crt.markdown

import nl.jjkester.crt.api.builder.TextBuilder
import nl.jjkester.crt.api.renderer.TextRenderer
import nl.jjkester.crt.api.text.RichTextString
import org.commonmark.node.Block
import org.commonmark.node.Document
import org.commonmark.parser.Parser

public class MarkdownTextRenderer<out R : RichTextString> internal constructor(
    parser: Parser,
    private val builderFactory: () -> TextBuilder<R>
) : AbstractMarkdownRenderer<R>(parser), TextRenderer<R> {

    override fun render(input: Document): R {
        check(input.firstChild is Block && input.firstChild.next == null) {
            "Markdown document should contain exactly one block"
        }

        return builderFactory()
            .apply { MarkdownSpanVisitor(this).visit(input) }
            .build()
    }

    public companion object {
        @JvmStatic
        public fun <R : RichTextString> create(builderFactory: () -> TextBuilder<R>): TextRenderer<R> {
            val parser = Parser.builder().build()
            return MarkdownTextRenderer(parser, builderFactory)
        }

        public operator fun <R : RichTextString> invoke(builderFactory: () -> TextBuilder<R>): TextRenderer<R> =
            create(builderFactory)
    }
}
