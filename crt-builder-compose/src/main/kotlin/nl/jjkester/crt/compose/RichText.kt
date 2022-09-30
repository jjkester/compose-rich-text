package nl.jjkester.crt.compose

import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import nl.jjkester.crt.api.builder.DocumentBuilder
import nl.jjkester.crt.api.renderer.DocumentRenderer
import nl.jjkester.crt.compose.builder.AnnotatedStringTextBuilder
import nl.jjkester.crt.compose.builder.ComposableDocumentBuilder
import nl.jjkester.crt.compose.builder.ComposeRichTextDocument
import nl.jjkester.crt.compose.builder.ComposeRichTextString
import nl.jjkester.crt.compose.internal.text.LocalSpanBaseStyle
import nl.jjkester.crt.compose.internal.text.LocalSpanClickHandler
import nl.jjkester.crt.compose.style.DocumentStyles
import nl.jjkester.crt.compose.style.TextStyles

@Composable
fun RichText(
    string: String,
    renderer: DocumentRenderer<ComposeRichTextDocument>,
    modifier: Modifier = Modifier,
    style: TextStyle = TextStyle.Default,
    onClick: (String) -> Unit = {}
) {
    Box(modifier = modifier) {
        CompositionLocalProvider(
            LocalSpanBaseStyle provides style,
            LocalSpanClickHandler provides onClick,
            content = remember(string, renderer) { renderer.render(string) }.content
        )
    }
}

@Composable
fun rememberDocumentRenderer(
    documentStyles: DocumentStyles = DocumentStyles.Default,
    textStyles: TextStyles = TextStyles.Default,
    factory: (DocumentBuilder<ComposeRichTextDocument, ComposeRichTextString>) -> DocumentRenderer<ComposeRichTextDocument>
): DocumentRenderer<ComposeRichTextDocument> = remember(documentStyles, textStyles) {
    factory(ComposableDocumentBuilder(documentStyles) { AnnotatedStringTextBuilder(textStyles) })
}
