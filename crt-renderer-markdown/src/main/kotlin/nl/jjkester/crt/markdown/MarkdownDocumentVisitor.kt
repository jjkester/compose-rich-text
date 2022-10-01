package nl.jjkester.crt.markdown

import nl.jjkester.crt.api.builder.DocumentBuilder
import nl.jjkester.crt.api.builder.TextBuilder
import org.commonmark.node.*

internal class MarkdownDocumentVisitor(
    private val builder: DocumentBuilder<*, *>
) : AbstractMarkdownVisitor() {
    override fun node(node: Node) {
        when (node) {
            is BlockQuote -> blockquote(node)
            is BulletList -> bulletList(node)
            is Document -> document(node)
            is FencedCodeBlock -> fencedCode(node)
            is Heading -> heading(node)
            is HtmlBlock -> html(node)
            is IndentedCodeBlock -> indentedCode(node)
            is LinkReferenceDefinition -> suppressed(node)
            is ListItem -> listItem(node)
            is OrderedList -> orderedList(node)
            is Paragraph -> paragraph(node)
            is ThematicBreak -> thematicBreak(node)
            else -> unknown(node)
        }
    }

    private fun blockquote(node: BlockQuote) {
        builder.blockquote {
            children(node)
        }
    }

    private fun bulletList(node: BulletList) {
        builder.unorderedList {
            children(node)
        }
    }

    private fun document(node: Document) {
        children(node)
    }

    private fun fencedCode(node: FencedCodeBlock) {
        builder.code(node.literal.trimEnd(), node.info)
    }

    private fun heading(node: Heading) {
        builder.heading(node.level) {
            text(node)
        }
    }

    private fun html(node: HtmlBlock) {
        // HTML is not rendered at the moment, so we present it as text
        builder.paragraph {
            append(node.literal)
        }
    }

    private fun indentedCode(node: IndentedCodeBlock) {
        builder.code(node.literal.trimEnd())
    }

    private fun listItem(node: ListItem) {
        builder.listItem {
            children(node)
        }
    }

    private fun orderedList(node: OrderedList) {
        builder.orderedList {
            children(node)
        }
    }

    private fun paragraph(node: Paragraph) {
        builder.paragraph {
            text(node)
        }
    }

    private fun thematicBreak(node: ThematicBreak) {
        builder.separator()
    }

    private fun TextBuilder<*>.text(node: Node) {
        val visitor = MarkdownSpanVisitor(this)
        childrenOf(node).forEach(visitor::visit)
    }
}
