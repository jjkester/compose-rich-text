package nl.jjkester.crt.api.parser

public interface ParserModule<T : Any, I : Any> {

    public fun parse(value: T, parseNext: (T) -> I?): I?
}
