package nl.jjkester.crt.api.model

/**
 * Node representing an ordered list.
 *
 * Ordered lists are typically rendered with numbered items.
 *
 * @property children Contents of this list. A list only contains list items.
 * @property metadata Optional metadata of this node.
 */
public class OrderedList(
    override val children: List<ListItem>,
    override val metadata: NodeMetadata?
) : Node.Block
