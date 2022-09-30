package nl.jjkester.crt.compose.internal.text

import androidx.compose.foundation.text.BasicText
import androidx.compose.runtime.Composable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import nl.jjkester.crt.compose.builder.ComposeRichTextString

@Composable
internal fun Span(
    span: ComposeRichTextString,
    modifier: Modifier = Modifier,
    style: TextStyle = TextStyle.Default,
    onTextLayout: (TextLayoutResult) -> Unit = {},
    overflow: TextOverflow = TextOverflow.Clip,
    softWrap: Boolean = true,
    maxLines: Int = Int.MAX_VALUE
) {
    val mergedStyle = LocalTextStyle.current.merge(style)
    val clickOffsetHandler = rememberClickOffsetHandler(span.clickOffsets)

    BasicText(
        text = buildAnnotatedString {
            withStyle(mergedStyle.toParagraphStyle()) {
                withStyle(mergedStyle.toSpanStyle()) {
                    append(span.annotatedString)
                }
            }
        },
        modifier = modifier.clickableText(clickOffsetHandler, LocalSpanClickHandler.current),
        style = LocalSpanBaseStyle.current,
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

internal val LocalSpanClickHandler = compositionLocalOf<(String) -> Unit> {
    error("No span click handler set")
}

internal val LocalSpanBaseStyle = compositionLocalOf<TextStyle> {
    error("No span base style set")
}
