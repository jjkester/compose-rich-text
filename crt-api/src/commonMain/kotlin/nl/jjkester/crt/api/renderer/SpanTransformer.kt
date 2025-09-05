package nl.jjkester.crt.api.renderer

import nl.jjkester.crt.api.annotations.InternalRendererApi
import nl.jjkester.crt.api.model.Code
import nl.jjkester.crt.api.model.Emphasis
import nl.jjkester.crt.api.model.Link
import nl.jjkester.crt.api.model.Node
import nl.jjkester.crt.api.model.StrongEmphasis
import nl.jjkester.crt.api.model.Text

/**
 * Interface for easy implementation of the rendering of span nodes.
 *
 * This interface specifies abstract methods for transforming all kinds of span nodes. Transformers operate as visitors
 * on the tree structure representing the rich text to render.
 *
 * @param T Type of visual output.
 * @see BlockTransformer Equivalent for block nodes.
 * @see Transformer Combination of a block and span transformer.
 */
@InternalRendererApi
public interface SpanTransformer<out T> {

    /**
     * Renders inline code.
     *
     * @param node Node to render.
     * @return Rendered node.
     */
    @InternalRendererApi
    public fun code(node: Code): T

    /**
     * Renders emphasized text.
     *
     * @param node Node to render.
     * @return Rendered node.
     */
    @InternalRendererApi
    public fun emphasis(node: Emphasis): T

    /**
     * Renders linked text.
     *
     * @param node Node to render.
     * @return Rendered node.
     */
    @InternalRendererApi
    public fun link(node: Link): T

    /**
     * Renders strongly emphasized text.
     *
     * @param node Node to render.
     * @return Rendered node.
     */
    @InternalRendererApi
    public fun strongEmphasis(node: StrongEmphasis): T

    /**
     * Renders plain text.
     *
     * @param node Node to render.
     * @return Rendered node.
     */
    @InternalRendererApi
    public fun text(node: Text): T
}

/**
 * Transforms the span [node]. Selects a specific transformation method based on the type of the [node].
 *
 * @param node Node to transform.
 * @return Render of the [node].
 */
@InternalRendererApi
public fun <T> SpanTransformer<T>.transformSpan(node: Node.Span): T = when (node) {
    is Code -> code(node)
    is Emphasis -> emphasis(node)
    is Link -> link(node)
    is StrongEmphasis -> strongEmphasis(node)
    is Text -> text(node)
}
