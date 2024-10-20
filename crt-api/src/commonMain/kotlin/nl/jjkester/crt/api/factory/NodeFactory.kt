package nl.jjkester.crt.api.factory

import nl.jjkester.crt.api.annotations.InternalFactoryApi
import nl.jjkester.crt.api.model.Blockquote
import nl.jjkester.crt.api.model.Code
import nl.jjkester.crt.api.model.CodeBlock
import nl.jjkester.crt.api.model.Container
import nl.jjkester.crt.api.model.Divider
import nl.jjkester.crt.api.model.Emphasis
import nl.jjkester.crt.api.model.Heading
import nl.jjkester.crt.api.model.Language
import nl.jjkester.crt.api.model.Link
import nl.jjkester.crt.api.model.ListItem
import nl.jjkester.crt.api.model.Node
import nl.jjkester.crt.api.model.NodeMetadata
import nl.jjkester.crt.api.model.OrderedList
import nl.jjkester.crt.api.model.Paragraph
import nl.jjkester.crt.api.model.StrongEmphasis
import nl.jjkester.crt.api.model.Text
import nl.jjkester.crt.api.model.UnorderedList

/**
 * Factory for creating internal model nodes.
 *
 * Implementations of this interface can alter the creation of nodes.
 *
 * @see DefaultNodeFactory Default implementation proxying node constructors.
 */
public interface NodeFactory {

    /**
     * Creates a node representing a blockquote.
     *
     * @param children Contents of this blockquote. A blockquote only contains other blocks.
     * @param metadata Optional metadata of this node.
     * @return Created blockquote node.
     * @see Blockquote
     */
    @InternalFactoryApi
    public fun blockquote(
        children: List<Node.Block> = emptyList(),
        metadata: NodeMetadata? = null
    ): Blockquote

    /**
     * Creates a node representing an inline code fragment.
     *
     * @param content Textual content of this code fragment.
     * @param languageHint Optional programming language hint for syntax highlighting.
     * @param metadata Optional metadata of this node.
     * @return Created inline code fragment node.
     * @see Code
     */
    @InternalFactoryApi
    public fun code(
        content: String,
        languageHint: Language? = null,
        metadata: NodeMetadata? = null
    ): Code

    /**
     * Creates a node representing a code fragment block.
     *
     * @param content Textual content of this code fragment.
     * @param languageHint Optional programming language hint for syntax highlighting.
     * @param metadata Optional metadata of this node.
     * @return Created code fragment block node.
     * @see CodeBlock
     */
    @InternalFactoryApi
    public fun codeBlock(
        content: String,
        languageHint: Language? = null,
        metadata: NodeMetadata? = null
    ): CodeBlock

    /**
     * Creates a node representing a block containing other blocks.
     *
     * @param children Contents of this container.
     * @param metadata Optional metadata of this node.
     * @return Created block node.
     * @see Container
     */
    @InternalFactoryApi
    public fun container(
        children: List<Node.Block> = emptyList(),
        metadata: NodeMetadata? = null
    ): Container

    /**
     * Creates a node representing a divider.
     *
     * @param metadata Optional metadata of this node.
     * @return Created divider node.
     * @see Divider
     */
    @InternalFactoryApi
    public fun divider(
        metadata: NodeMetadata? = null
    ): Divider

    /**
     * Creates a node representing emphasized text.
     *
     * @param children Emphasized contents.
     * @param metadata Optional metadata of this node.
     * @return Created emphasized text node.
     * @see Emphasis
     */
    @InternalFactoryApi
    public fun emphasis(
        children: List<Node.Span> = emptyList(),
        metadata: NodeMetadata? = null
    ): Emphasis

    /**
     * Creates a node representing a heading block.
     *
     * @param level Importance level of this heading.
     * @param children Contents of this heading.
     * @param metadata Optional metadata of this node.
     * @return Created heading block node.
     * @see Heading
     */
    @InternalFactoryApi
    public fun heading(
        level: Heading.Level,
        children: List<Node.Span> = emptyList(),
        metadata: NodeMetadata? = null
    ): Heading

    /**
     * Creates a node representing a clickable link.
     *
     * @param destination Destination of the link when clicked.
     * @param children Clickable contents.
     * @param metadata Optional metadata of this node.
     * @return Created clickable link node.
     * @see Link
     */
    @InternalFactoryApi
    public fun link(
        destination: Link.Destination,
        children: List<Node.Span> = emptyList(),
        metadata: NodeMetadata? = null
    ): Link

