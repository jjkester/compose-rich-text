package nl.jjkester.crt.common.enumeration

import nl.jjkester.crt.api.text.RichTextString

internal class StaticEnumeration<out T : RichTextString>(
    lazyValue: () -> T,
    child: Enumeration.Fixed<T>? = null
) : Enumeration.Fixed<T> {

    override val value: T by lazy(lazyValue)

    override val child: Enumeration.Fixed<T> = child ?: this
}
