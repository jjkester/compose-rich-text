package nl.jjkester.crt.compose.internal

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.isSpecified

@Composable
internal fun Blockquote(
    modifier: Modifier = Modifier,
    borderStroke: BorderStroke? = null,
    inset: Dp = Dp.Unspecified,
    space: Dp = Dp.Unspecified,
    content: @Composable () -> Unit
) {
    val borderModifier = if (borderStroke != null) Modifier.blockquoteBorder(borderStroke) else Modifier
    val insetModifier = if (inset.isSpecified) Modifier.padding(start = inset) else Modifier

    Container(
        space = space,
        modifier = modifier.then(borderModifier).then(insetModifier),
        content = content
    )
}

private fun Modifier.blockquoteBorder(stroke: BorderStroke) = drawBehind {
    val strokeWidth = stroke.width.toPx()

    drawLine(
        brush = stroke.brush,
        start = Offset(strokeWidth / 2, 0f),
        end = Offset(strokeWidth / 2, size.height),
        strokeWidth = strokeWidth
    )
}
