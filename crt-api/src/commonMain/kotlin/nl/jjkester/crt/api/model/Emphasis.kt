package nl.jjkester.crt.api.model

/**
 * Node representing emphasized text.
 *
 * Visual emphasis is put on the [children] of this node.
 *
 * @property children Emphasized contents. A span only contains other spans.
 * @property metadata Optional metadata of this node.
 */
public class Emphasis(
    override val children: List<Node.Span>,
    override val metadata: NodeMetadata?
) : Node.Span
