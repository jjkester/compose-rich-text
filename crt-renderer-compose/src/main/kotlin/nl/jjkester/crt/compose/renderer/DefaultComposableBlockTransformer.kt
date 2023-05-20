package nl.jjkester.crt.compose.renderer

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.text.AnnotatedString
import nl.jjkester.crt.api.model.Blockquote
import nl.jjkester.crt.api.model.Code
import nl.jjkester.crt.api.model.CodeBlock
import nl.jjkester.crt.api.model.Container
import nl.jjkester.crt.api.model.Divider
import nl.jjkester.crt.api.model.Heading
import nl.jjkester.crt.api.model.ListItem
import nl.jjkester.crt.api.model.OrderedList
import nl.jjkester.crt.api.model.Paragraph
import nl.jjkester.crt.api.model.UnorderedList
import nl.jjkester.crt.api.renderer.transform
import nl.jjkester.crt.common.enumeration.Enumeration
import nl.jjkester.crt.compose.internal.document.Blockquote
import nl.jjkester.crt.compose.internal.document.Container
import nl.jjkester.crt.compose.internal.document.Divider
import nl.jjkester.crt.compose.internal.document.ListContainer
import nl.jjkester.crt.compose.internal.document.ListItem
import nl.jjkester.crt.compose.internal.text.Span
import nl.jjkester.crt.compose.internal.text.WithTextStyle
import nl.jjkester.crt.compose.style.LocalRichTextStyle
import nl.jjkester.crt.compose.text.AnnotatedStringWithExtras
import nl.jjkester.crt.compose.text.withoutExtras

class DefaultComposableBlockTransformer(
    private val spanTransformerFactory: @Composable () -> AnnotatedStringSpanTransformer
) : ComposableBlockTransformer {

    override fun blockquote(node: Blockquote): @Composable () -> Unit = {
        val richTextStyle = LocalRichTextStyle.current

        WithTextStyle(style = richTextStyle.blockquote.text) {
            Blockquote(
                borderStroke = richTextStyle.blockquote.border,
                inset = richTextStyle.blockInset,
                space = richTextStyle.blockSpacing,
                contents = node.children.map { transform(it) }
            )
        }
    }

    override fun codeBlock(node: CodeBlock): @Composable () -> Unit = {
        Span(
            span = spanTransformerFactory().code(Code(node.content, node.languageHint, node.metadata))
        )
    }

    override fun container(node: Container): @Composable () -> Unit = {
        Container(
            space = LocalRichTextStyle.current.blockSpacing,
            contents = node.children.map { transform(it) }
        )
    }

    override fun divider(node: Divider): @Composable () -> Unit = {
        LocalRichTextStyle.current.divider?.let {
            Divider(borderStroke = it)
        }
    }

    override fun heading(node: Heading): @Composable () -> Unit = {
        val richTextStyle = LocalRichTextStyle.current

        Span(
            span = spanTransformerFactory().transformAll(node.children),
            style = when (node.level) {
                Heading.Level.One -> richTextStyle.h1
                Heading.Level.Two -> richTextStyle.h2
                Heading.Level.Three -> richTextStyle.h3
                Heading.Level.Four -> richTextStyle.h4
                Heading.Level.Five -> richTextStyle.h5
                Heading.Level.Six -> richTextStyle.h6
            }
        )
    }

    override fun listItem(node: ListItem): @Composable () -> Unit = {
        ListItem(
            marker = LocalListEnumerator.current.next(),
            markerSize = LocalRichTextStyle.current.blockInset,
            contents = node.children.map { transform(it) }
        )
    }

    override fun orderedList(node: OrderedList): @Composable () -> Unit = {
        val enumeration = LocalNestedOrderedListEnumeration.current ?: LocalRichTextStyle.current.enumeration.ordered
        CompositionLocalProvider(
            LocalNestedOrderedListEnumeration provides enumeration.child,
            LocalNestedUnorderedListEnumeration provides null,
            LocalListEnumerator provides enumeration.iterator()
        ) {
            ListContainer(
                contents = node.children.map { transform(it) }
            )
        }
    }

    override fun paragraph(node: Paragraph): @Composable () -> Unit = {
        Span(
            span = spanTransformerFactory().transformAll(node.children),
            style = LocalRichTextStyle.current.paragraph
        )
    }

    override fun unorderedList(node: UnorderedList): @Composable () -> Unit = {
        val enumeration = LocalNestedUnorderedListEnumeration.current ?: LocalRichTextStyle.current.enumeration.unordered
        CompositionLocalProvider(
            LocalNestedOrderedListEnumeration provides null,
            LocalNestedUnorderedListEnumeration provides enumeration.child,
            LocalListEnumerator provides enumeration.iterator()
        ) {
            ListContainer(
                contents = node.children.map { transform(it) }
            )
        }
    }

    companion object {
        private val LocalNestedUnorderedListEnumeration =
            compositionLocalOf<Enumeration<AnnotatedStringWithExtras>?> { null }
        private val LocalNestedOrderedListEnumeration =
            compositionLocalOf<Enumeration<AnnotatedStringWithExtras>?> { null }
        private val LocalListEnumerator = compositionLocalOf<Iterator<AnnotatedStringWithExtras>> {
            iterator { AnnotatedString("").withoutExtras() }
        }
    }
}
