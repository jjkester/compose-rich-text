package nl.jjkester.crt.demo.markdown

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import nl.jjkester.crt.api.renderer.DocumentRenderer
import nl.jjkester.crt.compose.RichText
import nl.jjkester.crt.compose.builder.ComposeRichTextDocument
import nl.jjkester.crt.compose.rememberDocumentRenderer
import nl.jjkester.crt.compose.style.DocumentStyles
import nl.jjkester.crt.compose.style.TextStyles
import nl.jjkester.crt.markdown.MarkdownDocumentRenderer

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
    RichText(
        string = text,
        renderer = rememberMarkdownRenderer(documentStyles, textStyles),
        modifier = modifier,
        style = style,
        onClick = onClick
    )
}

@Composable
fun rememberMarkdownRenderer(
    documentStyles: DocumentStyles = DocumentStyles.Default,
    textStyles: TextStyles = TextStyles.Default
): DocumentRenderer<ComposeRichTextDocument> = rememberDocumentRenderer(documentStyles, textStyles) {
    MarkdownDocumentRenderer { it }
}
