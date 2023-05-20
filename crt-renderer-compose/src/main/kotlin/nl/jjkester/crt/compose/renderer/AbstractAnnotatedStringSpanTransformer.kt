package nl.jjkester.crt.compose.renderer

import androidx.compose.ui.text.AnnotatedString
import nl.jjkester.crt.api.annotations.InternalRendererApi
import nl.jjkester.crt.api.model.Code
import nl.jjkester.crt.api.model.Emphasis
import nl.jjkester.crt.api.model.Link
import nl.jjkester.crt.api.model.Node
import nl.jjkester.crt.api.model.StrongEmphasis
import nl.jjkester.crt.api.model.Text
import nl.jjkester.crt.compose.text.AnnotatedStringExtras
import nl.jjkester.crt.compose.text.AnnotatedStringWithExtras
import nl.jjkester.crt.compose.text.captureExtras
import nl.jjkester.crt.compose.text.withoutExtras

/**
 * Transformer for rendering span nodes into annotated strings with extras that abstracts the heavy lifting around
 * annotated string builders. Implementors of this class have to implement relatively simple append functions taking
 * in a node, and can call [transformAndAppend] for any child node that needs to be rendered.
 */
abstract class AbstractAnnotatedStringSpanTransformer : AnnotatedStringSpanTransformer {

    @InternalRendererApi
    final override fun code(node: Code): AnnotatedStringWithExtras = buildAnnotatedStringWithExtras {
        appendCode(node)
    }

    @InternalRendererApi
    final override fun emphasis(node: Emphasis): AnnotatedStringWithExtras = buildAnnotatedStringWithExtras {
        appendEmphasis(node)
    }

    @InternalRendererApi
    final override fun link(node: Link): AnnotatedStringWithExtras = buildAnnotatedStringWithExtras {
        appendLink(node)
    }

    @InternalRendererApi
    final override fun strongEmphasis(node: StrongEmphasis): AnnotatedStringWithExtras =
        buildAnnotatedStringWithExtras {
            appendStrongEmphasis(node)
        }

    @InternalRendererApi
    final override fun text(node: Text): AnnotatedStringWithExtras = AnnotatedString(node.text).withoutExtras()

    @InternalRendererApi
    final override fun transformAll(nodes: List<Node.Span>): AnnotatedStringWithExtras =
        buildAnnotatedStringWithExtras {
            captureExtras {
                nodes.forEach { yield(transformAndAppend(it)) }
            }
        }

    /**
     * Appends the content of the provided code node to the builder.
     *
     * @param node Node to append.
     * @return Annotated string extras resulting from rendering the [node] and its children.
     */
    protected abstract fun AnnotatedString.Builder.appendCode(node: Code): AnnotatedStringExtras

    /**
     * Appends the content of the provided emphasis node to the builder.
     *
     * @param node Node to append.
     * @return Annotated string extras resulting from rendering the [node] and its children.
     */
    protected abstract fun AnnotatedString.Builder.appendEmphasis(node: Emphasis): AnnotatedStringExtras

    /**
     * Appends the content of the provided link node to the builder.
     *
     * @param node Node to append.
     * @return Annotated string extras resulting from rendering the [node] and its children.
     */
    protected abstract fun AnnotatedString.Builder.appendLink(node: Link): AnnotatedStringExtras

    /**
     * Appends the content of the provided strong emphasis node to the builder.
     *
     * @param node Node to append.
     * @return Annotated string extras resulting from rendering the [node] and its children.
     */
    protected abstract fun AnnotatedString.Builder.appendStrongEmphasis(node: StrongEmphasis): AnnotatedStringExtras

    /**
     * Appends the content of the provided text node to the builder.
     *
     * @param node Node to append.
     * @return Annotated string extras resulting from rendering the [node].
     */
    protected abstract fun AnnotatedString.Builder.appendText(node: Text): AnnotatedStringExtras

    /**
     * Transforms the provided [node] by calling the appropriate appender.
     *
     * @param node Node to render.
     * @return Annotated string extras resulting from rendering the [node] and its children.
     */
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
