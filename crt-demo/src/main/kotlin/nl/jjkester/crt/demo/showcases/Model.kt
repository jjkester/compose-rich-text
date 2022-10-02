package nl.jjkester.crt.demo.showcases

import androidx.compose.runtime.Composable

data class Showcase(
    val name: String,
    val description: String,
    val examples: List<Example>
)

data class Example(
    val name: String,
    val description: String,
    val content: @Composable () -> Unit
)
