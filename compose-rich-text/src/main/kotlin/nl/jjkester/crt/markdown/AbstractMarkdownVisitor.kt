package nl.jjkester.crt.markdown

import android.util.Log
import org.commonmark.node.Node

abstract class AbstractMarkdownVisitor {

    protected val logTag: String = javaClass.name

    fun visit(node: Node) {
        node(node)
    }

    protected abstract fun node(node: Node)

    protected fun suppressed(node: Node) {
        Log.w(logTag, "Suppressing partially rendered node ${nodeDetails(node)}")
        children(node)
    }

    protected fun ignored(node: Node) {
        Log.w(logTag, "Ignoring unexpected node ${nodeDetails(node)}")
    }

    protected fun unknown(node: Node) {
        val nodeDetails = nodeDetails(node)
        Log.e(logTag, "Failed to render unknown node $nodeDetails")
        error("Cannot render $nodeDetails")
    }

    private fun nodeDetails(node: Node): String {
        val startPosition = node.sourceSpans.firstOrNull()?.let {
            "line ${it.lineIndex}, column ${it.columnIndex}"
        } ?: "unknown"
        return "${node.javaClass.simpleName} at $startPosition"
    }

    protected fun childrenOf(node: Node): Sequence<Node> {
        return generateSequence(node.firstChild) { it.next }
    }

    protected fun children(node: Node) {
        childrenOf(node).forEach(::node)
    }
}