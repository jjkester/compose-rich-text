package nl.jjkester.crt.demo.markdown

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import nl.jjkester.crt.compose.LazyRichText
import nl.jjkester.crt.compose.RichText
import nl.jjkester.crt.compose.rememberRichTextState
import nl.jjkester.crt.compose.text.LinkHandler
import nl.jjkester.crt.demo.rememberMaterialRichTextStyle
import nl.jjkester.crt.markdown.MarkdownParserFactory
import java.io.InputStream

@Composable
fun Markdown(
    text: InputStream,
    modifier: Modifier = Modifier,
    style: TextStyle = TextStyle.Default,
    linkHandler: LinkHandler = LinkHandler.Default
) {
    RichText(
        state = rememberRichTextState(text, MarkdownParserFactory::create),
        modifier = modifier,
        richTextStyle = rememberMaterialRichTextStyle(),
        style = style,
        linkHandler = linkHandler
    )
}

@Composable
fun LazyMarkdown(
    text: InputStream,
    modifier: Modifier = Modifier,
    style: TextStyle = TextStyle.Default,
    contentPadding: PaddingValues = PaddingValues(0.dp),
    linkHandler: LinkHandler = LinkHandler.Default
) {
    LazyRichText(
        state = rememberRichTextState(text, MarkdownParserFactory::create),
        modifier = modifier,
        richTextStyle = rememberMaterialRichTextStyle(),
        style = style,
        contentPadding = contentPadding,
        linkHandler = linkHandler
    )
}
