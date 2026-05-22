package nl.jjkester.crt.compose.renderer

import androidx.compose.runtime.Composable
import nl.jjkester.crt.api.annotations.InternalRendererApi
import nl.jjkester.crt.api.renderer.BlockTransformer

/**
 * Transformer for rendering block nodes into composables.
 */
@OptIn(InternalRendererApi::class)
public interface ComposableBlockTransformer : BlockTransformer<@Composable () -> Unit> {

    /**
     * Renders a container, accepting pre-rendered [children]
     *
     * @param children Pre-rendered children.
     * @return Rendered node.
     */
    public fun container(children: List<@Composable () -> Unit>): @Composable () -> Unit
}
