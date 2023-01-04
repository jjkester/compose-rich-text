package nl.jjkester.crt.api.parser

import java.io.InputStream

/**
 * Parser for parsing rich text in a specific format to the internal model.
 *
 * @param R Result type of this parser.
 */
public interface Parser<out R : ParserResult> {

    /**
     * Parses rich text from string.
     *
     * @param input Rich text to parse.
     */
    public fun parse(input: String): R

    /**
     * Parses rich text from an input stream.
     *
     * @param inputStream Input stream of the rich text to parse.
     */
    public fun parse(inputStream: InputStream): R
}
