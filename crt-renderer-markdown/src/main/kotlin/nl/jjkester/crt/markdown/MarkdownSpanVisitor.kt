package nl.jjkester.crt.markdown

import nl.jjkester.crt.api.builder.TextBuilder
import org.commonmark.node.Code
import org.commonmark.node.Document
import org.commonmark.node.Emphasis
import org.commonmark.node.HardLineBreak
import org.commonmark.node.Heading
import org.commonmark.node.HtmlInline
import org.commonmark.node.Image
import org.commonmark.node.Link
import org.commonmark.node.Node
import org.commonmark.node.Paragraph
import org.commonmark.node.SoftLineBreak
import org.commonmark.node.StrongEmphasis
import org.commonmark.node.Text

internal class MarkdownSpanVisitor(
    private val builder: TextBuilder<*>
) : AbstractMarkdownVisitor() {

    override fun node(node: Node) {
        when (node) {
            is Code -> code(node)
            is Document -> suppressed(node)
            is Emphasis -> emphasis(node)
            is HardLineBreak -> hardLineBreak(node)
            is Heading -> suppressed(node)
            is HtmlInline -> html(node)
            is Image -> image(node)
            is Link -> link(node)
            is Paragraph -> suppressed(node)
            is SoftLineBreak -> softLineBreak(node)
            is StrongEmphasis -> strongEmphasis(node)
            is Text -> text(node)
            else -> unknown(node)
        }
    }

    private fun code(node: Code) {
        builder.code {
            append(node.literal)
            children(node)
        }
    }

    private fun emphasis(node: Emphasis) {
        builder.emphasis {
            children(node)
        }
    }

    private fun hardLineBreak(node: HardLineBreak) {
        builder.append(System.lineSeparator())
    }

    private fun html(node: HtmlInline) {
        // HTML is not rendered at the moment, so we present it as text
        builder.append(node.literal)
    }

    private fun image(node: Image) {
        // Images are not rendered at the moment, so we present it as link
        builder.link(node.destination) {
            if (node.firstChild == null) {
                append(node.destination)
            } else {
                children(node)
            }
        }
    }

    private fun link(node: Link) {
        builder.link(node.destination) {
            children(node)
        }
    }

    private fun softLineBreak(node: SoftLineBreak) {
        builder.append(" ")
    }

    private fun strongEmphasis(node: StrongEmphasis) {
        builder.strongEmphasis {
            children(node)
        }
    }

    private fun text(node: Text) {
        builder.append(node.literal)
        children(node)
    }
}
