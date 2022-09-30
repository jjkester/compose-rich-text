package nl.jjkester.crt.compose.document.composables

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset

@Composable
internal fun Separator(
    borderStroke: BorderStroke
) {
    Box(
        modifier = Modifier
            .height(borderStroke.width)
            .fillMaxWidth()
            .drawBehind {
                val center = borderStroke.width.toPx() / 2

                drawLine(
                    brush = borderStroke.brush,
                    start = Offset(0f, center),
                    end = Offset(size.width, center),
                    strokeWidth = borderStroke.width.toPx()
                )
            }
    )
}
