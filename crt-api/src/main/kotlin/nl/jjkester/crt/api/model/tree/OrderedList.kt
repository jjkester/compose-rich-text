package nl.jjkester.crt.api.model.tree

public class OrderedList(
    override val children: List<ListItem>,
    override val metadata: NodeMetadata?
) : Node.Block()
