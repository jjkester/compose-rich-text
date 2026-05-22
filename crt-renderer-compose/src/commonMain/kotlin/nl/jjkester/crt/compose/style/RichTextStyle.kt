package nl.jjkester.crt.compose.style

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.runtime.Stable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextLinkStyles
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import nl.jjkester.crt.common.enumeration.Enumeration
import nl.jjkester.crt.compose.text.AnnotatedStringWithExtras

/**
 * Style for rendered rich text. By default, no styling is applied.
 *
 * @property h1 Text style for headings of level 1.
 * @property h2 Text style for headings of level 2.
 * @property h3 Text style for headings of level 3.
 * @property h4 Text style for headings of level 4.
 * @property h5 Text style for headings of level 5.
 * @property h6 Text style for headings of level 6.
 * @property paragraph Text style for paragraphs.
 * @property blockquote Style for blockquotes.
 * @property enumeration Style for enumerations.
 * @property divider Optional border stroke for dividers.
 * @property blockSpacing Vertical spacing between blocks.
 * @property blockInset Horizontal spacing at the start of indented blocks. Spacing will stack with nested indents.
 * @property emphasis Span style for emphasis.
 * @property strongEmphasis Span style for strong emphasis.
 * @property code Span style for code.
 * @property link Style for links.
 * @see rememberBasicRichTextStyle
 */
@Immutable
public data class RichTextStyle(
    val h1: TextStyle = TextStyle.Default,
    val h2: TextStyle = TextStyle.Default,
    val h3: TextStyle = TextStyle.Default,
    val h4: TextStyle = TextStyle.Default,
    val h5: TextStyle = TextStyle.Default,
    val h6: TextStyle = TextStyle.Default,
    val paragraph: TextStyle = TextStyle.Default,
    val blockquote: BlockquoteStyle = BlockquoteStyle.Default,
    val enumeration: EnumerationStyle = EnumerationStyle.Default,
    val divider: BorderStroke? = null,
    val blockSpacing: Dp = Dp.Unspecified,
    val blockInset: Dp = Dp.Unspecified,
    val emphasis: SpanStyle = TextStyle.Default.toSpanStyle(),
    val strongEmphasis: SpanStyle = TextStyle.Default.toSpanStyle(),
    val code: SpanStyle = TextStyle.Default.toSpanStyle(),
    val link: TextLinkStyles = TextLinkStyles()
) {
    public companion object {
        /**
         * Default rich text style that does not apply any styling.
         */
        @Stable
        public val Default: RichTextStyle = RichTextStyle()
    }
}

/**
 * Style for blockquotes.
 *
 * @property text Text style for blockquotes.
 * @property border Border stroke for the start of blockquotes.
 */
@Immutable
public data class BlockquoteStyle(
    val text: TextStyle = TextStyle.Default,
    val border: BorderStroke? = null
) {
    public companion object {
        /**
         * Default blockquote style that does not apply any styling.
         */
        @Stable
        public val Default: BlockquoteStyle = BlockquoteStyle()
    }
}

/**
 * Style for enumerations.
 *
 * @property ordered Enumeration sequence for ordered lists.
 * @property unordered Enumeration sequence for unordered lists.
 */
@Immutable
public data class EnumerationStyle(
    val ordered: Enumeration.Counting<AnnotatedStringWithExtras> = ComposeEnumerationFactory.alphanumeric,
    val unordered: Enumeration.Fixed<AnnotatedStringWithExtras> = ComposeEnumerationFactory.bulleted
) {
    public companion object {
        /**
         * Default enumeration style that does not apply any styling.
         */
        @Stable
        public val Default: EnumerationStyle = EnumerationStyle()
    }
}

/**
 * Composition local for providing the rich text style for a composition.
 */
public val LocalRichTextStyle: ProvidableCompositionLocal<RichTextStyle> = compositionLocalOf { RichTextStyle.Default }

/**
 * Creates and remembers a basic [RichTextStyle] on top of the base [TextStyle]. Text sizes have been defined relative
 * to the text size of the base text style. Spacing, mot notably [RichTextStyle.blockSpacing] and
 * [RichTextStyle.blockInset], is set based on the provided [spacing] values. The end value from the provided [spacing]
 * is ignored.
 *
 * @param color The base color to be used for text.
 * @param spacing Spacing values for the rich text style.
 * @return Rich text style using relative font sizes and the provided [spacing].
 */
@Composable
public fun rememberBasicRichTextStyle(color: Color, spacing: PaddingValues): RichTextStyle {
    val layoutDirection = LocalLayoutDirection.current
    return remember(layoutDirection, color, spacing) {
        RichTextStyle(
            h1 = TextStyle(
                fontWeight = FontWeight.Bold,
                fontSize = 2.em,
                color = color
            ),
            h2 = TextStyle(
                fontWeight = FontWeight.Bold,
                fontSize = 1.5.em,
                color = color
            ),
            h3 = TextStyle(
                fontWeight = FontWeight.Bold,
                fontSize = 1.25.em,
                color = color
            ),
            h4 = TextStyle(
                fontWeight = FontWeight.Bold,
                fontSize = 1.1.em,
                color = color
            ),
            h5 = TextStyle(
                fontWeight = FontWeight.Bold,
                fontSize = 1.em,
                color = color
            ),
            h6 = TextStyle(
                textDecoration = TextDecoration.Underline,
                fontSize = 1.em,
                color = color
            ),
            paragraph = TextStyle(
                fontSize = 1.em,
                color = color
            ),
            blockquote = BlockquoteStyle(
                text = TextStyle(color = color.run { copy(alpha = alpha * .70f) }),
                border = BorderStroke(2.dp, color.run { copy(alpha = alpha * .70f) }),
            ),
            enumeration = EnumerationStyle(
                ordered = ComposeEnumerationFactory.alphanumeric,
                unordered = ComposeEnumerationFactory.bulleted
            ),
            blockSpacing = maxOf(spacing.calculateBottomPadding(), spacing.calculateTopPadding()),
            blockInset = spacing.calculateStartPadding(layoutDirection),
            emphasis = SpanStyle(fontStyle = FontStyle.Italic),
            strongEmphasis = SpanStyle(fontWeight = FontWeight.Bold),
            code = SpanStyle(fontFamily = FontFamily.Monospace, color = color),
            link = TextLinkStyles(
                style = SpanStyle(color = color, textDecoration = TextDecoration.Underline),
                focusedStyle = SpanStyle(color = color.copy(alpha = .70f)),
                hoveredStyle = SpanStyle(color = color.copy(alpha = .70f)),
            )
        )
    }
}
