package nl.jjkester.crt.api.model

/**
 * Node representing a paragraph of text.
 *
 * A paragraph is the fundamental node type to represent formatted text.
 *
 * @property children Contents of this paragraph. A paragraph can only contain spans.
 * @property metadata Optional metadata of this node.
 */
public class Paragraph(
    override val children: List<Node.Span>,
    override val metadata: NodeMetadata?
) : Node.Block
