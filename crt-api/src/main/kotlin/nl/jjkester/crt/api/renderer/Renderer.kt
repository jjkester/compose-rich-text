package nl.jjkester.crt.api.renderer

import nl.jjkester.crt.api.model.tree.Node

public interface Renderer<out T> {

    public fun render(node: Node): T
}
