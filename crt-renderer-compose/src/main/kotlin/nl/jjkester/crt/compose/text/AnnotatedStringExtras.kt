package nl.jjkester.crt.compose.text

import androidx.compose.foundation.text.InlineTextContent

/**
 * Combination of extras to an [androidx.compose.ui.text.AnnotatedString].
 *
 * @property inlineContent Replacement map of inline content for the annotated string.
 * @see AnnotatedStringWithExtras
 */
public class AnnotatedStringExtras internal constructor(
    internal val inlineContent: Map<String, InlineTextContent> = emptyMap()
) {
    public companion object {

        /**
         * Instance without any extras.
         */
        public val Empty: AnnotatedStringExtras = AnnotatedStringExtras()

        /**
         * Creates an instance containing just an inline content replacement entry to replace the [placeholder] with the
         * [content].
         *
         * @param placeholder Placeholder to replace.
         * @param content Content to replace the [placeholder] with.
         * @return [AnnotatedStringExtras] with the provided replacement.
         */
        public fun inlineContent(placeholder: String, content: InlineTextContent): AnnotatedStringExtras =
            AnnotatedStringExtras(inlineContent = mapOf(placeholder to content))
    }
}

/**
 * Combines two instances of [AnnotatedStringExtras].
 */
public operator fun AnnotatedStringExtras.plus(other: AnnotatedStringExtras): AnnotatedStringExtras =
    AnnotatedStringExtras(inlineContent = inlineContent + other.inlineContent)

/**
 * Function to ease working with [androidx.compose.ui.text.AnnotatedString] builders and capturing
 * [AnnotatedStringExtras] at the same time.
 *
 * Within [block], [SequenceScope.yield] can be called to register the extras, removing the need for manually keeping
 * a mutable collection.
 */
public fun captureExtras(block: suspend SequenceScope<AnnotatedStringExtras>.() -> Unit): AnnotatedStringExtras =
    sequence(block)
        .filter { it != AnnotatedStringExtras.Empty }
        .toList()
        .takeIf { it.isNotEmpty() }
        ?.let { sequence ->
            AnnotatedStringExtras(
                inlineContent = sequence.flatMap { it.inlineContent.entries }.associate { it.key to it.value }
            )
        }
        ?: AnnotatedStringExtras.Empty
