package nl.jjkester.crt.markdown

import nl.jjkester.crt.document.DocumentBuilder
import nl.jjkester.crt.document.RichTextDocument
import nl.jjkester.crt.renderer.DocumentRenderer
import nl.jjkester.crt.text.RichTextString
import org.commonmark.node.Document
import org.commonmark.parser.Parser

class MarkdownDocumentRenderer<out R : RichTextDocument, out T : RichTextString>(
    parser: Parser,
    private val builderFactory: () -> DocumentBuilder<R, T>
) : AbstractMarkdownRenderer<R>(parser), DocumentRenderer<R> {

    override fun render(input: Document): R = builderFactory()
        .apply { MarkdownDocumentVisitor(this).visit(input) }
        .build()
}
