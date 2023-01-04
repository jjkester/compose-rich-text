package nl.jjkester.crt.api.parser

/**
 * Abstract parser factory implementation that sets default values for all configuration options.
 *
 * @param M Type of parser modules for this type of parser.
 * @param R Result type for this type of parser.
 */
public abstract class AbstractParserFactory<M : ParserModule<*, *>, out R : ParserResult> : ParserFactory<M, R> {

    override var modules: List<M> = emptyList()

    override var collectDebugInfo: Boolean = false
}
