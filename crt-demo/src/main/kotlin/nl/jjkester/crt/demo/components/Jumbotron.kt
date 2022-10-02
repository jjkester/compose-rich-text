package nl.jjkester.crt.demo.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.isSpecified
import androidx.compose.ui.unit.dp

@Composable
fun Jumbotron(
    title: String,
    modifier: Modifier = Modifier,
    color: Color = Color.Unspecified,
    eyebrow: String? = null,
    subtitle: String? = null
) {
    Surface(
        modifier = modifier.fillMaxWidth(),
        color = color
    ) {
        Column(
            modifier = if (color.isSpecified) Modifier.padding(16.dp) else Modifier,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Column {
                if (eyebrow != null) {
                    Text(
                        text = eyebrow,
                        style = MaterialTheme.typography.labelMedium,
                        modifier = Modifier.alpha(.66f)
                    )
                }
                Text(
                    text = title,
                    style = MaterialTheme.typography.displayMedium
                )
            }
            if (subtitle != null) {
                Text(
                    text = subtitle,
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }
    }
}
