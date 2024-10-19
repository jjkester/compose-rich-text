package nl.jjkester.crt.api.model

/**
 * Node representing an inline code fragment.
 *
 * A code fragment cannot contain additional formatting, except from externally applied formatting based on the
 * [languageHint]. Code fragments therefore have no children.
 *
 * @property content Textual content of this code fragment.
 * @property languageHint Optional programming language hint for syntax highlighting.
 * @property metadata Optional metadata of this node.
 */
public class Code(
    public val content: String,
    public val languageHint: Language?,
    override val metadata: NodeMetadata?
) : Node.Span {

    /** Empty list, since a code fragment cannot have any children. */
    override val children: List<Nothing> = emptyList()
}
