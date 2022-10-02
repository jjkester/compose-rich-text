package nl.jjkester.crt.common.enumeration

internal class SequenceEnumeration<T : Any>(
    override val sequence: Sequence<T>,
    child: Enumeration.Counting<T>? = null
) : Enumeration.Counting<T> {

    override val child: Enumeration.Counting<T> = child ?: this
}
