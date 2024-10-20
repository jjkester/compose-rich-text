package nl.jjkester.crt.dsl

import nl.jjkester.crt.api.model.Container
import nl.jjkester.crt.api.model.Node
import nl.jjkester.crt.dsl.internal.resolve

@DslMarker
@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.BINARY)
internal annotation class RichTextDsl

@RichTextDsl
public interface BlockScope {

    public fun blockquote(init: BlockScope.() -> Unit)

    public fun code(content: String, languageHint: String? = null)

    public fun code(languageHint: String? = null, init: LeafScope.() -> Unit)

    public fun divider()

    public fun heading(level: Int, init: SpanScope.() -> Unit)

    public fun orderedList(init: ListScope.() -> Unit)

    public fun paragraph(init: SpanScope.() -> Unit)

    public fun unorderedList(init: ListScope.() -> Unit)
}

@RichTextDsl
public interface ListScope {

    public fun listItem(init: BlockScope.() -> Unit)
}

@RichTextDsl
public interface SpanScope {

    public fun code(content: String, languageHint: String? = null)

    public fun code(languageHint: String? = null, init: LeafScope.() -> Unit)

    public fun emphasis(init: SpanScope.() -> Unit)

    public fun link(destination: String, init: SpanScope.() -> Unit)

    public fun strongEmphasis(init: SpanScope.() -> Unit)

    public fun text(text: String)

    public operator fun String.unaryPlus(): Unit = text(this)
}

@RichTextDsl
public interface LeafScope {

    public operator fun String.unaryPlus()
}

public fun buildRichText(init: BlockScope.() -> Unit): Node {
    return Container(init.resolve(), null)
}
