package nl.jjkester.crt.compose.text

import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.withStyle
import nl.jjkester.crt.text.TextBuilder

class AnnotatedStringTextBuilder(
    private val textStyles: TextStyles
) : TextBuilder<ComposeRichTextString> {

    private val inner = AnnotatedString.Builder()
    private val clickOffsets = mutableListOf<ClickOffset>()

    override fun append(text: String) {
        inner.append(text)
    }

    override fun emphasis(content: TextBuilder<ComposeRichTextString>.() -> Unit) {
        inner.withStyle(textStyles.emphasis) {
            content()
        }
    }

    override fun strongEmphasis(content: TextBuilder<ComposeRichTextString>.() -> Unit) {
        inner.withStyle(textStyles.strongEmphasis) {
            content()
        }
    }

    override fun code(content: TextBuilder<ComposeRichTextString>.() -> Unit) {
        inner.withStyle(textStyles.code) {
            content()
        }
    }

    override fun link(id: String, content: TextBuilder<ComposeRichTextString>.() -> Unit) {
        val range = measureOffsetRange {
            inner.withStyle(textStyles.link) {
                content()
            }
        }

        clickOffsets.add(ClickOffset(id, range))
    }

    override fun build(): ComposeRichTextString {
        return ComposeRichTextString(
            annotatedString = inner.toAnnotatedString(),
            inlineContent = emptyMap(),
            clickOffsets = clickOffsets
        )
    }

    private inline fun measureOffsetRange(block: () -> Unit): IntRange {
        val start = inner.length
        block()
        val end = inner.length

        return start until end
    }
}