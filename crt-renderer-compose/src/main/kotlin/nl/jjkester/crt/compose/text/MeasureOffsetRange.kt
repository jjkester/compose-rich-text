package nl.jjkester.crt.compose.text

import androidx.compose.ui.text.AnnotatedString

inline fun AnnotatedString.Builder.measureOffsetRange(block: AnnotatedString.Builder.() -> Unit): IntRange {
    val start = length
    block()
    val end = length

    return start until end
}
