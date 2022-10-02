package nl.jjkester.crt.api.parser

public interface ParserFactory<M : ParserModule<*, *>, out R : ParserResult> {

    public var modules: List<M>

    public var collectDebugInfo: Boolean

    public fun create(): Parser<R>
}
