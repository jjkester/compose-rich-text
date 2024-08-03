package nl.jjkester.crt.compose.text

/**
 * Representation of a reference to clickable text in an [androidx.compose.ui.text.AnnotatedString].
 *
 * @property target Target where the clickable text is linking to.
 * @property offset Location of the clickable text in the [androidx.compose.ui.text.AnnotatedString].
 */
public data class ClickOffset internal constructor(
    val target: String,
    val offset: IntRange
)
