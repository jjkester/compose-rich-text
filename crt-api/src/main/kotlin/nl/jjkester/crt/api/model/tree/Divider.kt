package nl.jjkester.crt.api.model.tree

public class Divider(
    override val metadata: NodeMetadata?
) : Node.Block() {

    override val children: List<Nothing> = emptyList()
}
