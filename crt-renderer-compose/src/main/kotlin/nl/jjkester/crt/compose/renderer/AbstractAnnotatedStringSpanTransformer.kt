package nl.jjkester.crt.compose.renderer

import androidx.compose.ui.text.AnnotatedString
import nl.jjkester.crt.api.model.tree.Code
import nl.jjkester.crt.api.model.tree.Emphasis
import nl.jjkester.crt.api.model.tree.Link
import nl.jjkester.crt.api.model.tree.Node
import nl.jjkester.crt.api.model.tree.StrongEmphasis
import nl.jjkester.crt.api.model.tree.Text
import nl.jjkester.crt.compose.text.AnnotatedStringExtras
import nl.jjkester.crt.compose.text.AnnotatedStringWithExtras
import nl.jjkester.crt.compose.text.captureExtras
import nl.jjkester.crt.compose.text.withoutExtras

abstract class AbstractAnnotatedStringSpanTransformer : AnnotatedStringSpanTransformer {

    final override fun code(node: Code): AnnotatedStringWithExtras = buildAnnotatedStringWithExtras {
        appendCode(node)
    }

    final override fun emphasis(node: Emphasis): AnnotatedStringWithExtras = buildAnnotatedStringWithExtras {
        appendEmphasis(node)
    }

    final override fun link(node: Link): AnnotatedStringWithExtras = buildAnnotatedStringWithExtras {
        appendLink(node)
    }

    final override fun strongEmphasis(node: StrongEmphasis): AnnotatedStringWithExtras =
        buildAnnotatedStringWithExtras {
            appendStrongEmphasis(node)
        }

    final override fun text(node: Text): AnnotatedStringWithExtras = AnnotatedString(node.text).withoutExtras()

    final override fun transformAll(nodes: List<Node.Span>): AnnotatedStringWithExtras =
        buildAnnotatedStringWithExtras {
            captureExtras {
                nodes.forEach { yield(transformAndAppend(it)) }
            }
        }

    protected abstract fun AnnotatedString.Builder.appendCode(node: Code): AnnotatedStringExtras

    protected abstract fun AnnotatedString.Builder.appendEmphasis(node: Emphasis): AnnotatedStringExtras

    protected abstract fun AnnotatedString.Builder.appendLink(node: Link): AnnotatedStringExtras

    protected abstract fun AnnotatedString.Builder.appendStrongEmphasis(node: StrongEmphasis): AnnotatedStringExtras

    protected abstract fun AnnotatedString.Builder.appendText(node: Text): AnnotatedStringExtras

    protected fun AnnotatedString.Builder.transformAndAppend(node: Node.Span): AnnotatedStringExtras = when (node) {
        is Code -> appendCode(node)
        is Emphasis -> appendEmphasis(node)
        is Link -> appendLink(node)
        is StrongEmphasis -> appendStrongEmphasis(node)
        is Text -> appendText(node)
    }

    private inline fun buildAnnotatedStringWithExtras(block: AnnotatedString.Builder.() -> AnnotatedStringExtras): AnnotatedStringWithExtras {
        val builder = AnnotatedString.Builder()
        val extras = builder.block()
        return AnnotatedStringWithExtras(
            annotatedString = builder.toAnnotatedString(),
            inlineContent = extras.inlineContent,
            clickOffsets = extras.clickOffsets
        )
    }
}
