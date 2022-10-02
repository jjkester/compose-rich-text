package nl.jjkester.crt.compose.internal.document

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.isSpecified

@Composable
internal fun Blockquote(
    modifier: Modifier = Modifier,
    borderStroke: BorderStroke? = null,
    inset: Dp = Dp.Unspecified,
    space: Dp = Dp.Unspecified,
    contents: List<@Composable () -> Unit>
) {
    val borderModifier = if (borderStroke != null) Modifier.blockquoteBorder(borderStroke) else Modifier
    val insetModifier = if (inset.isSpecified) Modifier.padding(start = inset) else Modifier

    Container(
        space = space,
        modifier = modifier
            .then(borderModifier)
            .then(insetModifier),
        contents = contents
    )
}

private fun Modifier.blockquoteBorder(stroke: BorderStroke) = composed {
    val layoutDirection = LocalLayoutDirection.current
    drawBehind {
        val strokeWidth = stroke.width.toPx()
        val xOffset = when (layoutDirection) {
            LayoutDirection.Ltr -> strokeWidth
            LayoutDirection.Rtl -> size.width - strokeWidth
        }

        drawLine(
            brush = stroke.brush,
            start = Offset(xOffset, 0f),
            end = Offset(xOffset, size.height),
            strokeWidth = strokeWidth
        )
    }
}
