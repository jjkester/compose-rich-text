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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.isSpecified
import nl.jjkester.crt.api.annotations.InternalRendererApi
import nl.jjkester.crt.compose.internal.text.LocalSpanBaseStyle
import nl.jjkester.crt.compose.internal.text.LocalSpanClickHandler
import nl.jjkester.crt.compose.renderer.ComposableBlockTransformer
import nl.jjkester.crt.compose.renderer.ComposeRenderer
import nl.jjkester.crt.compose.renderer.DefaultAnnotatedStringSpanTransformer
import nl.jjkester.crt.compose.renderer.DefaultComposableBlockTransformer
import nl.jjkester.crt.compose.style.LocalRichTextStyle
import nl.jjkester.crt.compose.style.RichTextStyle

/**
 * Composable for rendering rich text. The content is provided by the [state]. A [RichTextState] instance can be
 * remembered and populated with content by calling [rememberRichTextState].
 *
 * The basic style of the text can be configured by providing a base [TextStyle] through the [style] parameter. More
 * fine-grained styling can be set by providing a custom [RichTextStyle] via either the [richTextStyle] parameter or by
 * providing a value to [LocalRichTextStyle].
 *
 * When a link in the text is clicked, the [onClick] function will be called with the link destination as the parameter.
 *
 * @param state Rich text state holding the parsed text.
 * @param modifier Modifier for this component.
 * @param richTextStyle Styling for the rich text inside the component.
 * @param style Base text style. The [richTextStyle] will inherit from this style.
 * @param onClick Function that is called whenever a link is clicked. The link's URI is passed as a parameter.
 * @see rememberRichTextState
 * @see LazyRichText Renders rich text lazily, recommended for longer documents.
 */
@OptIn(InternalRendererApi::class)
@Composable
fun RichText(
    state: RichTextState,
    modifier: Modifier = Modifier,
    richTextStyle: RichTextStyle = LocalRichTextStyle.current,
    style: TextStyle = TextStyle.Default,
    onClick: (uri: String) -> Unit = {}
) {
    Box(modifier = modifier) {
        CompositionLocalProvider(
            LocalSpanBaseStyle provides style,
            LocalSpanClickHandler provides onClick,
            LocalRichTextStyle provides richTextStyle
        ) {
            state.result?.single?.invoke()
        }
    }
}

/**
 *
 * @param state Rich text state holding the parsed text.
 * @param modifier Modifier for this component.
 * @param richTextStyle Styling for the rich text inside the component.
 * @param style Base text style. The [richTextStyle] will inherit from this style.
 * @param contentPadding Padding around the whole content. Avoids visible padding at the top and bottom when the content
 * is not scrolled all the way to the edge.
 * @param onClick Function that is called whenever a link is clicked. The link's URI is passed as a parameter.
 * @see RichText Renders rich text eagerly, recommended for small snippets.
 */
@OptIn(InternalRendererApi::class)
@Composable
fun LazyRichText(
    state: RichTextState,
    modifier: Modifier = Modifier,
    richTextStyle: RichTextStyle = LocalRichTextStyle.current,
    style: TextStyle = TextStyle.Default,
    contentPadding: PaddingValues = PaddingValues(0.dp),
    onClick: (uri: String) -> Unit = {}
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
            state.result?.also { result -> items(result.lazy) { it() } }
        }
    }
}
