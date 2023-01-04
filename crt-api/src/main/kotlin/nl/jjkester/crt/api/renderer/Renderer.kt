package nl.jjkester.crt.api.renderer

import nl.jjkester.crt.api.model.Node

/**
 * Renderer that transforms the internal model to a visual output.
 *
 * @param T Type of visual output.
 */
public interface Renderer<out T> {

    /**
     * Renders the [node] for visual output.
     *
     * @param node Node to render.
     */
    public fun render(node: Node): T
}
