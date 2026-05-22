package nl.jjkester.crt.compose.internal.document

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.isSpecified

@Composable
internal fun Container(
    modifier: Modifier = Modifier,
    space: Dp = Dp.Unspecified,
    contents: List<@Composable () -> Unit>
) {
    val arrangement = if (space.isSpecified) Arrangement.spacedBy(space) else Arrangement.Top

    Column(
        modifier = modifier,
        verticalArrangement = arrangement
    ) {
        contents.forEach { it() }
    }
}
