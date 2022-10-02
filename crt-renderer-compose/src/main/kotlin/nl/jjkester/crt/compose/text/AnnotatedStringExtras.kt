package nl.jjkester.crt.compose.text

import androidx.compose.foundation.text.InlineTextContent

class AnnotatedStringExtras(
    val inlineContent: Map<String, InlineTextContent> = emptyMap(),
    val clickOffsets: Collection<ClickOffset> = emptyList()
) {
    companion object {

        val Empty: AnnotatedStringExtras = AnnotatedStringExtras()

        fun inlineContent(placeholder: String, content: InlineTextContent): AnnotatedStringExtras =
            AnnotatedStringExtras(inlineContent = mapOf(placeholder to content))

        fun clickOffset(clickOffset: ClickOffset): AnnotatedStringExtras =
            AnnotatedStringExtras(clickOffsets = listOf(clickOffset))
    }
}

operator fun AnnotatedStringExtras.plus(other: AnnotatedStringExtras): AnnotatedStringExtras = AnnotatedStringExtras(
    inlineContent = inlineContent + other.inlineContent,
    clickOffsets = clickOffsets + other.clickOffsets
)

fun captureExtras(block: suspend SequenceScope<AnnotatedStringExtras>.() -> Unit): AnnotatedStringExtras =
    sequence { block() }
        .toList()
        .let { sequence ->
            AnnotatedStringExtras(
                inlineContent = sequence.flatMap { it.inlineContent.entries }.associate { it.key to it.value },
                clickOffsets = sequence.flatMap { it.clickOffsets }.toList()
            )
        }
