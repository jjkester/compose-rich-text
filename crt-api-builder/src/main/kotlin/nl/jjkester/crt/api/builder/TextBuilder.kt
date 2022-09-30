package nl.jjkester.crt.api.builder

import nl.jjkester.crt.api.text.RichTextString

public interface TextBuilder<out R : RichTextString> {
    public fun append(text: String)
    public fun emphasis(content: TextBuilder<R>.() -> Unit)
    public fun strongEmphasis(content: TextBuilder<R>.() -> Unit)
    public fun code(content: TextBuilder<R>.() -> Unit)
    public fun link(id: String, content: TextBuilder<R>.() -> Unit)
    public fun build(): R
}
