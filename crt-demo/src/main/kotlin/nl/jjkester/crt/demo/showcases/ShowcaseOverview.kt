package nl.jjkester.crt.demo.showcases

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import nl.jjkester.crt.demo.components.Jumbotron
import nl.jjkester.crt.demo.components.NavigationCard

@Composable
fun ShowcaseOverview(showcase: Showcase, onExampleClick: (Example) -> Unit, onEditorClick: () -> Unit) {
    LazyColumn(
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Jumbotron(
                eyebrow = "Showcase",
                title = showcase.name,
                subtitle = showcase.description
            )
            Spacer(modifier = Modifier.height(16.dp))
        }

        items(showcase.examples) { example ->
            NavigationCard(
                title = example.name,
                description = example.description,
                onClick = { onExampleClick(example) },
            )
        }

        if (showcase.editorFormat != null) {
            item("editor") {
                NavigationCard(
                    title = "Try ${showcase.name} yourself",
                    description = "Type any ${showcase.name} you want and see the rendering in action",
                    icon = Icons.Default.Edit,
                    onClick = { onEditorClick() }
                )
            }
        }
    }
}
