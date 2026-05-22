package nl.jjkester.crt.api.parser

public actual interface Parser<out R : ParserResult> {
    public actual fun parse(input: String): R
    public actual fun parse(source: kotlinx.io.Source): R
}
