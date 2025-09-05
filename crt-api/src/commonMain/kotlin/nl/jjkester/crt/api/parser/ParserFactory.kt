package nl.jjkester.crt.api.parser

/**
 * Factory to create specific [Parser]s.
 *
 * @param M Type of parser modules for this type of parser.
 * @param R Result type for this type of parser.
 */
public interface ParserFactory<M : ParserModule<*, *>, out R : ParserResult> {

    /**
     * Modules to include in created parsers.
     */
    public var modules: List<M>

    /**
     * Whether to collect debug information during parsing.
     */
    public var collectDebugInfo: Boolean

    /**
     * Creates a new parser with the configuration of this factory.
     */
    public fun create(): Parser<R>
}
