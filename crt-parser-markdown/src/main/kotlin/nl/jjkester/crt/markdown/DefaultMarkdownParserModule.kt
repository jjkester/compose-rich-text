package nl.jjkester.crt.markdown

import nl.jjkester.crt.api.factory.DefaultNodeFactory
import nl.jjkester.crt.api.factory.NodeFactory
import nl.jjkester.crt.api.factory.codeBlock
import nl.jjkester.crt.api.factory.heading
import nl.jjkester.crt.api.factory.link
import nl.jjkester.crt.api.model.Node
import nl.jjkester.crt.common.logging.logger
import org.commonmark.node.BlockQuote
import org.commonmark.node.BulletList
import org.commonmark.node.Code
import org.commonmark.node.CustomBlock
import org.commonmark.node.CustomNode
import org.commonmark.node.Document
import org.commonmark.node.Emphasis
import org.commonmark.node.FencedCodeBlock
import org.commonmark.node.HardLineBreak
import org.commonmark.node.Heading
import org.commonmark.node.HtmlBlock
import org.commonmark.node.HtmlInline
import org.commonmark.node.Image
import org.commonmark.node.IndentedCodeBlock
import org.commonmark.node.Link
import org.commonmark.node.LinkReferenceDefinition
import org.commonmark.node.ListItem
import org.commonmark.node.OrderedList
import org.commonmark.node.Paragraph
import org.commonmark.node.SoftLineBreak
import org.commonmark.node.StrongEmphasis
import org.commonmark.node.Text
import org.commonmark.node.ThematicBreak
import kotlin.reflect.KClass
import org.commonmark.node.Node as CommonMarkNode

internal class DefaultMarkdownParserModule(
    private val textFallback: Boolean
) : MarkdownParserModule {

    private val nodeFactory: NodeFactory = DefaultNodeFactory

    override fun parse(value: CommonMarkNode, parseNext: (CommonMarkNode) -> Node?): Node? {
        return parse(value, MarkdownChildParser(parseNext))
    }

    private fun parse(node: CommonMarkNode, childParser: MarkdownChildParser): Node? {
        return when (node) {
            is BlockQuote -> nodeFactory.blockquote(childParser.parseBlockChildren(node))
            is BulletList -> nodeFactory.unorderedList(childParser.parseListItemChildren(node))
            is Code -> nodeFactory.code(node.literal)
            is Document -> nodeFactory.container(childParser.parseBlockChildren(node))
            is Emphasis -> nodeFactory.emphasis(childParser.parseSpanChildren(node))
            is FencedCodeBlock -> nodeFactory.codeBlock(node.literal.removeSuffix(System.lineSeparator()), node.info)
            is HardLineBreak -> nodeFactory.text(System.lineSeparator())
            is Heading -> nodeFactory.heading(node.level, childParser.parseSpanChildren(node))
            is IndentedCodeBlock -> nodeFactory.codeBlock(node.literal.removeSuffix(System.lineSeparator()))
            is Link -> nodeFactory.link(node.destination, childParser.parseSpanChildren(node))
            is ListItem -> nodeFactory.listItem(childParser.parseBlockChildren(node))
            is OrderedList -> nodeFactory.orderedList(childParser.parseListItemChildren(node))
            is Paragraph -> nodeFactory.paragraph(childParser.parseSpanChildren(node))
            is SoftLineBreak -> nodeFactory.text(" ")
            is StrongEmphasis -> nodeFactory.strongEmphasis(childParser.parseSpanChildren(node))
            is Text -> nodeFactory.text(node.literal)
            is ThematicBreak -> nodeFactory.divider()
            else -> (if (textFallback) parseFallback(node) else null) ?: nodeNotRecognized(node)
        }
    }

    private fun parseFallback(node: CommonMarkNode): Node? {
        return when (node) {
            is HtmlBlock -> nodeFactory.paragraph(listOf(nodeFactory.text(node.literal)))
            is HtmlInline -> nodeFactory.text(node.literal)
            is Image -> nodeFactory.link(node.destination, listOf(nodeFactory.text(node.title ?: node.destination)))
            else -> null
        }
    }

    private fun nodeNotRecognized(node: CommonMarkNode): Node? {
        // Log when the nodes are not extending the custom node abstract classes
        if (node::class !in ignoredNodeTypes) {
            logger.warn("Unrecognized node of type '${node::class.simpleName}'")
        }
        return null
    }

    private companion object {

        private val ignoredNodeTypes: List<KClass<out CommonMarkNode>> = listOf(
            CustomBlock::class,
            CustomNode::class,
            LinkReferenceDefinition::class,
        )
    }
}
