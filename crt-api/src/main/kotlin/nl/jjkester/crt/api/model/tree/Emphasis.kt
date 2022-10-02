package nl.jjkester.crt.api.model.tree

public class Emphasis(
    override val children: List<Span>,
    override val metadata: NodeMetadata?
) : Node.Span()
