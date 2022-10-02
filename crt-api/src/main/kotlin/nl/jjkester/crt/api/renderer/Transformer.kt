package nl.jjkester.crt.api.renderer

import nl.jjkester.crt.api.model.tree.Blockquote
import nl.jjkester.crt.api.model.tree.Code
import nl.jjkester.crt.api.model.tree.CodeBlock
import nl.jjkester.crt.api.model.tree.Container
import nl.jjkester.crt.api.model.tree.Divider
import nl.jjkester.crt.api.model.tree.Emphasis
import nl.jjkester.crt.api.model.tree.Heading
import nl.jjkester.crt.api.model.tree.Link
import nl.jjkester.crt.api.model.tree.ListItem
import nl.jjkester.crt.api.model.tree.Node
import nl.jjkester.crt.api.model.tree.OrderedList
import nl.jjkester.crt.api.model.tree.Paragraph
import nl.jjkester.crt.api.model.tree.StrongEmphasis
import nl.jjkester.crt.api.model.tree.Text
import nl.jjkester.crt.api.model.tree.UnorderedList

public interface Transformer<out T> : BlockTransformer<T>, SpanTransformer<T>

public fun <T> Transformer<T>.transform(node: Node): T = when (node) {
    is Blockquote -> blockquote(node)
    is CodeBlock -> codeBlock(node)
    is Container -> container(node)
    is Divider -> divider(node)
    is Heading -> heading(node)
    is ListItem -> listItem(node)
    is OrderedList -> orderedList(node)
    is Paragraph -> paragraph(node)
    is UnorderedList -> unorderedList(node)
    is Code -> code(node)
    is Emphasis -> emphasis(node)
    is Link -> link(node)
    is StrongEmphasis -> strongEmphasis(node)
    is Text -> text(node)
}
