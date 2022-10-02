package nl.jjkester.crt.api.builder

import nl.jjkester.crt.api.document.RichTextDocument
import nl.jjkester.crt.api.text.RichTextString

public interface DocumentBuilder<out R : RichTextDocument, out T : RichTextString> {
    public fun heading(level: Int, content: TextBuilder<T>.() -> Unit)
    public fun paragraph(content: TextBuilder<T>.() -> Unit)
    public fun blockquote(content: DocumentBuilder<R, T>.() -> Unit)
    public fun orderedList(content: DocumentBuilder<R, T>.() -> Unit)
    public fun unorderedList(content: DocumentBuilder<R, T>.() -> Unit)
    public fun listItem(content: DocumentBuilder<R, T>.() -> Unit)
    public fun code(content: String, language: String? = null)
    public fun separator()
    public fun build(): R
}
