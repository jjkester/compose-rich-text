package nl.jjkester.crt.api.model

/**
 * Node representing strongly emphasized text.
 *
 * Strong visual emphasis is put on the [children] of this node.
 *
 * @property children Strongly emphasized contents. A span only contains other spans.
 * @property metadata Optional metadata of this node.
 */
public class StrongEmphasis(
    override val children: List<Node.Span>,
    override val metadata: NodeMetadata?
) : Node.Span
