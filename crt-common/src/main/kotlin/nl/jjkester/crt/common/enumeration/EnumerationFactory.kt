package nl.jjkester.crt.common.enumeration

import nl.jjkester.crt.api.text.RichTextString

public abstract class EnumerationFactory<T : RichTextString> {

    public val fixed: Enumeration.Fixed<T> by lazy(LazyThreadSafetyMode.NONE) {
        fixed(bullet, circle, square)
    }

    public val alphanumeric: Enumeration.Counting<T> by lazy(LazyThreadSafetyMode.NONE) {
        numbers(lowercaseLetters(bullets().asCounting()))
    }

    protected abstract fun convert(value: String): T

    @JvmOverloads
    public fun fixed(value: String, child: Enumeration.Fixed<T>? = null): Enumeration.Fixed<T> =
        StaticEnumeration({ convert(value) }, child)

    public fun fixed(firstValue: String, vararg otherValues: String): Enumeration.Fixed<T> =
        fixed(firstValue, otherValues.foldRight(null as Enumeration.Fixed<T>?, this::fixed))

    @JvmOverloads
    public fun counting(sequence: Sequence<String>, child: Enumeration.Counting<T>? = null): Enumeration.Counting<T> =
        SequenceEnumeration(sequence.map(this::convert), child)

    @JvmOverloads
    public fun bullets(child: Enumeration.Fixed<T>? = null): Enumeration.Fixed<T> = fixed(bullet, child)

    @JvmOverloads
    public fun dashes(child: Enumeration.Fixed<T>? = null): Enumeration.Fixed<T> = fixed(dash, child)

    @JvmOverloads
    public fun numbers(child: Enumeration.Counting<T>? = null): Enumeration.Counting<T> = counting(integers, child)

    @JvmOverloads
    public fun lowercaseLetters(child: Enumeration.Counting<T>? = null): Enumeration.Counting<T> =
        counting(lowercaseAlphabet, child)

    @JvmOverloads
    public fun uppercaseLetters(child: Enumeration.Counting<T>? = null): Enumeration.Counting<T> =
        counting(uppercaseAlphabet, child)

    private fun Enumeration.Fixed<T>.asCounting(): Enumeration.Counting<T> {
        val sequence = generateSequence(value) { it }
        return SequenceEnumeration(sequence, child.takeIf { it != this }?.asCounting())
    }

    private companion object {
        private const val bullet = "\u2022"
        private const val circle = "\u25E6"
        private const val square = "\u25A0"
        private const val dash = "\u2013"

        private val integers: Sequence<String> = generateSequence(1u, UInt::inc).map(UInt::toString)
        private val lowercaseAlphabet: Sequence<String> = repeatCombining('a'..'z')
        private val uppercaseAlphabet: Sequence<String> = repeatCombining('A'..'Z')

        private fun repeatCombining(iterable: Iterable<Any>, prefix: String = ""): Sequence<String> = sequence {
            yieldAll(iterable.map { "$prefix$it" })

            iterable.forEach {
                yieldAll(repeatCombining(iterable,"$it$prefix"))
            }
        }
    }
}
