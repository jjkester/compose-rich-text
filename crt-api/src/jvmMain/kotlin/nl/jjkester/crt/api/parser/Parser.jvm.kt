package nl.jjkester.crt.api.parser

import kotlinx.io.Source
import java.io.InputStream

public actual interface Parser<out R : ParserResult> {
    public actual fun parse(input: String): R
    public actual fun parse(source: Source): R

    /**
     * Parses rich text in a streaming fashion.
     *
     * @param inputStream Input stream of the rich text to parse.
     */
    public fun parse(inputStream: InputStream): R
}
