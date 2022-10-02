package nl.jjkester.crt.compose.style

import androidx.compose.foundation.BorderStroke
import androidx.compose.runtime.Stable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import nl.jjkester.crt.common.enumeration.Enumeration
import nl.jjkester.crt.compose.text.AnnotatedStringWithExtras

data class RichTextStyle(
    val h1: TextStyle = TextStyle.Default,
    val h2: TextStyle = TextStyle.Default,
    val h3: TextStyle = TextStyle.Default,
    val h4: TextStyle = TextStyle.Default,
    val h5: TextStyle = TextStyle.Default,
    val h6: TextStyle = TextStyle.Default,
    val paragraph: TextStyle = TextStyle.Default,
    val blockquote: BlockquoteStyle = BlockquoteStyle.Default,
    val enumeration: EnumerationStyle = EnumerationStyle.Default,
    val divider: BorderStroke? = null,
    val blockSpacing: Dp = 0.dp,
    val blockInset: Dp = Dp.Unspecified,
    val emphasis: SpanStyle = TextStyle.Default.toSpanStyle(),
    val strongEmphasis: SpanStyle = TextStyle.Default.toSpanStyle(),
    val code: SpanStyle = TextStyle.Default.toSpanStyle(),
    val link: SpanStyle = TextStyle.Default.toSpanStyle()
) {
    companion object {
        @Stable
        val Default = RichTextStyle()
    }
}

data class BlockquoteStyle(
    val text: TextStyle = TextStyle.Default,
    val border: BorderStroke? = null
) {
    companion object {
        @Stable
        val Default = BlockquoteStyle()
    }
}

data class EnumerationStyle(
    val ordered: Enumeration.Counting<AnnotatedStringWithExtras> = ComposeEnumerationFactory.alphanumeric,
    val unordered: Enumeration.Fixed<AnnotatedStringWithExtras> = ComposeEnumerationFactory.bulleted
) {
    companion object {
        @Stable
        val Default = EnumerationStyle()
    }
}

val LocalRichTextStyle = compositionLocalOf { RichTextStyle.Default }
