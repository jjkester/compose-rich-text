package nl.jjkester.crt.dsl.internal

import nl.jjkester.crt.api.model.Blockquote
import nl.jjkester.crt.api.model.CodeBlock
import nl.jjkester.crt.api.model.Divider
import nl.jjkester.crt.api.model.Heading
import nl.jjkester.crt.api.model.Language
import nl.jjkester.crt.api.model.Node
import nl.jjkester.crt.api.model.OrderedList
import nl.jjkester.crt.api.model.Paragraph
import nl.jjkester.crt.api.model.UnorderedList
import nl.jjkester.crt.dsl.BlockScope
import nl.jjkester.crt.dsl.LeafScope
import nl.jjkester.crt.dsl.ListScope
import nl.jjkester.crt.dsl.SpanScope

internal class BlockNodeContentBuilder : AbstractNodeContentBuilder<Node.Block>(), BlockScope {

    override fun blockquote(init: BlockScope.() -> Unit) {
        add(Blockquote(init.resolve(), null))
    }

    override fun code(content: String, languageHint: String?) {
        add(CodeBlock(content, languageHint?.let(::Language), null))
    }

    override fun code(languageHint: String?, init: LeafScope.() -> Unit) {
        code(init.resolve(), languageHint)
    }

    override fun divider() {
        add(Divider(null))
    }

    override fun heading(level: Int, init: SpanScope.() -> Unit) {
        val parsedLevel = requireNotNull(Heading.Level.fromIntOrNull(level)) { "Invalid heading level" }
        add(Heading(parsedLevel, init.resolve(), null))
    }

    override fun orderedList(init: ListScope.() -> Unit) {
        add(OrderedList(init.resolve(), null))
    }

    override fun paragraph(init: SpanScope.() -> Unit) {
        add(Paragraph(init.resolve(), null))
    }

    override fun unorderedList(init: ListScope.() -> Unit) {
        add(UnorderedList(init.resolve(), null))
    }
}

internal fun (BlockScope.() -> Unit).resolve(): List<Node.Block> = BlockNodeContentBuilder().apply(this).children
