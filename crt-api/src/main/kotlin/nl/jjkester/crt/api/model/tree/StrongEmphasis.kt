package nl.jjkester.crt.api.model.tree

public class StrongEmphasis(
    override val children: List<Span>,
    override val metadata: NodeMetadata?
) : Node.Span()
