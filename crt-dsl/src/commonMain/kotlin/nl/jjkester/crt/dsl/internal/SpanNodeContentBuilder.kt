package nl.jjkester.crt.dsl.internal

import nl.jjkester.crt.api.model.Code
import nl.jjkester.crt.api.model.Emphasis
import nl.jjkester.crt.api.model.Language
import nl.jjkester.crt.api.model.Link
import nl.jjkester.crt.api.model.Node
import nl.jjkester.crt.api.model.StrongEmphasis
import nl.jjkester.crt.api.model.Text
import nl.jjkester.crt.dsl.LeafScope
import nl.jjkester.crt.dsl.SpanScope

internal class SpanNodeContentBuilder : AbstractNodeContentBuilder<Node.Span>(), SpanScope {

    override fun code(content: String, languageHint: String?) {
        add(Code(content, languageHint?.let(::Language), null))
    }

    override fun code(languageHint: String?, init: LeafScope.() -> Unit) {
        code(init.resolve(), languageHint)
    }

    override fun emphasis(init: SpanScope.() -> Unit) {
        add(Emphasis(init.resolve(), null))
    }

    override fun link(destination: String, init: SpanScope.() -> Unit) {
        add(Link(Link.Destination(destination), init.resolve(), null))
    }

    override fun strongEmphasis(init: SpanScope.() -> Unit) {
        add(StrongEmphasis(init.resolve(), null))
    }

    override fun text(text: String) {
        add(Text(text, null))
    }
}

internal fun (SpanScope.() -> Unit).resolve(): List<Node.Span> = SpanNodeContentBuilder().apply(this).children
