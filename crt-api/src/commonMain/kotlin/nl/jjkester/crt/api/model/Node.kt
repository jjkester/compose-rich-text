package nl.jjkester.crt.api.model

/**
 * A node in the rich text tree.
 *
 * Each node represents formatting and/or content in rich text. Nodes can be either [Block]s or [Span]s.
 */
public sealed interface Node {

    /** Child nodes of this node. */
    public val children: List<Node>

    /** Optional metadata about this node. */
    public val metadata: NodeMetadata?

    /**
     * A block node. Block nodes are structurally relevant and contain text.
     */
    public sealed interface Block : Node

    /**
     * A span node. Span nodes do not imply any structure and operate within text.
     */
    public sealed interface Span : Node {

        /**
         * Child nodes of this node.
         *
         * Span nodes can only contain other span nodes.
         */
        abstract override val children: List<Span>
    }
}
