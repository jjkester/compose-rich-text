package nl.jjkester.crt.api.parser

import kotlinx.io.asSource
import kotlinx.io.buffered
import java.io.InputStream

/**
 * Parses rich text from an input stream.
 *
 * @param inputStream Input stream of the rich text to parse.
 */
public fun <R : ParserResult> Parser<R>.parse(inputStream: InputStream): R = parse(inputStream.asSource().buffered())
