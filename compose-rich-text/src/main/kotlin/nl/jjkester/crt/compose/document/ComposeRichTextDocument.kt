package nl.jjkester.crt.compose.document

import androidx.compose.runtime.Composable
import nl.jjkester.crt.document.RichTextDocument

class ComposeRichTextDocument(
    val content: @Composable () -> Unit
) : RichTextDocument