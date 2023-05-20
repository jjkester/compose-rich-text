package nl.jjkester.crt.compose.text

import androidx.compose.foundation.text.InlineTextContent
import androidx.compose.ui.text.AnnotatedString

/**
 * Combination of an [AnnotatedString] and extras for it.
 *
 * @property annotatedString [AnnotatedString] for which the extras are defined.
 * @property inlineContent Replacement map of inline content for the annotated string.
 * @property clickOffsets Collection of clickable texts for the annotated string.
 * @see AnnotatedStringExtras
 */
class AnnotatedStringWithExtras(
    val annotatedString: AnnotatedString,
    val inlineContent: Map<String, InlineTextContent> = emptyMap(),
    val clickOffsets: Collection<ClickOffset> = emptyList()
) {
    override fun toString() = annotatedString.toString()

    override fun equals(other: Any?): Boolean = when {
        this === other -> true
        other !is AnnotatedStringWithExtras -> false
        annotatedString != other.annotatedString -> false
        inlineContent != other.inlineContent -> false
        else -> clickOffsets == other.clickOffsets
    }

    override fun hashCode(): Int {
        var result = annotatedString.hashCode()
        result = 31 * result + inlineContent.hashCode()
        result = 31 * result + clickOffsets.hashCode()
        return result
    }
}

/**
 * Creates an [AnnotatedStringWithExtras] without extras from a regular [AnnotatedString].
 */
fun AnnotatedString.withoutExtras(): AnnotatedStringWithExtras = AnnotatedStringWithExtras(this)
