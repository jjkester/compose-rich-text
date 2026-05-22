package nl.jjkester.crt.api.model

/**
 * Node representing an unordered list.
 *
 * Unordered lists are typically rendered with bulleted items.
 *
 * @property children Contents of this list. A list only contains list items.
 * @property metadata Optional metadata of this node.
 */
public class UnorderedList(
    override val children: List<ListItem>,
    override val metadata: NodeMetadata?
) : Node.Block
