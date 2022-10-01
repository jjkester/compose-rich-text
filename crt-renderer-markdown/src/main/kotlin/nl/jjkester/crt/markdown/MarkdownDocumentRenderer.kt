package nl.jjkester.crt.markdown

import nl.jjkester.crt.api.builder.DocumentBuilder
import nl.jjkester.crt.api.document.RichTextDocument
import nl.jjkester.crt.api.renderer.DocumentRenderer
import nl.jjkester.crt.api.text.RichTextString
import org.commonmark.node.Document
import org.commonmark.parser.IncludeSourceSpans
import org.commonmark.parser.Parser

public class MarkdownDocumentRenderer<out R : RichTextDocument, out T : RichTextString> internal constructor(
    parser: Parser,
    private val builderFactory: () -> DocumentBuilder<R, T>
) : AbstractMarkdownRenderer<R>(parser), DocumentRenderer<R> {

    override fun render(input: Document): R = builderFactory()
        .apply { MarkdownDocumentVisitor(this).visit(input) }
        .build()

    public companion object {
        @JvmStatic
        public fun <R : RichTextDocument, T : RichTextString> create(
            builderFactory: () -> DocumentBuilder<R, T>,
            includeDebugInfo: Boolean = false
        ): DocumentRenderer<R> {
            val parser = Parser.builder()
                .apply {
                    if (includeDebugInfo) includeSourceSpans(IncludeSourceSpans.BLOCKS_AND_INLINES)
                }
                .build()

            return MarkdownDocumentRenderer(parser, builderFactory)
        }

        public operator fun <R : RichTextDocument, T : RichTextString> invoke(
            builderFactory: () -> DocumentBuilder<R, T>
        ): DocumentRenderer<R> = create(builderFactory)
    }
}
