package nl.jjkester.crt.compose.style

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration

data class TextStyles(
    val emphasis: SpanStyle = SpanStyle(fontStyle = FontStyle.Italic),
    val strongEmphasis: SpanStyle = SpanStyle(fontWeight = FontWeight.Bold),
    val code: SpanStyle = SpanStyle(fontFamily = FontFamily.Monospace),
    val link: SpanStyle = SpanStyle(color = Color.Blue, textDecoration = TextDecoration.Underline)
) {
    companion object {
        val Default: TextStyles by lazy(LazyThreadSafetyMode.NONE, ::TextStyles)
    }
}
