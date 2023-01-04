package nl.jjkester.crt.api.model

/**
 * Node representing a divider.
 *
 * A divider is typically used within a list of blocks to visually separate the content between groups of blocks. A
 * divider cannot contain content itself and is only used for formatting.
 *
 * @property metadata Optional metadata of this node.
 */
public class Divider(
    override val metadata: NodeMetadata?
) : Node.Block {

    /** Empty list, since a divider cannot have any children. */
    override val children: List<Nothing> = emptyList()
}
