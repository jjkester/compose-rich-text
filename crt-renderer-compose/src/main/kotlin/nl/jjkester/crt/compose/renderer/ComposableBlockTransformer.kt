package nl.jjkester.crt.compose.renderer

import androidx.compose.runtime.Composable
import nl.jjkester.crt.api.model.Container
import nl.jjkester.crt.api.renderer.BlockTransformer

interface ComposableBlockTransformer : BlockTransformer<@Composable () -> Unit> {

    fun container(children: List<@Composable () -> Unit>): @Composable () -> Unit
}
