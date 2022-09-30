package nl.jjkester.crt.compose.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.text.BasicText
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import nl.jjkester.crt.compose.document.ComposeRichTextDocument
import nl.jjkester.crt.compose.text.ComposeRichTextString

@Composable
fun RichText(
    document: ComposeRichTextDocument,
    modifier: Modifier = Modifier,
    style: TextStyle = TextStyle.Default,
    onClick: (String) -> Unit = {}
) {
    CompositionLocalProvider(
        LocalRichTextOptions provides RichTextOptions(
            style = style,
            onClick = onClick
        )
    ) {
        Box(modifier = modifier) {
            document.content()
        }
    }
}

@Composable
fun RichText(
    span: ComposeRichTextString,
    modifier: Modifier = Modifier,
    style: TextStyle = TextStyle.Default,
    onTextLayout: (TextLayoutResult) -> Unit = {},
    overflow: TextOverflow = TextOverflow.Clip,
    softWrap: Boolean = true,
    maxLines: Int = Int.MAX_VALUE,
    onClick: (String) -> Unit = {}
) {
    CompositionLocalProvider(
        LocalRichTextOptions provides RichTextOptions(
            style = style,
            onClick = onClick
        )
    ) {
        RichTextImpl(
            span = span,
            modifier = modifier,
            onTextLayout = onTextLayout,
            overflow = overflow,
            softWrap = softWrap,
            maxLines = maxLines
        )
    }
}

@Composable
internal fun RichTextImpl(
    span: ComposeRichTextString,
    modifier: Modifier = Modifier,
    style: TextStyle = TextStyle.Default,
    onTextLayout: (TextLayoutResult) -> Unit = {},
    overflow: TextOverflow = TextOverflow.Clip,
    softWrap: Boolean = true,
    maxLines: Int = Int.MAX_VALUE
) {
    val options = LocalRichTextOptions.current
    val inlineStyle = LocalTextStyle.current.merge(style)
    val clickOffsetHandler = rememberClickOffsetHandler(span.clickOffsets)

    BasicText(
        text = buildAnnotatedString {
            withStyle(inlineStyle.toParagraphStyle()) {
                withStyle(inlineStyle.toSpanStyle()) {
                    append(span.annotatedString)
                }
            }
        },
        modifier = modifier.clickableText(clickOffsetHandler, options.onClick),
        style = options.style,
        onTextLayout = {
            clickOffsetHandler.onTextLayout(it)
            onTextLayout(it)
        },
        overflow = overflow,
        softWrap = softWrap,
        maxLines = maxLines,
        inlineContent = span.inlineContent
    )
}

@Composable
internal fun WithTextStyle(style: TextStyle, content: @Composable () -> Unit) {
    val merged = LocalTextStyle.current.merge(style)

    CompositionLocalProvider(
        LocalTextStyle provides merged,
        content = content
    )
}

private data class RichTextOptions(
    val style: TextStyle = TextStyle.Default,
    val onClick: (String) -> Unit = {}
)

private val LocalRichTextOptions = compositionLocalOf { RichTextOptions() }

private val LocalTextStyle = compositionLocalOf { TextStyle.Default }
