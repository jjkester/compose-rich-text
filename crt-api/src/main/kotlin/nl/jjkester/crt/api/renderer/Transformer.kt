package nl.jjkester.crt.api.renderer

import nl.jjkester.crt.api.model.Blockquote
import nl.jjkester.crt.api.model.Code
import nl.jjkester.crt.api.model.CodeBlock
import nl.jjkester.crt.api.model.Container
import nl.jjkester.crt.api.model.Divider
import nl.jjkester.crt.api.model.Emphasis
import nl.jjkester.crt.api.model.Heading
import nl.jjkester.crt.api.model.Link
import nl.jjkester.crt.api.model.ListItem
import nl.jjkester.crt.api.model.Node
import nl.jjkester.crt.api.model.OrderedList
import nl.jjkester.crt.api.model.Paragraph
import nl.jjkester.crt.api.model.StrongEmphasis
import nl.jjkester.crt.api.model.Text
import nl.jjkester.crt.api.model.UnorderedList

/**
 * Interface for easy implementation of the rendering of nodes.
 *
 * This interface specifies abstract methods for transforming all kinds of nodes. Transformers operate as visitors on
 * the tree structure representing the rich text to render.
 *
 * This interface can be used when the type of visual output is equal for both block and span nodes.
 *
 * @param T Type of visual output.
 * @see BlockTransformer Equivalent for only block nodes.
 * @see SpanTransformer Equivalent for only span nodes.
 */
public interface Transformer<out T> : BlockTransformer<T>, SpanTransformer<T>

/**
 * Transforms the internal node to the output type of this transformer.
 *
 * This function calls the appropriate function on the transformer for the type of the provided node.
 *
 * @param T Type of visual output.
 * @param node Node to transform.
 * @return Result of transforming the provided node.
 */
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
