package nl.jjkester.crt.compose.renderer

import androidx.compose.runtime.Composable
import nl.jjkester.crt.api.annotations.InternalRendererApi
import nl.jjkester.crt.api.renderer.BlockTransformer

@OptIn(InternalRendererApi::class)
interface ComposableBlockTransformer : BlockTransformer<@Composable () -> Unit> {

    fun container(children: List<@Composable () -> Unit>): @Composable () -> Unit
}
