package nl.jjkester.crt.compose.test

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import nl.jjkester.crt.api.model.Link
import nl.jjkester.crt.api.model.Node
import nl.jjkester.crt.compose.RichText
import nl.jjkester.crt.compose.rememberRichTextState
import nl.jjkester.crt.compose.style.rememberBasicRichTextStyle
import nl.jjkester.crt.compose.text.LinkHandler
import nl.jjkester.crt.dsl.BlockScope
import nl.jjkester.crt.dsl.buildRichText

@Composable
fun RichTextTestCase(rootNode: Node) {
    val state = rememberRichTextState(rootNode)
    val richTextStyle = rememberBasicRichTextStyle(Color.Black, PaddingValues(8.dp))
    RichText(state = state, linkHandler = TestLinkHandler, richTextStyle = richTextStyle)
}

@Composable
fun RichTextTestCase(builder: BlockScope.() -> Unit) {
    val rootNode = remember(builder) { buildRichText(builder) }
    RichTextTestCase(rootNode)
}

private object TestLinkHandler : LinkHandler {
    override fun getAction(destination: Link.Destination): (() -> Unit)? = {}
}
