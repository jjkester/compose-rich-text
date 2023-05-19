package nl.jjkester.crt.compose

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.isSpecified
import nl.jjkester.crt.api.model.Container
import nl.jjkester.crt.api.parser.Parser
import nl.jjkester.crt.compose.internal.text.LocalSpanBaseStyle
import nl.jjkester.crt.compose.internal.text.LocalSpanClickHandler
import nl.jjkester.crt.compose.renderer.ComposableBlockTransformer
import nl.jjkester.crt.compose.renderer.ComposeRenderer
import nl.jjkester.crt.compose.renderer.DefaultAnnotatedStringSpanTransformer
import nl.jjkester.crt.compose.renderer.DefaultComposableBlockTransformer
import nl.jjkester.crt.compose.style.LocalRichTextStyle
import nl.jjkester.crt.compose.style.RichTextStyle

@Composable
fun RichText(
    richText: ParsedRichText,
    modifier: Modifier = Modifier,
    richTextStyle: RichTextStyle = LocalRichTextStyle.current,
    style: TextStyle = TextStyle.Default,
    renderer: ComposeRenderer = rememberRichTextRenderer(),
    onClick: (String) -> Unit = LocalUriHandler.current::openUri
) {
    Box(modifier = modifier) {
        CompositionLocalProvider(
            LocalSpanBaseStyle provides style,
            LocalSpanClickHandler provides onClick,
            LocalRichTextStyle provides richTextStyle,
            content = remember(richText, renderer) { renderer.render(richText.node) }
        )
    }
}

@Composable
fun LazyRichText(
    richText: ParsedRichText,
    modifier: Modifier = Modifier,
    richTextStyle: RichTextStyle = LocalRichTextStyle.current,
    style: TextStyle = TextStyle.Default,
    contentPadding: PaddingValues = PaddingValues(0.dp),
    renderer: ComposeRenderer = rememberRichTextRenderer(),
    onClick: (String) -> Unit = LocalUriHandler.current::openUri
) {
    CompositionLocalProvider(
        LocalSpanBaseStyle provides style,
        LocalSpanClickHandler provides onClick,
        LocalRichTextStyle provides richTextStyle
    ) {
        val verticalArrangement = Arrangement.spacedBy(richTextStyle.blockSpacing)
            .takeIf { it.spacing.isSpecified }
            ?: Arrangement.Top

        LazyColumn(
            modifier = modifier,
            verticalArrangement = verticalArrangement,
            contentPadding = contentPadding
        ) {
            val root = richText.node

            if (root is Container) {
                items(root.children) { renderer.render(it)() }
            } else {
                item { renderer.render(root)() }
            }
        }
    }
}

@Composable
fun rememberRichTextRenderer(): ComposeRenderer = rememberRichTextRenderer {
    DefaultComposableBlockTransformer {
        DefaultAnnotatedStringSpanTransformer(LocalRichTextStyle.current)
    }
}

@Composable
fun rememberRichTextRenderer(transformerFactory: () -> ComposableBlockTransformer): ComposeRenderer =
    remember(transformerFactory) {
        ComposeRenderer(transformerFactory())
    }

@Composable
fun rememberParsedRichText(richText: String, parserFactory: () -> Parser<*>): ParsedRichText {
    val parser = remember(parserFactory) { parserFactory() }
    return remember(richText, parser) { ParsedRichText(parser.parse(richText).rootNode) }
}
