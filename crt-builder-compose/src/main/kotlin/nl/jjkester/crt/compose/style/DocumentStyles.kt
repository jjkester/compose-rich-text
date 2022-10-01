@file:OptIn(ExperimentalTextApi::class)

package nl.jjkester.crt.compose.style

import androidx.compose.foundation.BorderStroke
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import nl.jjkester.crt.common.enumeration.Enumeration
import nl.jjkester.crt.compose.builder.ComposeRichTextString

data class DocumentStyles constructor(
    val h1: TextStyle = TextStyle(
        fontWeight = FontWeight.Bold,
        fontSize = 2.em
    ),
    val h2: TextStyle = TextStyle(
        fontWeight = FontWeight.Bold,
        fontSize = 1.5.em
    ),
    val h3: TextStyle = TextStyle(
        fontWeight = FontWeight.Bold,
        fontSize = 1.25.em
    ),
    val h4: TextStyle = TextStyle(
        fontWeight = FontWeight.Bold,
        fontSize = 1.1.em
    ),
    val h5: TextStyle = TextStyle(
        fontWeight = FontWeight.Bold,
        fontSize = 1.em
    ),
    val h6: TextStyle = TextStyle(
        textDecoration = TextDecoration.Underline,
        fontSize = 1.em
    ),
    val paragraph: TextStyle = TextStyle(
        fontSize = 1.em
    ),
    val paragraphSpacing: Dp = 16.dp,
    val blockquote: TextStyle = TextStyle(
        color = Color.Gray
    ),
    val blockquoteBorder: BorderStroke = BorderStroke(2.dp, Color.Gray),
    val separatorBorder: BorderStroke = BorderStroke(Dp.Hairline, Color.Gray),
    val inset: Dp = 16.dp,
    val orderedEnumeration: Enumeration.Counting<ComposeRichTextString> = ComposeEnumerationFactory.alphanumeric,
    val unorderedEnumeration: Enumeration.Fixed<ComposeRichTextString> = ComposeEnumerationFactory.fixed
) {
    companion object {
        val Default: DocumentStyles by lazy(LazyThreadSafetyMode.NONE, ::DocumentStyles)
    }
}