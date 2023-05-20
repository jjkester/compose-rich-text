@file:JvmName("RomanNumerals")

package nl.jjkester.crt.common.enumeration

private val encoding by lazy(LazyThreadSafetyMode.NONE) {
    sortedMapOf(
        1 to "I",
        5 to "V",
        10 to "X",
        50 to "L",
        100 to "C",
        500 to "D",
        1000 to "M",
    )
        .entries
        .windowed(3, partialWindows = false)
        .filter { (first, second, third) -> second.key == first.key * 5 && third.key == first.key * 10 }
        .flatMap { (ones, fives, tens) ->
            listOf(
                ones.key to ones.value,
                fives.key - ones.key to ones.value + fives.value,
                fives.key to fives.value,
                tens.key - ones.key to ones.value + tens.value,
                tens.key to tens.value
            )
        }
        .toMap()
        .toSortedMap { first, second -> second.compareTo(first) }
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
