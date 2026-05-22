package nl.jjkester.crt.compose

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.isSpecified
import nl.jjkester.crt.api.annotations.InternalRendererApi
import nl.jjkester.crt.compose.internal.text.LocalSpanBaseStyle
import nl.jjkester.crt.compose.style.LocalRichTextStyle
import nl.jjkester.crt.compose.style.RichTextStyle
import nl.jjkester.crt.compose.text.LinkHandler
import nl.jjkester.crt.compose.text.LocalLinkHandler

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
 * @param linkHandler Handler determining the actions for clicking on links.
 * @see rememberRichTextState
 * @see LazyRichText Renders rich text lazily, recommended for longer documents.
 */
@OptIn(InternalRendererApi::class)
@Composable
public fun RichText(
    state: RichTextState,
    modifier: Modifier = Modifier,
    richTextStyle: RichTextStyle = LocalRichTextStyle.current,
    style: TextStyle = TextStyle.Default,
    linkHandler: LinkHandler = LocalLinkHandler.current
) {
    Box(modifier = modifier) {
        CompositionLocalProvider(
            LocalSpanBaseStyle provides style,
            LocalRichTextStyle provides richTextStyle,
            LocalLinkHandler provides linkHandler
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
 * @param linkHandler Handler determining the actions for clicking on links.
 * @see RichText Renders rich text eagerly, recommended for small snippets.
 */
@OptIn(InternalRendererApi::class)
@Composable
public fun LazyRichText(
    state: RichTextState,
    modifier: Modifier = Modifier,
    richTextStyle: RichTextStyle = LocalRichTextStyle.current,
    style: TextStyle = TextStyle.Default,
    contentPadding: PaddingValues = PaddingValues(0.dp),
    linkHandler: LinkHandler = LocalLinkHandler.current
) {
    CompositionLocalProvider(
        LocalSpanBaseStyle provides style,
        LocalRichTextStyle provides richTextStyle,
        LocalLinkHandler provides linkHandler
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
