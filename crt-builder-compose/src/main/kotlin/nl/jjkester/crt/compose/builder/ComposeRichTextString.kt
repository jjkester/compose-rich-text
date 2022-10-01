package nl.jjkester.crt.compose.builder

import androidx.compose.foundation.text.InlineTextContent
import androidx.compose.ui.text.AnnotatedString
import nl.jjkester.crt.api.text.RichTextString
import nl.jjkester.crt.compose.internal.text.ClickOffset

class ComposeRichTextString(
    internal val annotatedString: AnnotatedString,
    internal val inlineContent: Map<String, InlineTextContent> = emptyMap(),
    internal val clickOffsets: Collection<ClickOffset> = emptyList()
) : RichTextString {
    override fun toString() = annotatedString.toString()

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is ComposeRichTextString) return false
        if (annotatedString != other.annotatedString) return false
        if (inlineContent != other.inlineContent) return false
        if (clickOffsets != other.clickOffsets) return false
        return true
    }

    override fun hashCode(): Int {
        var result = annotatedString.hashCode()
        result = 31 * result + inlineContent.hashCode()
        result = 31 * result + clickOffsets.hashCode()
        return result
    }
}