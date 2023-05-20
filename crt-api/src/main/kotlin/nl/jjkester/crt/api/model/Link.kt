package nl.jjkester.crt.api.model

/**
 * Node representing a clickable link.
 *
 * Creates a clickable area around the [children] of this node, opening the [destination] when clicked.
 *
 * @property destination Destination of the link when clicked.
 * @property children Clickable contents. A span only contains other spans.
 * @property metadata Optional metadata of this node.
 */
public class Link(
    public val destination: Destination,
    override val children: List<Node.Span>,
    override val metadata: NodeMetadata?
) : Node.Span {

    /**
     * Destination of a link.
     */
    @JvmInline
    public value class Destination(public val value: String)
}
