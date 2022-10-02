package nl.jjkester.crt.compose.internal.document

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.widthIn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import nl.jjkester.crt.compose.text.AnnotatedStringWithExtras
import nl.jjkester.crt.compose.internal.text.Span

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
    marker: AnnotatedStringWithExtras,
    markerSize: Dp,
    contents: List<@Composable () -> Unit>,
    modifier: Modifier = Modifier
) {
    Row(modifier = modifier) {
        Span(
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
