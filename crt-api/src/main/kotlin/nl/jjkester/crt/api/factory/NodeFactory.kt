package nl.jjkester.crt.api.factory

import nl.jjkester.crt.api.model.Language
import nl.jjkester.crt.api.model.tree.Blockquote
import nl.jjkester.crt.api.model.tree.Code
import nl.jjkester.crt.api.model.tree.CodeBlock
import nl.jjkester.crt.api.model.tree.Container
import nl.jjkester.crt.api.model.tree.Divider
import nl.jjkester.crt.api.model.tree.Emphasis
import nl.jjkester.crt.api.model.tree.Heading
import nl.jjkester.crt.api.model.tree.Link
import nl.jjkester.crt.api.model.tree.ListItem
import nl.jjkester.crt.api.model.tree.Node
import nl.jjkester.crt.api.model.tree.NodeMetadata
import nl.jjkester.crt.api.model.tree.OrderedList
import nl.jjkester.crt.api.model.tree.Paragraph
import nl.jjkester.crt.api.model.tree.StrongEmphasis
import nl.jjkester.crt.api.model.tree.Text
import nl.jjkester.crt.api.model.tree.UnorderedList

public interface NodeFactory {

    public fun blockquote(
        children: List<Node.Block> = emptyList(),
        metadata: NodeMetadata? = null
    ): Blockquote

    public fun code(
        content: String,
        languageHint: Language? = null,
        metadata: NodeMetadata? = null
    ): Code

    public fun codeBlock(
        content: String,
        languageHint: Language? = null,
        metadata: NodeMetadata? = null
    ): CodeBlock

    public fun container(
        children: List<Node.Block> = emptyList(),
        metadata: NodeMetadata? = null
    ): Container

    public fun divider(
        metadata: NodeMetadata? = null
    ): Divider

    public fun emphasis(
        children: List<Node.Span> = emptyList(),
        metadata: NodeMetadata? = null
    ): Emphasis

    public fun heading(
        level: Heading.Level,
        children: List<Node.Span> = emptyList(),
        metadata: NodeMetadata? = null
    ): Heading

    public fun link(
        destination: Link.Destination,
        children: List<Node.Span> = emptyList(),
        metadata: NodeMetadata? = null
    ): Link

    public fun listItem(
        children: List<Node.Block> = emptyList(),
        metadata: NodeMetadata? = null
    ): ListItem

    public fun orderedList(
        children: List<ListItem> = emptyList(),
        metadata: NodeMetadata? = null
    ): OrderedList

    public fun paragraph(
        children: List<Node.Span> = emptyList(),
        metadata: NodeMetadata? = null
    ): Paragraph

    public fun strongEmphasis(
        children: List<Node.Span> = emptyList(),
        metadata: NodeMetadata? = null
    ): StrongEmphasis

    public fun text(
        content: String,
        metadata: NodeMetadata? = null
    ): Text

    public fun unorderedList(
        children: List<ListItem> = emptyList(),
        metadata: NodeMetadata? = null
    ): UnorderedList
}

public fun NodeFactory.code(
    content: String,
    languageHint: String? = null,
    metadata: NodeMetadata? = null
): Code = code(content, languageHint?.let(::Language), metadata)

public fun NodeFactory.codeBlock(
    content: String,
    languageHint: String? = null,
    metadata: NodeMetadata? = null
): CodeBlock = codeBlock(content, languageHint?.let(::Language), metadata)

public fun NodeFactory.heading(
    level: Int,
    children: List<Node.Span> = emptyList(),
    metadata: NodeMetadata? = null
): Heading = heading(Heading.Level.fromInt(level) ?: Heading.Level.Six, children, metadata)

public fun NodeFactory.link(
    destination: String,
    children: List<Node.Span> = emptyList(),
    metadata: NodeMetadata? = null
): Link = link(Link.Destination(destination), children, metadata)
