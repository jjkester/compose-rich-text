package nl.jjkester.crt.compose.style

import androidx.compose.ui.text.AnnotatedString
import nl.jjkester.crt.common.enumeration.EnumerationFactory
import nl.jjkester.crt.compose.builder.ComposeRichTextString

object ComposeEnumerationFactory : EnumerationFactory<ComposeRichTextString>() {

    override fun convert(value: String): ComposeRichTextString = ComposeRichTextString(AnnotatedString(value))
}
