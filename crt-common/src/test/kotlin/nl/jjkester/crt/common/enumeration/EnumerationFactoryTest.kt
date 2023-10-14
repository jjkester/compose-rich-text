package nl.jjkester.crt.common.enumeration

import assertk.Assert
import assertk.assertThat
import assertk.assertions.isEqualTo
import nl.jjkester.crt.api.annotations.InternalRendererApi
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

class EnumerationFactoryTest {

    private val systemUnderTest = object : EnumerationFactory<String>() {
        @InternalRendererApi
        override fun convert(value: String): String = value
    }

    @Test
    fun bulleted() {
        val result = systemUnderTest.bulleted

        assertThat(result).enumerates(
            { "\u2022\u2004" },
            { "\u25E6\u2004" },
            { "\u25A0\u2004" },
            { "\u25A0\u2004" }
        )
    }

    @Test
    fun alphanumeric() {
        val result = systemUnderTest.alphanumeric

        assertThat(result).enumerates(
            { "${it + 1}." },
            { arrayOf("a.", "b.", "c.")[it] },
            { arrayOf("i.", "ii.", "iii.")[it] }
        )
    }

    @Test
    fun fixed() {
        val result = systemUnderTest.fixed("-", "*", ".")

        assertThat(result).enumerates(
            { "-" },
            { "*" },
            { "." },
            { "." }
        )
    }

    @Test
    fun counting() {
        val numbers = generateSequence("1") { (it[0] + 1).toString() }
        val letters = generateSequence("a") { (it[0] + 1).toString() }

        val result = systemUnderTest.counting(numbers, letters, numbers)

        assertThat(result).enumerates(
            { numbers.drop(it).first() },
            { letters.drop(it).first() },
            { numbers.drop(it).first() },
        )
    }

    @Test
    fun bullets() {
        val result = systemUnderTest.bullets()

        assertThat(result).enumerates(
            generateSequence { "\u2022\u2004" },
            generateSequence { "\u2022\u2004" }
        )
    }

    @ParameterizedTest(name = "{displayName} {0}")
    @ValueSource(booleans = [true, false])
    fun bullets(space: Boolean) {
        val result = systemUnderTest.bullets(emptyStringEnumeration, space)

        assertThat(result).enumerates(
            generateSequence { "\u2022${if (space) "\u2004" else ""}" },
            emptyStringSequence(),
            emptyStringSequence()
        )
    }

    @Test
    fun dashes() {
        val result = systemUnderTest.dashes()

        assertThat(result).enumerates(
            { "\u2013\u2004" },
            { "\u2013\u2004" }
        )
    }

    @ParameterizedTest(name = "{displayName} {0}")
    @ValueSource(booleans = [true, false])
    fun dashes(space: Boolean) {
        val result = systemUnderTest.dashes(emptyStringEnumeration, space)

        assertThat(result).enumerates(
            generateSequence { "\u2013${if (space) "\u2004" else ""}" },
            emptyStringSequence(),
            emptyStringSequence()
        )
    }

    @Test
    fun numbers() {
        val result = systemUnderTest.numbers()

        assertThat(result).enumerates(
            intSequence().map { "$it." },
            intSequence().map { "$it." }
        )
    }

    @ParameterizedTest(name = "{displayName} {0}")
    @ValueSource(booleans = [true, false])
    fun numbers(dots: Boolean) {
        val result = systemUnderTest.numbers(emptyStringEnumeration, dots)

        assertThat(result).enumerates(
            intSequence().map { "$it${if (dots) "." else ""}" },
            emptyStringSequence(),
            emptyStringSequence()
        )
    }

    @Test
    fun lowercaseLetters() {
        val result = systemUnderTest.lowercaseLetters()

        assertThat(result).enumerates(
            charSequence().map { "$it." },
            charSequence().map { "$it." }
        )
    }

    @ParameterizedTest(name = "{displayName} {0}")
    @ValueSource(booleans = [true, false])
    fun lowercaseLetters(dots: Boolean) {
        val result = systemUnderTest.lowercaseLetters(emptyStringEnumeration, dots)

        assertThat(result).enumerates(
            charSequence().map { "$it${if (dots) "." else ""}" },
            emptyStringSequence(),
            emptyStringSequence()
        )
    }

    @Test
    fun uppercaseLetters() {
        val result = systemUnderTest.uppercaseLetters()

        assertThat(result).enumerates(
            charSequence().map { "${it.uppercaseChar()}." },
            charSequence().map { "${it.uppercaseChar()}." }
        )
    }

    @ParameterizedTest(name = "{displayName} {0}")
    @ValueSource(booleans = [true, false])
    fun uppercaseLetters(dots: Boolean) {
        val result = systemUnderTest.uppercaseLetters(emptyStringEnumeration, dots)

        assertThat(result).enumerates(
            charSequence().map { "${it.uppercaseChar()}${if (dots) "." else ""}" },
            emptyStringSequence(),
            emptyStringSequence()
        )
    }

    @Test
    fun lowercaseRomanNumerals() {
        val result = systemUnderTest.lowercaseRomanNumerals()

        assertThat(result).enumerates(
            intSequence().map { "${it.toUInt().toRomanNumeralString().lowercase()}." },
            intSequence().map { "${it.toUInt().toRomanNumeralString().lowercase()}." }
        )
    }

    @ParameterizedTest(name = "{displayName} {0}")
    @ValueSource(booleans = [true, false])
    fun lowercaseRomanNumerals(dots: Boolean) {
        val result = systemUnderTest.lowercaseRomanNumerals(emptyStringEnumeration, dots)

        assertThat(result).enumerates(
            intSequence().map { "${it.toUInt().toRomanNumeralString().lowercase()}${if (dots) "." else ""}" },
            emptyStringSequence(),
            emptyStringSequence()
        )
    }

    @Test
    fun uppercaseRomanNumerals() {
        val result = systemUnderTest.uppercaseRomanNumerals()

        assertThat(result).enumerates(
            intSequence().map { "${it.toUInt().toRomanNumeralString()}." },
            intSequence().map { "${it.toUInt().toRomanNumeralString()}." }
        )
    }

    @ParameterizedTest(name = "{displayName} {0}")
    @ValueSource(booleans = [true, false])
    fun uppercaseRomanNumerals(dots: Boolean) {
        val result = systemUnderTest.uppercaseRomanNumerals(emptyStringEnumeration, dots)

        assertThat(result).enumerates(
            intSequence().map { "${it.toUInt().toRomanNumeralString()}${if (dots) "." else ""}" },
            emptyStringSequence(),
            emptyStringSequence()
        )
    }

    companion object {

        private val emptyStringEnumeration = StaticEnumeration({ "" })

        private fun emptyStringSequence() = generateSequence { "" }

        private fun intSequence() = generateSequence(1) { it + 1 }

        private fun charSequence() = generateSequence('a') { it + 1 }

        private fun <T : Any> Assert<Enumeration<T>>.enumerates(vararg levels: (Int) -> T) = this
            .transform { enumeration ->
                List(levels.size) { (0 until it).fold(enumeration) { parent, _ -> parent.child }.take(3) }
            }
            .isEqualTo(levels.map { List(3, it) })

        private fun <T : Any> Assert<Enumeration<T>>.enumerates(vararg levels: Sequence<T>) = this
            .transform { enumeration ->
                List(levels.size) { (0 until it).fold(enumeration) { parent, _ -> parent.child }.take(3) }
            }
            .isEqualTo(levels.map { it.take(3).toList() })
    }
}
