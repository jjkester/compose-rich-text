package nl.jjkester.crt.api.renderer

import nl.jjkester.crt.api.model.tree.Blockquote
import nl.jjkester.crt.api.model.tree.CodeBlock
import nl.jjkester.crt.api.model.tree.Container
import nl.jjkester.crt.api.model.tree.Divider
import nl.jjkester.crt.api.model.tree.Heading
import nl.jjkester.crt.api.model.tree.ListItem
import nl.jjkester.crt.api.model.tree.Node
import nl.jjkester.crt.api.model.tree.OrderedList
import nl.jjkester.crt.api.model.tree.Paragraph
import nl.jjkester.crt.api.model.tree.UnorderedList

public interface BlockTransformer<out T> {

    public fun blockquote(node: Blockquote): T

    public fun codeBlock(node: CodeBlock): T

    public fun container(node: Container): T

    public fun divider(node: Divider): T

    public fun heading(node: Heading): T

    public fun listItem(node: ListItem): T

    public fun orderedList(node: OrderedList): T

    public fun paragraph(node: Paragraph): T

    public fun unorderedList(node: UnorderedList): T
}

public fun <T> BlockTransformer<T>.transform(node: Node.Block): T = when (node) {
    is Blockquote -> blockquote(node)
    is CodeBlock -> codeBlock(node)
    is Container -> container(node)
    is Divider -> divider(node)
    is Heading -> heading(node)
    is ListItem -> listItem(node)
    is OrderedList -> orderedList(node)
    is Paragraph -> paragraph(node)
    is UnorderedList -> unorderedList(node)
}
