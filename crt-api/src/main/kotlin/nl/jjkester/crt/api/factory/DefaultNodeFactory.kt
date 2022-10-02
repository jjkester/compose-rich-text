package nl.jjkester.crt.api.factory

import nl.jjkester.crt.api.model.tree.Blockquote
import nl.jjkester.crt.api.model.tree.Code
import nl.jjkester.crt.api.model.tree.CodeBlock
import nl.jjkester.crt.api.model.Language
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

public object DefaultNodeFactory : NodeFactory {

    override fun blockquote(
        children: List<Node.Block>,
        metadata: NodeMetadata?
    ): Blockquote = Blockquote(children, metadata)

    override fun code(
        content: String,
        languageHint: Language?,
        metadata: NodeMetadata?
    ): Code = Code(content, languageHint, metadata)

    override fun codeBlock(
        content: String,
        languageHint: Language?,
        metadata: NodeMetadata?
    ): CodeBlock = CodeBlock(content, languageHint, metadata)

    override fun container(
        children: List<Node.Block>,
        metadata: NodeMetadata?
    ): Container = Container(children, metadata)

    override fun divider(
        metadata: NodeMetadata?
    ): Divider = Divider(metadata)

    override fun emphasis(
        children: List<Node.Span>,
        metadata: NodeMetadata?
    ): Emphasis = Emphasis(children, metadata)

    override fun heading(
        level: Heading.Level,
        children: List<Node.Span>,
        metadata: NodeMetadata?
    ): Heading = Heading(level, children, metadata)

    override fun link(
        destination: Link.Destination,
        children: List<Node.Span>,
        metadata: NodeMetadata?
    ): Link = Link(destination, children, metadata)

    override fun listItem(
        children: List<Node.Block>,
        metadata: NodeMetadata?
    ): ListItem = ListItem(children, metadata)

    override fun orderedList(
        children: List<ListItem>,
        metadata: NodeMetadata?
    ): OrderedList = OrderedList(children, metadata)

    override fun paragraph(
        children: List<Node.Span>,
        metadata: NodeMetadata?
    ): Paragraph = Paragraph(children, metadata)

    override fun strongEmphasis(
        children: List<Node.Span>,
        metadata: NodeMetadata?
    ): StrongEmphasis = StrongEmphasis(children, metadata)

    override fun text(
        content: String,
        metadata: NodeMetadata?
    ): Text = Text(content, metadata)

    override fun unorderedList(
        children: List<ListItem>,
        metadata: NodeMetadata?
    ): UnorderedList = UnorderedList(children, metadata)
}
