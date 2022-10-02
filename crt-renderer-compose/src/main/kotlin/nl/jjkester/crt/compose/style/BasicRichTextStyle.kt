package nl.jjkester.crt.compose.style

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em

@Composable
fun rememberBasicRichTextStyle(spacing: PaddingValues): RichTextStyle {
    val layoutDirection = LocalLayoutDirection.current
    return remember(spacing) {
        RichTextStyle(
            h1 = TextStyle(
                fontWeight = FontWeight.Bold,
                fontSize = 2.em
            ),
            h2 = TextStyle(
                fontWeight = FontWeight.Bold,
                fontSize = 1.5.em
            ),
            h3 = TextStyle(
                fontWeight = FontWeight.Bold,
                fontSize = 1.25.em
            ),
            h4 = TextStyle(
                fontWeight = FontWeight.Bold,
                fontSize = 1.1.em
            ),
            h5 = TextStyle(
                fontWeight = FontWeight.Bold,
                fontSize = 1.em
            ),
            h6 = TextStyle(
                textDecoration = TextDecoration.Underline,
                fontSize = 1.em
            ),
            paragraph = TextStyle(
                fontSize = 1.em,
            ),
            blockquote = BlockquoteStyle(
                text = TextStyle(color = Color.Gray),
                border = BorderStroke(2.dp, Color.Gray),
            ),
            enumeration = EnumerationStyle(
                ordered = ComposeEnumerationFactory.alphanumeric,
                unordered = ComposeEnumerationFactory.bulleted
            ),
            blockSpacing = maxOf(spacing.calculateBottomPadding(), spacing.calculateTopPadding()),
            blockInset = spacing.calculateStartPadding(layoutDirection),
            emphasis = SpanStyle(fontStyle = FontStyle.Italic),
            strongEmphasis = SpanStyle(fontWeight = FontWeight.Bold),
            code = SpanStyle(fontFamily = FontFamily.Monospace),
            link = SpanStyle(color = Color.Blue, textDecoration = TextDecoration.Underline)
        )
    }
}
