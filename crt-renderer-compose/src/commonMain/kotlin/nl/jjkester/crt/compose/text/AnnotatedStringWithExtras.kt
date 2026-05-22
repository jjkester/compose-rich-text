package nl.jjkester.crt.compose.text

import androidx.compose.foundation.text.InlineTextContent
import androidx.compose.ui.text.AnnotatedString

/**
 * Combination of an [AnnotatedString] and extras for it.
 *
 * @property annotatedString [AnnotatedString] for which the extras are defined.
 * @property inlineContent Replacement map of inline content for the annotated string.
 * @see AnnotatedStringExtras
 */
public class AnnotatedStringWithExtras internal constructor(
    internal val annotatedString: AnnotatedString,
    internal val inlineContent: Map<String, InlineTextContent> = emptyMap()
) {
    override fun toString(): String = annotatedString.toString()

    override fun equals(other: Any?): Boolean = when {
        this === other -> true
        other !is AnnotatedStringWithExtras -> false
        annotatedString != other.annotatedString -> false
        else -> inlineContent == other.inlineContent
    }

    override fun hashCode(): Int {
        var result = annotatedString.hashCode()
        result = 31 * result + inlineContent.hashCode()
        return result
    }
}

/**
 * Creates an [AnnotatedStringWithExtras] from an [AnnotatedString] and the provided [extras].
 */
public fun AnnotatedString.withExtras(extras: AnnotatedStringExtras): AnnotatedStringWithExtras =
    AnnotatedStringWithExtras(this, extras.inlineContent)

/**
 * Creates an [AnnotatedStringWithExtras] without extras from a regular [AnnotatedString].
 */
public fun AnnotatedString.withoutExtras(): AnnotatedStringWithExtras = AnnotatedStringWithExtras(this)
