package nl.jjkester.crt.compose.text

import androidx.compose.foundation.text.InlineTextContent

/**
 * Combination of extras to an [androidx.compose.ui.text.AnnotatedString].
 *
 * @property inlineContent Replacement map of inline content for the annotated string.
 * @property clickOffsets Collection of clickable texts for the annotated string.
 * @see AnnotatedStringWithExtras
 */
public class AnnotatedStringExtras internal constructor(
    internal val inlineContent: Map<String, InlineTextContent> = emptyMap(),
    internal val clickOffsets: Collection<ClickOffset> = emptyList()
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

        /**
         * Creates an instance containing just a clickable area entry.
         *
         * @param clickOffset Click offset representing the clickable area.
         * @return [AnnotatedStringExtras] with the provided clickable area.
         */
        public fun clickOffset(clickOffset: ClickOffset): AnnotatedStringExtras =
            AnnotatedStringExtras(clickOffsets = listOf(clickOffset))
    }
}

/**
 * Combines two instances of [AnnotatedStringExtras].
 */
public operator fun AnnotatedStringExtras.plus(other: AnnotatedStringExtras): AnnotatedStringExtras =
    AnnotatedStringExtras(
        inlineContent = inlineContent + other.inlineContent,
        clickOffsets = clickOffsets + other.clickOffsets
    )

/**
 * Function to ease working with [androidx.compose.ui.text.AnnotatedString] builders and capturing
 * [AnnotatedStringExtras] at the same time.
 *
 * Within [block], [SequenceScope.yield] can be called to register the extras, removing the need for manually keeping
 * a mutable collection.
 */
public fun captureExtras(block: suspend SequenceScope<AnnotatedStringExtras>.() -> Unit): AnnotatedStringExtras =
    sequence(block)
        .toList()
        .let { sequence ->
            AnnotatedStringExtras(
                inlineContent = sequence.flatMap { it.inlineContent.entries }.associate { it.key to it.value },
                clickOffsets = sequence.flatMap { it.clickOffsets }.toList()
            )
        }
