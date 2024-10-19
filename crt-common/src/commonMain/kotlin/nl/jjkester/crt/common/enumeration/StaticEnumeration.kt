package nl.jjkester.crt.common.enumeration

internal class StaticEnumeration<out T : Any>(
    lazyValue: () -> T,
    child: Enumeration<T>? = null
) : Enumeration.Fixed<T> {

    private val value: T by lazy(lazyValue)

    override val child: Enumeration<T> = child ?: this

    override fun iterator(): Iterator<T> = iterator { while (true) yield(value) }
}
