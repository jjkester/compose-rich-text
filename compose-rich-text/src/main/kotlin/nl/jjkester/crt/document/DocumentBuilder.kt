package nl.jjkester.crt.document

import nl.jjkester.crt.text.RichTextString
import nl.jjkester.crt.text.TextBuilder

interface DocumentBuilder<out R : RichTextDocument, out T : RichTextString> {
    fun heading(level: Int, content: TextBuilder<T>.() -> Unit)
    fun paragraph(content: TextBuilder<T>.() -> Unit)
    fun blockquote(content: DocumentBuilder<R, T>.() -> Unit)
    fun orderedList(content: DocumentBuilder<R, T>.() -> Unit)
    fun unorderedList(content: DocumentBuilder<R, T>.() -> Unit)
    fun listItem(content: DocumentBuilder<R, T>.() -> Unit)
    fun code(content: String, language: String? = null)
    fun separator()
    fun build(): R
}