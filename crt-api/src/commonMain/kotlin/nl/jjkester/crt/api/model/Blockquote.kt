package nl.jjkester.crt.api.model

/**
 * Node representing a blockquote.
 *
 * @property children Contents of this blockquote. A blockquote only contains other blocks.
 * @property metadata Optional metadata of this node.
 */
public class Blockquote(
    override val children: List<Node.Block>,
    override val metadata: NodeMetadata?
) : Node.Block
