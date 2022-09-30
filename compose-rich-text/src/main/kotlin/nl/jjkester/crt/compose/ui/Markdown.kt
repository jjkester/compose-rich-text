package nl.jjkester.crt.compose.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import nl.jjkester.crt.compose.document.ComposableDocumentBuilder
import nl.jjkester.crt.compose.document.ComposeRichTextDocument
import nl.jjkester.crt.compose.document.DocumentStyles
import nl.jjkester.crt.compose.text.AnnotatedStringTextBuilder
import nl.jjkester.crt.compose.text.ComposeRichTextString
import nl.jjkester.crt.compose.text.TextStyles
import nl.jjkester.crt.markdown.MarkdownDocumentRenderer
import nl.jjkester.crt.markdown.MarkdownTextRenderer
import org.commonmark.parser.Parser

@Composable
fun Markdown(
    text: String,
    modifier: Modifier = Modifier,
    style: TextStyle = TextStyle.Default,
    onClick: (String) -> Unit = {}
) {
    Markdown(
        text = text,
        documentStyles = DocumentStyles.Default,
        textStyles = TextStyles.Default,
        modifier = modifier,
        style = style,
        onClick = onClick
    )
}

@Composable
fun Markdown(
    text: String,
    documentStyles: DocumentStyles,
    textStyles: TextStyles,
    modifier: Modifier = Modifier,
    style: TextStyle = TextStyle.Default,
    onClick: (String) -> Unit = {}
) {
    val renderer = rememberMarkdownRenderer(documentStyles, textStyles)
    val document = remember(text, renderer) { renderer.render(text) }

    RichText(
        document = document,
        modifier = modifier,
        style = style,
        onClick = onClick
    )
}

@Composable
fun InlineMarkdown(
    text: String,
    modifier: Modifier = Modifier,
    style: TextStyle = TextStyle.Default,
    onClick: (String) -> Unit = {}
) {
    InlineMarkdown(
        text = text,
        textStyles = TextStyles.Default,
        modifier = modifier,
        style = style,
        onClick = onClick
    )
}

@Composable
fun InlineMarkdown(
    text: String,
    textStyles: TextStyles,
    modifier: Modifier = Modifier,
    style: TextStyle = TextStyle.Default,
    onClick: (String) -> Unit = {}
) {
    val renderer = rememberInlineMarkdownRenderer(textStyles)
    val span = remember(text, renderer) { renderer.render(text) }

    RichText(
        span = span,
        modifier = modifier,
        style = style,
        onClick = onClick
    )
}

@Composable
fun rememberMarkdownRenderer(
    documentStyles: DocumentStyles = DocumentStyles.Default,
    textStyles: TextStyles = TextStyles.Default
): MarkdownDocumentRenderer<ComposeRichTextDocument, ComposeRichTextString> = remember(documentStyles, textStyles) {
    MarkdownDocumentRenderer(Parser.builder().build()) {
        ComposableDocumentBuilder(documentStyles) {
            AnnotatedStringTextBuilder(textStyles)
        }
    }
}

@Composable
fun rememberInlineMarkdownRenderer(
    textStyles: TextStyles = TextStyles.Default
): MarkdownTextRenderer<ComposeRichTextString> = remember(textStyles) {
    MarkdownTextRenderer(Parser.builder().build()) {
        AnnotatedStringTextBuilder(textStyles)
    }
}
