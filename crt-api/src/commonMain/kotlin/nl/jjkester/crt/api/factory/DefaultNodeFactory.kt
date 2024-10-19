package nl.jjkester.crt.api.factory

import nl.jjkester.crt.api.annotations.InternalFactoryApi
import nl.jjkester.crt.api.model.Blockquote
import nl.jjkester.crt.api.model.Code
import nl.jjkester.crt.api.model.CodeBlock
import nl.jjkester.crt.api.model.Language
import nl.jjkester.crt.api.model.Container
import nl.jjkester.crt.api.model.Divider
import nl.jjkester.crt.api.model.Emphasis
import nl.jjkester.crt.api.model.Heading
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
 * Node factory implementation that directly calls the node constructor without any customizations.
 */
public object DefaultNodeFactory : NodeFactory {

    @InternalFactoryApi
    override fun blockquote(
        children: List<Node.Block>,
        metadata: NodeMetadata?
    ): Blockquote = Blockquote(children, metadata)

    @InternalFactoryApi
    override fun code(
        content: String,
        languageHint: Language?,
        metadata: NodeMetadata?
    ): Code = Code(content, languageHint, metadata)

    @InternalFactoryApi
    override fun codeBlock(
        content: String,
        languageHint: Language?,
        metadata: NodeMetadata?
    ): CodeBlock = CodeBlock(content, languageHint, metadata)

    @InternalFactoryApi
    override fun container(
        children: List<Node.Block>,
        metadata: NodeMetadata?
    ): Container = Container(children, metadata)

    @InternalFactoryApi
    override fun divider(
        metadata: NodeMetadata?
    ): Divider = Divider(metadata)

    @InternalFactoryApi
    override fun emphasis(
        children: List<Node.Span>,
        metadata: NodeMetadata?
    ): Emphasis = Emphasis(children, metadata)

    @InternalFactoryApi
    override fun heading(
        level: Heading.Level,
        children: List<Node.Span>,
        metadata: NodeMetadata?
    ): Heading = Heading(level, children, metadata)

    @InternalFactoryApi
    override fun link(
        destination: Link.Destination,
        children: List<Node.Span>,
        metadata: NodeMetadata?
    ): Link = Link(destination, children, metadata)

    @InternalFactoryApi
    override fun listItem(
        children: List<Node.Block>,
        metadata: NodeMetadata?
    ): ListItem = ListItem(children, metadata)

    @InternalFactoryApi
    override fun orderedList(
        children: List<ListItem>,
        metadata: NodeMetadata?
    ): OrderedList = OrderedList(children, metadata)

    @InternalFactoryApi
    override fun paragraph(
        children: List<Node.Span>,
        metadata: NodeMetadata?
    ): Paragraph = Paragraph(children, metadata)

    @InternalFactoryApi
    override fun strongEmphasis(
        children: List<Node.Span>,
        metadata: NodeMetadata?
    ): StrongEmphasis = StrongEmphasis(children, metadata)

    @InternalFactoryApi
    override fun text(
        content: String,
        metadata: NodeMetadata?
    ): Text = Text(content, metadata)

    @InternalFactoryApi
    override fun unorderedList(
        children: List<ListItem>,
        metadata: NodeMetadata?
    ): UnorderedList = UnorderedList(children, metadata)
}
