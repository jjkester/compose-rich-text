package nl.jjkester.crt.compose

import nl.jjkester.crt.api.model.tree.Container
import nl.jjkester.crt.api.model.tree.Node

class ParsedRichText(val node: Node) {
    companion object {
        val Empty = ParsedRichText(Container(emptyList(), null))
    }
}
