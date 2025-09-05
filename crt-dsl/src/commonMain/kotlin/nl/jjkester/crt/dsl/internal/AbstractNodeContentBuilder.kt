package nl.jjkester.crt.dsl.internal

import nl.jjkester.crt.api.model.Node

internal abstract class AbstractNodeContentBuilder<T : Node> {

    private val _children: MutableList<T> = mutableListOf()

    val children: List<T>
        get() = _children.toList()

    protected fun add(node: T) {
        _children.add(node)
    }
}
