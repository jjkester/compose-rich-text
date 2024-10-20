package nl.jjkester.crt.api.model

import kotlin.jvm.JvmInline

/**
 * Programming language for code snippets. The language is used for syntax highlighting.
 *
 * @property value String representation of the programming language.
 */
@JvmInline
public value class Language(public val value: String)
