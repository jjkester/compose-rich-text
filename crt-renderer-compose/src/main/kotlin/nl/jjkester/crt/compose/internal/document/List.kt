package nl.jjkester.crt.compose.internal.document

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.widthIn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.unit.Dp
import nl.jjkester.crt.compose.internal.text.Span
import nl.jjkester.crt.compose.text.AnnotatedStringWithExtras
import nl.jjkester.crt.compose.text.withoutExtras

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
        Row(
            modifier = Modifier
                .widthIn(min = markerSize)
                .alignByBaseline(),
            horizontalArrangement = Arrangement.End
        ) {
            Span(span = marker)
            Span(span = AnnotatedString("\u2004").withoutExtras())
        }
        Column(
            modifier = Modifier.alignByBaseline()
        ) {
            contents.forEach { it() }
        }
    }
}
