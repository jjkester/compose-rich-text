package nl.jjkester.crt.common.enumeration

import nl.jjkester.crt.api.text.RichTextString

internal class SequenceEnumeration<T : RichTextString>(
    override val sequence: Sequence<T>,
    child: Enumeration.Counting<T>? = null
) : Enumeration.Counting<T> {

    override val child: Enumeration.Counting<T> = child ?: this
}
