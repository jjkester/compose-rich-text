package nl.jjkester.crt.api.model.tree

public class Link(
    public val destination: Destination,
    override val children: List<Span>,
    override val metadata: NodeMetadata?
) : Node.Span() {

    @JvmInline
    public value class Destination(public val value: String)
}
