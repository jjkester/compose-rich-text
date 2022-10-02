package nl.jjkester.crt.api.model.tree

import nl.jjkester.crt.api.model.Language

public class CodeBlock(
    public val content: String,
    public val languageHint: Language?,
    override val metadata: NodeMetadata?
) : Node.Block() {

    override val children: List<Nothing> = emptyList()
}
