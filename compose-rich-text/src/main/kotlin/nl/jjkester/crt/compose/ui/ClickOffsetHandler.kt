package nl.jjkester.crt.compose.ui

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.TextLayoutResult
import nl.jjkester.crt.compose.text.ClickOffset

internal class ClickOffsetHandler(private val clickOffsets: Collection<ClickOffset>) {
    private val layoutResult = mutableStateOf<TextLayoutResult?>(null)

    fun onTextLayout(result: TextLayoutResult) {
        layoutResult.value = result
    }

    fun getIdForClick(offset: Offset): String? {
        return layoutResult.value?.let { layoutResult ->
            clickOffsets.find {
                layoutResult.getOffsetForPosition(offset) in it.offset
            }?.id
        }
    }
}

@Composable
internal fun rememberClickOffsetHandler(clickOffsets: Collection<ClickOffset>): ClickOffsetHandler = remember {
    ClickOffsetHandler(clickOffsets)
}

internal fun Modifier.clickableText(
    clickOffsetHandler: ClickOffsetHandler,
    onClick: (String) -> Unit
) = pointerInput(onClick) {
    detectTapGestures { offset ->
        clickOffsetHandler.getIdForClick(offset)?.also(onClick)
    }
}
