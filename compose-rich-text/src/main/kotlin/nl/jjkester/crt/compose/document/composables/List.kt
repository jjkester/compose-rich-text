package nl.jjkester.crt.compose.document.composables

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.widthIn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import nl.jjkester.crt.compose.text.ComposeRichTextString
import nl.jjkester.crt.compose.ui.RichTextImpl

@Composable
internal fun ListContainer(
    contents: List<@Composable () -> Unit>,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        contents.forEach { it() }
    }
}

@Composable
internal fun ListItem(
    marker: ComposeRichTextString,
    markerSize: Dp,
    contents: List<@Composable () -> Unit>,
    modifier: Modifier = Modifier
) {
    Row(modifier = modifier) {
        RichTextImpl(
            span = marker,
            modifier = Modifier
                .widthIn(min = markerSize)
                .alignByBaseline()
        )
        Column(
            modifier = Modifier.alignByBaseline()
        ) {
            contents.forEach { it() }
        }
    }
}
