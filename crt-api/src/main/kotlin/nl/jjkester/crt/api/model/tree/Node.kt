package nl.jjkester.crt.api.model.tree

public sealed class Node private constructor() {

    public abstract val children: List<Node>

    public abstract val metadata: NodeMetadata?

    public sealed class Block : Node()

    public sealed class Span : Node() {

        abstract override val children: List<Span>
    }
}
