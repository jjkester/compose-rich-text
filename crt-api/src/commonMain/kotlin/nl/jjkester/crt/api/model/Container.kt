package nl.jjkester.crt.api.model

/**
 * Node representing a block containing other blocks.
 *
 * A container groups a list of block nodes as a single block node. Typically, this type is used as root of a tree.
 *
 * @property children Contents of this container. A container only contains other blocks.
 * @property metadata Optional metadata of this node.
 */
public class Container(
    override val children: List<Node.Block>,
    override val metadata: NodeMetadata?
) : Node.Block
