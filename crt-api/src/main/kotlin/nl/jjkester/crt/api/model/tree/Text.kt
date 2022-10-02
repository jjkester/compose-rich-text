package nl.jjkester.crt.api.model.tree

public class Text(
    public val text: String,
    override val metadata: NodeMetadata?
) : Node.Span() {

    override val children: List<Nothing> = emptyList()
}
