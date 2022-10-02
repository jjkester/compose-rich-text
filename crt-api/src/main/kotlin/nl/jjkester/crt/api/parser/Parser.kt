package nl.jjkester.crt.api.parser

import java.io.InputStream

public interface Parser<out R : ParserResult> {

    public fun parse(input: String): R

    public fun parse(inputStream: InputStream): R
}
