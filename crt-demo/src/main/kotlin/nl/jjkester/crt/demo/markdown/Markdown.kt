package nl.jjkester.crt.demo.markdown

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import nl.jjkester.crt.compose.LazyRichText
import nl.jjkester.crt.compose.RichText
import nl.jjkester.crt.compose.rememberParsedRichText
import nl.jjkester.crt.demo.rememberMaterialRichTextStyle
import nl.jjkester.crt.markdown.MarkdownParserFactory

@Composable
fun Markdown(
    text: String,
    modifier: Modifier = Modifier,
    style: TextStyle = TextStyle.Default,
    onClick: (String) -> Unit = {}
) {
    RichText(
        richText = rememberParsedRichText(text, MarkdownParserFactory),
        modifier = modifier,
        richTextStyle = rememberMaterialRichTextStyle(),
        style = style,
        onClick = onClick
    )
}

@Composable
fun LazyMarkdown(
    text: String,
    modifier: Modifier = Modifier,
    style: TextStyle = TextStyle.Default,
    contentPadding: PaddingValues = PaddingValues(0.dp),
    onClick: (String) -> Unit = {}
) {
    LazyRichText(
        richText = rememberParsedRichText(text, MarkdownParserFactory),
        modifier = modifier,
        richTextStyle = rememberMaterialRichTextStyle(),
        style = style,
        contentPadding = contentPadding,
        onClick = onClick
    )
}
