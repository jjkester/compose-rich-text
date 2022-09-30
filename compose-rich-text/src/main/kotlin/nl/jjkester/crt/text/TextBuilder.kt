package nl.jjkester.crt.text

interface TextBuilder<out R : RichTextString> {
    fun append(text: String)
    fun emphasis(content: TextBuilder<R>.() -> Unit)
    fun strongEmphasis(content: TextBuilder<R>.() -> Unit)
    fun code(content: TextBuilder<R>.() -> Unit)
    fun link(id: String, content: TextBuilder<R>.() -> Unit)
    fun build(): R
}

