package nl.jjkester.crt.demo.showcases

import androidx.compose.runtime.Composable
import nl.jjkester.crt.demo.editor.EditorFormat

data class Showcase(
    val name: String,
    val description: String,
    val examples: List<Example>,
    val editorFormat: EditorFormat?
)

data class Example(
    val name: String,
    val description: String,
    val content: @Composable () -> Unit
)
