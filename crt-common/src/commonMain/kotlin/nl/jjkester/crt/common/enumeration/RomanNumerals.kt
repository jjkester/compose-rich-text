@file:JvmName("RomanNumerals")

package nl.jjkester.crt.common.enumeration

import kotlin.jvm.JvmName

private val encoding by lazy(LazyThreadSafetyMode.NONE) {
    listOf(
        1 to "I",
        5 to "V",
        10 to "X",
        50 to "L",
        100 to "C",
        500 to "D",
        1000 to "M",
    )
        .windowed(3, partialWindows = false)
        .filter { (first, second, third) -> second.first == first.first * 5 && third.first == first.first * 10 }
        .flatMap { (ones, fives, tens) ->
            listOf(
                ones.first to ones.second,
                fives.first - ones.first to ones.second + fives.second,
                fives.first to fives.second,
                tens.first - ones.first to ones.second + tens.second,
                tens.first to tens.second
            )
        }
        .sortedByDescending { it.first }
}

internal fun UInt.toRomanNumeralString(): String {
    var remainder = this

    return buildString {
        encoding.forEach { (multiplier, character) ->
            if (remainder > 0u) {
                append(character.repeat(remainder.toInt() / multiplier))
                remainder %= multiplier.toUInt()
            }
        }
    }
}
