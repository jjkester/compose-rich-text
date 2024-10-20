package nl.jjkester.crt.common.enumeration

internal class SequenceEnumeration<T : Any>(
    private val sequence: Sequence<T>,
    child: Enumeration<T>? = null
) : Enumeration.Counting<T> {

    override val child: Enumeration<T> = child ?: this

    override fun iterator(): Iterator<T> = sequence.iterator()
}
