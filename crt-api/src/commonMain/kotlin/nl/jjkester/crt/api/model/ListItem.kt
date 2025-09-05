package nl.jjkester.crt.api.model

/**
 * Node representing an item in a list.
 *
 * @property children Contents of this list item. A list item only contains other blocks.
 * @property metadata Optional metadata of this node.
 */
public class ListItem(
    override val children: List<Node.Block>,
    override val metadata: NodeMetadata?
) : Node.Block
