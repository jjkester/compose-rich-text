package nl.jjkester.crt.api.model.tree

public class Paragraph(
    override val children: List<Span>,
    override val metadata: NodeMetadata?
) : Node.Block()
