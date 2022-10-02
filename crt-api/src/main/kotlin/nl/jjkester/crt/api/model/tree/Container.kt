package nl.jjkester.crt.api.model.tree

public class Container(
    override val children: List<Block>,
    override val metadata: NodeMetadata?
) : Node.Block()
