package nl.jjkester.crt.common.enumeration

import nl.jjkester.crt.api.annotations.InternalRendererApi
import kotlin.jvm.JvmOverloads

/**
 * Factory for enumeration sequence definitions.
 *
 * @param T Type of enumerated values.
 */
@OptIn(InternalRendererApi::class)
public abstract class EnumerationFactory<T : Any> {

    /**
     * Fixed enumeration with bullets, circles and squares.
     */
    public val bulleted: Enumeration.Fixed<T> by lazy(LazyThreadSafetyMode.NONE) {
        fixed(bullet.space(), circle.space(), square.space())
    }

    /**
     * Counting enumeration with numbers, letters, and roman numerals.
     */
    public val alphanumeric: Enumeration.Counting<T> by lazy(LazyThreadSafetyMode.NONE) {
        counting(integers.dots(), lowercaseAlphabet.dots(), lowercaseRomanNumerals.dots())
    }

    /**
     * Converts a string into the type of enumerated value.
     *
     * This function is called for the predefined enumerations and some builder functions.
     *
     * @param value String value to convert.
     * @return Converted string value.
     */
    @InternalRendererApi
    protected abstract fun convert(value: String): T

    /**
     * Creates a fixed enumeration sequence with the provided value and optional child enumeration.
     *
     * @param value String value for the fixed enumeration.
     * @param child Enumeration for the next nested level.
     * @return Fixed enumeration with the provided string value and child.
     */
    @JvmOverloads
    public fun fixed(value: String, child: Enumeration<T>? = null): Enumeration.Fixed<T> =
        StaticEnumeration({ convert(value) }, child)

    /**
     * Creates a fixed enumeration sequence with the provided values. Each value represents another (nested) level.
     *
     * @param firstValue String value for the fixed enumeration of the first level.
     * @param otherValues String values for the fixed enumerations of subsequent nested levels.
     * @return Fixed enumeration with the provided string values.
     */
    public fun fixed(firstValue: String, vararg otherValues: String): Enumeration.Fixed<T> =
        fixed(firstValue, otherValues.foldRight(null as Enumeration.Fixed<T>?, this::fixed))

    /**
     * Creates a counting enumeration sequence with the provided sequence of values and optional child enumeration.
     *
     * @param sequence String values for the counting enumeration.
     * @param child Enumeration for the next nested level.
     * @return Counting enumeration with the provided string values and child.
     */
    @JvmOverloads
    public fun counting(sequence: Sequence<String>, child: Enumeration<T>? = null): Enumeration.Counting<T> =
        SequenceEnumeration(sequence.map(this::convert), child)

    /**
     * Creates a counting enumeration sequence with the provided sequences. Each sequence represents another (nested)
     * level.
     *
     * @param firstSequence String values for the counting enumeration of the first level.
     * @param otherSequences String values for the counting enumerations of subsequent nested levels.
     * @return Counting enumeration with the provided string values.
     */
    public fun counting(
        firstSequence: Sequence<String>,
        vararg otherSequences: Sequence<String>
    ): Enumeration.Counting<T> =
        counting(firstSequence, otherSequences.foldRight(null as Enumeration<T>?, this::counting))

    /**
     * Creates a fixed enumeration of bullets with the provided child enumeration.
     *
     * @param child Enumeration for the next nested level.
     * @param space Whether to append additional spacing.
     * @return Fixed enumeration with bullets.
     */
    @JvmOverloads
    public fun bullets(child: Enumeration<T>? = null, space: Boolean = true): Enumeration<T> =
        fixed(bullet.space(space), child)

    /**
     * Creates a fixed enumeration of dashes with the provided child enumeration.
     *
     * @param child Enumeration for the next nested level.
     * @param space Whether to append additional spacing.
     * @return Fixed enumeration with dashes.
     */
    @JvmOverloads
    public fun dashes(child: Enumeration<T>? = null, space: Boolean = true): Enumeration.Fixed<T> =
        fixed(dash.space(space), child)

    /**
     * Creates a counting enumeration of numbers with the provided child enumeration.
     *
     * @param child Enumeration for the next nested level.
     * @param dots Whether to append dots.
     * @return Counting enumeration with numbers.
     */
    @JvmOverloads
    public fun numbers(child: Enumeration<T>? = null, dots: Boolean = true): Enumeration.Counting<T> =
        counting(integers.dots(dots), child)

    /**
     * Creates a counting enumeration of lowercase letters with the provided child enumeration.
     *
     * @param child Enumeration for the next nested level.
     * @param dots Whether to append dots.
     * @return Counting enumeration with lowercase letters.
     */
    @JvmOverloads
    public fun lowercaseLetters(child: Enumeration<T>? = null, dots: Boolean = true): Enumeration.Counting<T> =
        counting(lowercaseAlphabet.dots(dots), child)

    /**
     * Creates a counting enumeration of uppercase letters with the provided child enumeration.
     *
     * @param child Enumeration for the next nested level.
     * @param dots Whether to append dots.
     * @return Counting enumeration with uppercase letters.
     */
    @JvmOverloads
    public fun uppercaseLetters(child: Enumeration<T>? = null, dots: Boolean = true): Enumeration.Counting<T> =
        counting(uppercaseAlphabet.dots(dots), child)

    /**
     * Creates a counting enumeration of lowercase roman numerals with the provided child enumeration.
     *
     * @param child Enumeration for the next nested level.
     * @param dots Whether to append dots.
     * @return Counting enumeration with lowercase roman numerals.
     */
    @JvmOverloads
    public fun lowercaseRomanNumerals(child: Enumeration<T>? = null, dots: Boolean = true): Enumeration.Counting<T> =
        counting(lowercaseRomanNumerals.dots(dots), child)

    /**
     * Creates a counting enumeration of uppercase roman numerals with the provided child enumeration.
     *
     * @param child Enumeration for the next nested level.
     * @param dots Whether to append dots.
     * @return Counting enumeration with uppercase roman numerals.
     */
    @JvmOverloads
    public fun uppercaseRomanNumerals(child: Enumeration<T>? = null, dots: Boolean = true): Enumeration.Counting<T> =
        counting(uppercaseRomanNumerals.dots(dots), child)

    private fun String.space(space: Boolean = true) = if (space) "$this\u2004" else this

    private fun Sequence<String>.dots(dots: Boolean = true) =
        if (dots) this.map { "$it." } else this

    private companion object {
        private const val bullet = "\u2022"
        private const val circle = "\u25E6"
        private const val square = "\u25A0"
        private const val dash = "\u2013"

        private val counting = generateSequence(1u, UInt::inc)
        private val integers: Sequence<String> = counting.map(UInt::toString)
        private val lowercaseAlphabet: Sequence<String> = repeatCombining('a'..'z')
        private val uppercaseAlphabet: Sequence<String> = repeatCombining('A'..'Z')
        private val uppercaseRomanNumerals = counting.map(UInt::toRomanNumeralString)
        private val lowercaseRomanNumerals = counting.map { it.toRomanNumeralString().lowercase() }

        private fun repeatCombining(iterable: Iterable<Any>, prefix: String = ""): Sequence<String> = sequence {
            yieldAll(iterable.map { "$prefix$it" })

            iterable.forEach {
                yieldAll(repeatCombining(iterable, "$it$prefix"))
            }
        }
    }
}
