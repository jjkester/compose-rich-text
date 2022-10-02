package nl.jjkester.crt.compose.internal.text

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocal
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.text.TextStyle

@Composable
internal fun WithTextStyle(style: TextStyle, content: @Composable () -> Unit) {
    CompositionLocalProvider(
        ProvidableLocalTextStyle provides LocalTextStyle.current.merge(style),
        content = content
    )
}

private val ProvidableLocalTextStyle = compositionLocalOf { TextStyle.Default }

internal val LocalTextStyle: CompositionLocal<TextStyle> = ProvidableLocalTextStyle
