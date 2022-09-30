package nl.jjkester.crt.markdown

import nl.jjkester.crt.renderer.Renderer
import org.commonmark.node.Document
import org.commonmark.parser.Parser

abstract class AbstractMarkdownRenderer<out R : Any>(
    private val parser: Parser
) : Renderer<R> {

    final override fun render(input: String): R {
        val document = checkNotNull(parser.parse(input) as? Document) {
            "Parsing Markdown did not yield an expected result"
        }

        return render(document)
    }

    protected abstract fun render(input: Document): R

}