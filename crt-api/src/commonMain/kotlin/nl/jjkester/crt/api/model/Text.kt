package nl.jjkester.crt.api.model

/**
 * Node representing text.
 *
 * A text node is the fundamental node to type to represent a sequence of characters regardless of formatting.
 *
 * @property text Textual content.
 * @property metadata Optional metadata of this node.
 */
public class Text(
    public val text: String,
    override val metadata: NodeMetadata?
) : Node.Span {

    /** Empty list, since text cannot have any children. */
    override val children: List<Nothing> = emptyList()
}
