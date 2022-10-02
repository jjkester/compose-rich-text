package nl.jjkester.crt.api.model.tree

public class Blockquote(
    override val children: List<Block>,
    override val metadata: NodeMetadata?
) : Node.Block()
