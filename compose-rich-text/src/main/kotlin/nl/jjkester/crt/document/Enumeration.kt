package nl.jjkester.crt.document

import nl.jjkester.crt.text.RichTextString

sealed interface Enumeration {

    val child: Enumeration

    class Fixed<out T : RichTextString>(val string: T, child: Fixed<T>? = null) : Enumeration {
        override val child: Fixed<T> = child ?: this
    }

    class Counting<out T : RichTextString>(val sequence: Sequence<T>, child: Counting<T>? = null) : Enumeration {
        override val child: Counting<T> = child ?: this
    }

    companion object {
        fun <T : RichTextString> dashes(child: Fixed<T>? = null, transform: (Char) -> T) = Fixed(
            string = transform(Typography.mdash),
            child = child
        )

        fun <T : RichTextString> bullets(child: Fixed<T>? = null, transform: (Char) -> T) = Fixed(
            string = transform(Typography.bullet),
            child = child
        )

        fun <T : RichTextString> numbers(child: Counting<T>? = null, transform: (Int) -> T) = Counting(
            sequence = generateSequence(1) { it + 1 }.map(transform),
            child = child
        )

        fun <T : RichTextString> lowercaseLetters(child: Counting<T>? = null, transform: (String) -> T) = Counting(
            sequence = alphaSequence('a'..'z').map(transform),
            child = child
        )

        fun <T : RichTextString> uppercaseLetters(child: Counting<T>? = null, transform: (String) -> T) = Counting(
            sequence = alphaSequence('A'..'Z').map(transform),
            child = child
        )

        fun <T : RichTextString> hierarchy(final: Counting<T>? = null, transform: (String) -> T) =
            numbers(lowercaseLetters(final) { transform("$it.") }) { transform("$it.") }
    }
}

fun <T : RichTextString> Enumeration.Fixed<T>.counting(): Enumeration.Counting<T> = Enumeration.Counting(
    sequence = generateSequence { string },
    child = child.counting()
)

private fun alphaSequence(chars: Iterable<Char>, prefix: String = ""): Sequence<String> = sequence {
    yieldAll(chars.map { "$prefix$it" })
    chars.forEach {
        yieldAll(alphaSequence(chars, "$it$prefix"))
    }
}