    /**
     * Creates a node representing an item in a list.
     *
     * @param children Contents of this list item.
     * @param metadata Optional metadata of this node.
     * @return Created list item node.
     * @see ListItem
     */
    @InternalFactoryApi
    public fun listItem(
        children: List<Node.Block> = emptyList(),
        metadata: NodeMetadata? = null
    ): ListItem

    /**
     * Creates a node representing an ordered list.
     *
     * @param children Contents of this list.
     * @param metadata Optional metadata of this node.
     * @return Created ordered list node.
     * @see OrderedList
     */
    @InternalFactoryApi
    public fun orderedList(
        children: List<ListItem> = emptyList(),
        metadata: NodeMetadata? = null
    ): OrderedList

    /**
     * Creates a node representing a paragraph of text.
     *
     * @param children Contents of this paragraph.
     * @param metadata Optional metadata of this node.
     * @return Created paragraph of text node.
     * @see Paragraph
     */
    @InternalFactoryApi
    public fun paragraph(
        children: List<Node.Span> = emptyList(),
        metadata: NodeMetadata? = null
    ): Paragraph

    /**
     * Creates a node representing strongly emphasized text.
     *
     * @param children Strongly emphasized contents.
     * @param metadata Optional metadata of this node.
     * @return Created strongly emphasized text node.
     * @see StrongEmphasis
     */
    @InternalFactoryApi
    public fun strongEmphasis(
        children: List<Node.Span> = emptyList(),
        metadata: NodeMetadata? = null
    ): StrongEmphasis

    /**
     * Creates a node representing text.
     *
     * @param content Textual content.
     * @param metadata Optional metadata of this node.
     * @return Created text node.
     * @see Text
     */
    @InternalFactoryApi
    public fun text(
        content: String,
        metadata: NodeMetadata? = null
    ): Text

    /**
     * Creates a node representing an unordered list.
     *
     * @param children Contents of this list.
     * @param metadata Optional metadata of this node.
     * @return Created unordered list node.
     * @see UnorderedList
     */
    @InternalFactoryApi
    public fun unorderedList(
        children: List<ListItem> = emptyList(),
        metadata: NodeMetadata? = null
    ): UnorderedList
}

/**
 * Creates a node representing an inline code fragment.
 *
 * @param content Textual content of this code fragment.
 * @param languageHint Optional programming language hint for syntax highlighting.
 * @param metadata Optional metadata of this node.
 * @return Created inline code fragment node.
 * @see NodeFactory.code
 * @see Code
 */
@InternalFactoryApi
public fun NodeFactory.code(
    content: String,
    languageHint: String? = null,
    metadata: NodeMetadata? = null
): Code = code(content, languageHint?.let(::Language), metadata)

/**
 * Creates a node representing a code fragment block.
 *
 * @param content Textual content of this code fragment.
 * @param languageHint Optional programming language hint for syntax highlighting.
 * @param metadata Optional metadata of this node.
 * @return Created code fragment block node.
 * @see NodeFactory.codeBlock
 * @see CodeBlock
 */
@InternalFactoryApi
public fun NodeFactory.codeBlock(
    content: String,
    languageHint: String? = null,
    metadata: NodeMetadata? = null
): CodeBlock = codeBlock(content, languageHint?.let(::Language), metadata)

/**
 * Creates a node representing a heading block.
 *
 * @param level Importance level of this heading.
 * @param children Contents of this heading.
 * @param metadata Optional metadata of this node.
 * @return Created heading block node.
 * @see NodeFactory.heading
 * @see Heading
 */
@InternalFactoryApi
public fun NodeFactory.heading(
    level: Int,
    children: List<Node.Span> = emptyList(),
    metadata: NodeMetadata? = null
): Heading = heading(Heading.Level.fromIntOrNull(level) ?: Heading.Level.Six, children, metadata)

/**
 * Creates a node representing a clickable link.
 *
 * @param destination Destination of the link when clicked.
 * @param children Clickable contents.
 * @param metadata Optional metadata of this node.
 * @return Created clickable link node.
 * @see NodeFactory.link
 * @see Link
 */
@InternalFactoryApi
public fun NodeFactory.link(
    destination: String,
    children: List<Node.Span> = emptyList(),
    metadata: NodeMetadata? = null
): Link = link(Link.Destination(destination), children, metadata)
