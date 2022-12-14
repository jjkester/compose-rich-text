package nl.jjkester.crt.common.enumeration

internal class StaticEnumeration<out T : Any>(
    lazyValue: () -> T,
    child: Enumeration.Fixed<T>? = null
) : Enumeration.Fixed<T> {

    override val value: T by lazy(lazyValue)

    override val child: Enumeration.Fixed<T> = child ?: this
}
