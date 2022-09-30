package nl.jjkester.crt.compose.document

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.text.AnnotatedString
import nl.jjkester.crt.compose.document.composables.*
import nl.jjkester.crt.compose.text.ComposeRichTextString
import nl.jjkester.crt.compose.ui.RichTextImpl
import nl.jjkester.crt.compose.ui.WithTextStyle
import nl.jjkester.crt.document.DocumentBuilder
import nl.jjkester.crt.document.Enumeration
import nl.jjkester.crt.text.TextBuilder

class ComposableDocumentBuilder(
    private val documentStyles: DocumentStyles,
    private val textBuilderFactory: () -> TextBuilder<ComposeRichTextString>
) : DocumentBuilder<ComposeRichTextDocument, ComposeRichTextString> {
    private val contextManager = ContextManager().apply {
        start("root")
    }

    override fun heading(level: Int, content: TextBuilder<ComposeRichTextString>.() -> Unit) {
        contextManager.add {
            RichTextImpl(
                span = textBuilderFactory().apply(content).build(),
                style = when (level) {
                    1 -> documentStyles.h1
                    2 -> documentStyles.h2
                    3 -> documentStyles.h3
                    4 -> documentStyles.h4
                    5 -> documentStyles.h5
                    else -> documentStyles.h6
                }
            )
        }
    }

    override fun paragraph(content: TextBuilder<ComposeRichTextString>.() -> Unit) {
        contextManager.add {
            RichTextImpl(
                span = textBuilderFactory().apply(content).build(),
                style = documentStyles.paragraph
            )
        }
    }

    override fun blockquote(content: DocumentBuilder<ComposeRichTextDocument, ComposeRichTextString>.() -> Unit) {
        contextManager.nested("blockquote", { content() }) { children ->
            WithTextStyle(documentStyles.blockquote) {
                Blockquote(
                    borderStroke = documentStyles.blockquoteBorder,
                    inset = documentStyles.inset,
                    space = documentStyles.paragraphSpacing,
                    content = children()
                )
            }
        }
    }

    override fun orderedList(content: DocumentBuilder<ComposeRichTextDocument, ComposeRichTextString>.() -> Unit) {
        contextManager.nested("orderedList", { content() }) { children ->
            val enumeration = LocalNestedOrderedListEnumeration.current ?: documentStyles.orderedEnumeration
            CompositionLocalProvider(
                LocalNestedOrderedListEnumeration provides enumeration.child,
                LocalNestedUnorderedListEnumeration provides null,
                LocalListEnumerator provides enumeration.sequence.iterator()
            ) {
                ListContainer(
                    contents = children
                )
            }
        }
    }

    override fun unorderedList(content: DocumentBuilder<ComposeRichTextDocument, ComposeRichTextString>.() -> Unit) {
        contextManager.nested("unorderedList", { content() }) { children ->
            val enumeration = LocalNestedUnorderedListEnumeration.current ?: documentStyles.unorderedEnumeration
            CompositionLocalProvider(
                LocalNestedOrderedListEnumeration provides null,
                LocalNestedUnorderedListEnumeration provides enumeration.child,
                LocalListEnumerator provides generateSequence { enumeration.string }.iterator()
            ) {
                ListContainer(
                    contents = children
                )
            }
        }
    }

    override fun listItem(content: DocumentBuilder<ComposeRichTextDocument, ComposeRichTextString>.() -> Unit) {
        contextManager.nested("listItem", { content() }) { children ->
            ListItem(
                marker = LocalListEnumerator.current.next(),
                markerSize = documentStyles.inset,
                contents = children
            )
        }
    }

    override fun code(content: String, language: String?) {
        contextManager.add {
            RichTextImpl(
                span = textBuilderFactory().apply { code { append(content) } }.build()
            )
        }
    }

    override fun separator() {
        contextManager.add {
            Separator(
                borderStroke = documentStyles.separatorBorder
            )
        }
    }

    override fun build(): ComposeRichTextDocument {
        return ComposeRichTextDocument {
            Container(
                space = documentStyles.paragraphSpacing,
                content = contextManager.finish().invoke()
            )
        }
    }

    private class ContextManager {
        private val stack = ArrayDeque<Context>(10)

        fun add(item: @Composable () -> Unit) {
            stack.last().add(item)
        }

        fun start(debugName: String) {
            stack.addLast(Context(debugName))
        }

        fun finish(): List<@Composable () -> Unit> {
            return stack.removeLast().items
        }

        fun nested(
            debugName: String,
            block: () -> Unit,
            item: @Composable (children: List<@Composable () -> Unit>) -> Unit
        ) {
            start(debugName)
            block()
            val children = finish()
            add { item(children) }
        }
    }

    private class Context(val debugName: String) {
        private val _items = mutableListOf<@Composable () -> Unit>()
        val items: List<@Composable () -> Unit> = _items

        fun add(item: @Composable () -> Unit) {
            _items.add(item)
        }
    }

    companion object {
        private val LocalNestedUnorderedListEnumeration =
            compositionLocalOf<Enumeration.Fixed<ComposeRichTextString>?> { null }
        private val LocalNestedOrderedListEnumeration =
            compositionLocalOf<Enumeration.Counting<ComposeRichTextString>?> { null }
        private val LocalListEnumerator = compositionLocalOf<Iterator<ComposeRichTextString>> {
            iterator { ComposeRichTextString(AnnotatedString(""), emptyMap(), emptyList()) }
        }
    }
}

private operator fun List<@Composable () -> Unit>.invoke() = @Composable {
    forEach { it() }
}