package nl.jjkester.crt.api.parser

public abstract class AbstractParserFactory<M : ParserModule<*, *>, out R : ParserResult> : ParserFactory<M, R> {

    override var modules: List<M> = emptyList()

    override var collectDebugInfo: Boolean = false
}
