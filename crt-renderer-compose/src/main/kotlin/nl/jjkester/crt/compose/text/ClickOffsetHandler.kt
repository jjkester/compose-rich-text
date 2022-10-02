package nl.jjkester.crt.compose.text

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.TextLayoutResult

internal class ClickOffsetHandler(private val clickOffsets: Collection<ClickOffset>) {
    private var layoutResult: TextLayoutResult? = null

    fun onTextLayout(result: TextLayoutResult) {
        layoutResult = result
    }

    fun getIdForClick(offset: Offset): String? {
        return layoutResult?.let { layoutResult ->
            clickOffsets.find {
                layoutResult.getOffsetForPosition(offset) in it.offset
            }?.id
        }
    }
}

@Composable
internal fun rememberClickOffsetHandler(clickOffsets: Collection<ClickOffset>): ClickOffsetHandler =
    remember(clickOffsets) { ClickOffsetHandler(clickOffsets) }

internal fun Modifier.clickableText(
    clickOffsetHandler: ClickOffsetHandler,
    onClick: (String) -> Unit
) = pointerInput(onClick) {
    detectTapGestures { offset ->
        clickOffsetHandler.getIdForClick(offset)?.also(onClick)
    }
}
