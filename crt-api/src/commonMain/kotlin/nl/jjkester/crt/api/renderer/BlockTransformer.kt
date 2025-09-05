package nl.jjkester.crt.api.renderer

import nl.jjkester.crt.api.annotations.InternalRendererApi
import nl.jjkester.crt.api.model.Blockquote
import nl.jjkester.crt.api.model.CodeBlock
import nl.jjkester.crt.api.model.Container
import nl.jjkester.crt.api.model.Divider
import nl.jjkester.crt.api.model.Heading
import nl.jjkester.crt.api.model.ListItem
import nl.jjkester.crt.api.model.Node
import nl.jjkester.crt.api.model.OrderedList
import nl.jjkester.crt.api.model.Paragraph
import nl.jjkester.crt.api.model.UnorderedList

/**
 * Interface for easy implementation of the rendering of block nodes.
 *
 * This interface specifies abstract methods for transforming all kinds of block nodes. Transformers operate as visitors
 * on the tree structure representing the rich text to render.
 *
 * @param T Type of visual output.
 * @see SpanTransformer Equivalent for span nodes.
 * @see Transformer Combination of a block and span transformer.
 */
@InternalRendererApi
public interface BlockTransformer<out T> {

    /**
     * Renders a blockquote.
     *
     * @param node Node to render.
     * @return Rendered node.
     */
    @InternalRendererApi
    public fun blockquote(node: Blockquote): T

    /**
     * Renders a code block.
     *
     * @param node Node to render.
     * @return Rendered node.
     */
    @InternalRendererApi
    public fun codeBlock(node: CodeBlock): T

    /**
     * Renders a container.
     *
     * @param node Node to render.
     * @return Rendered node.
     */
    @InternalRendererApi
    public fun container(node: Container): T

    /**
     * Renders a divider.
     *
     * @param node Node to render.
     * @return Rendered node.
     */
    @InternalRendererApi
    public fun divider(node: Divider): T

    /**
     * Renders a heading.
     *
     * @param node Node to render.
     * @return Rendered node.
     */
    @InternalRendererApi
    public fun heading(node: Heading): T

    /**
     * Renders a list item.
     *
     * @param node Node to render.
     * @return Rendered node.
     */
    @InternalRendererApi
    public fun listItem(node: ListItem): T

    /**
     * Renders an ordered list.
     *
     * @param node Node to render.
     * @return Rendered node.
     */
    @InternalRendererApi
    public fun orderedList(node: OrderedList): T

    /**
     * Renders a paragraph.
     *
     * @param node Node to render.
     * @return Rendered node.
     */
    @InternalRendererApi
    public fun paragraph(node: Paragraph): T

    /**
     * Renders an unordered list.
     *
     * @param node Node to render.
     * @return Rendered node.
     */
    @InternalRendererApi
    public fun unorderedList(node: UnorderedList): T
}

/**
 * Transforms the block [node]. Selects a specific transformation method based on the type of the [node].
 *
 * @param node Node to transform.
 * @return Render of the [node].
 */
@InternalRendererApi
public fun <T> BlockTransformer<T>.transformBlock(node: Node.Block): T = when (node) {
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
