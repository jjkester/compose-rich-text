package nl.jjkester.crt.markdown

import nl.jjkester.crt.renderer.TextRenderer
import nl.jjkester.crt.text.RichTextString
import nl.jjkester.crt.text.TextBuilder
import org.commonmark.node.Block
import org.commonmark.node.Document
import org.commonmark.parser.Parser

class MarkdownTextRenderer<out R : RichTextString>(
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

}
