package nl.jjkester.crt.compose.builder

import androidx.compose.runtime.Composable
import nl.jjkester.crt.api.document.RichTextDocument

class ComposeRichTextDocument(
    internal val content: @Composable () -> Unit
) : RichTextDocument
