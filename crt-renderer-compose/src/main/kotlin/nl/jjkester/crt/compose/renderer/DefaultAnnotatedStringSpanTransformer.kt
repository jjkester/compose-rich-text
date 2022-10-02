package nl.jjkester.crt.compose.renderer

import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.withStyle
import nl.jjkester.crt.api.model.tree.Code
import nl.jjkester.crt.api.model.tree.Emphasis
import nl.jjkester.crt.api.model.tree.Link
import nl.jjkester.crt.api.model.tree.StrongEmphasis
import nl.jjkester.crt.api.model.tree.Text
import nl.jjkester.crt.compose.style.RichTextStyle
import nl.jjkester.crt.compose.text.AnnotatedStringExtras
import nl.jjkester.crt.compose.text.ClickOffset
import nl.jjkester.crt.compose.text.captureExtras
import nl.jjkester.crt.compose.text.measureOffsetRange

class DefaultAnnotatedStringSpanTransformer(
    private val richTextStyle: RichTextStyle
) : AbstractAnnotatedStringSpanTransformer() {

    override fun AnnotatedString.Builder.appendCode(node: Code): AnnotatedStringExtras {
        withStyle(richTextStyle.code) {
            append(node.content)
        }
        return AnnotatedStringExtras.Empty
    }

    override fun AnnotatedString.Builder.appendEmphasis(node: Emphasis): AnnotatedStringExtras = captureExtras {
        withStyle(richTextStyle.emphasis) {
            node.children.forEach { yield(transformAndAppend(it)) }
        }
    }

    override fun AnnotatedString.Builder.appendLink(node: Link): AnnotatedStringExtras = captureExtras {
        val offset = measureOffsetRange {
            withStyle(richTextStyle.link) {
                node.children.forEach { yield(transformAndAppend(it)) }
            }
        }

        yield(AnnotatedStringExtras.clickOffset(ClickOffset(node.destination.value, offset)))
    }

    override fun AnnotatedString.Builder.appendStrongEmphasis(node: StrongEmphasis): AnnotatedStringExtras =
        captureExtras {
            withStyle(richTextStyle.strongEmphasis) {
                node.children.forEach { yield(transformAndAppend(it)) }
            }
        }

    override fun AnnotatedString.Builder.appendText(node: Text): AnnotatedStringExtras {
        append(node.text)
        return AnnotatedStringExtras.Empty
    }
}
