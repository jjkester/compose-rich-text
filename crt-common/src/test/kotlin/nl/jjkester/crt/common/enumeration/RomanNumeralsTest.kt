package nl.jjkester.crt.common.enumeration

import assertk.assertThat
import assertk.assertions.isEqualTo
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource

class RomanNumeralsTest {

    @ParameterizedTest(name = "{1}")
    @MethodSource("testCases")
    fun toRomanNumeralString(number: UInt, numeral: String) {
        assertThat(number.toRomanNumeralString()).isEqualTo(numeral)
    }

    companion object {

        @JvmStatic
        fun testCases() = listOf(
            1 to "I",
            2 to "II",
            3 to "III",
            4 to "IV",
            5 to "V",
            6 to "VI",
            7 to "VII",
            8 to "VIII",
            9 to "IX",
            10 to "X",
            11 to "XI",
            12 to "XII",
            13 to "XIII",
            14 to "XIV",
            15 to "XV",
            16 to "XVI",
            17 to "XVII",
            18 to "XVIII",
            19 to "XIX",
            20 to "XX",
            49 to "XLIX",
            99 to "XCIX",
            499 to "CDXCIX",
            999 to "CMXCIX",
            3888 to "MMMDCCCLXXXVIII"
        ).map { Arguments.of(it.first, it.second) }
    }
}
