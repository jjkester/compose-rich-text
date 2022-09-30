package nl.jjkester.crt.markdown

import nl.jjkester.crt.text.TextBuilder
import org.commonmark.node.*

class MarkdownSpanVisitor(
    private val builder: TextBuilder<*>
) : AbstractMarkdownVisitor() {

    override fun node(node: Node) {
        when (node) {
            is Code -> code(node)
            is Document -> suppressed(node)
            is Emphasis -> emphasis(node)
            is HardLineBreak -> hardLineBreak(node)
            is Heading -> suppressed(node)
            is Link -> link(node)
            is Paragraph -> suppressed(node)
            is SoftLineBreak -> softLineBreak(node)
            is StrongEmphasis -> strongEmphasis(node)
            is Text -> text(node)
            is LinkReferenceDefinition -> ignored(node)
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