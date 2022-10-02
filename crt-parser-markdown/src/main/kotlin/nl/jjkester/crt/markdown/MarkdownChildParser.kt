package nl.jjkester.crt.markdown

import nl.jjkester.crt.api.model.tree.ListItem
import nl.jjkester.crt.api.model.tree.Node
import nl.jjkester.crt.common.logging.logger
import org.commonmark.node.Block as CommonMarkBlock
import org.commonmark.node.ListItem as CommonMarkListItem
import org.commonmark.node.Node as CommonMarkNode

internal class MarkdownChildParser(
    private val parseFunction: (CommonMarkNode) -> Node?
) {

    fun parseBlockChildren(node: CommonMarkNode): List<Node.Block> {
        return node.children
            .requireIsInstance<CommonMarkBlock>()
            .mapNotNull { parseFunction(it) }
            .checkIsInstance<Node.Block>()
            .toList()
    }

    fun parseSpanChildren(node: CommonMarkNode): List<Node.Span> {
        return node.children
            .requireNotIsInstance<CommonMarkBlock>()
            .mapNotNull { parseFunction(it) }
            .checkIsInstance<Node.Span>()
            .toList()
    }

    fun parseListItemChildren(node: CommonMarkNode): List<ListItem> {
        return node.children
            .requireIsInstance<CommonMarkListItem>()
            .mapNotNull { parseFunction(it) }
            .checkIsInstance<ListItem>()
            .toList()
    }

    private inline fun <reified T : CommonMarkNode> Sequence<CommonMarkNode>.requireIsInstance(): Sequence<T> {
        return mapNotNull {
            if (it is T) {
                it
            } else {
                this@MarkdownChildParser.logger.warn(
                    """
                    Ignoring source node of unexpected type, required '${T::class.simpleName}'
                        Node: $it
                        Parent: ${it.parent}
                    """.trimIndent()
                )
                null
            }
        }
    }

    private inline fun <reified T : CommonMarkNode> Sequence<CommonMarkNode>.requireNotIsInstance(): Sequence<CommonMarkNode> {
        return mapNotNull {
            if (it !is T) {
                it
            } else {
                this@MarkdownChildParser.logger.warn(
                    """
                    Ignoring source node of unexpected type '${T::class.simpleName}'
                        Node: $it
                        Parent: ${it.parent}
                    """.trimIndent()
                )
                null
            }
        }
    }

    private inline fun <reified R : Node> Sequence<Node>.checkIsInstance(): Sequence<R> {
        return mapNotNull {
            if (it is R) {
                it
            } else {
                logger.error(
                    """
                    Ignoring target node of unexpected type, required '${R::class.simpleName}'
                        Node: $it
                    """.trimIndent()
                )
                null
            }
        }
    }

    private companion object {

        private val CommonMarkNode.children: Sequence<CommonMarkNode>
            get() = generateSequence(firstChild) { it.next }
    }
}
