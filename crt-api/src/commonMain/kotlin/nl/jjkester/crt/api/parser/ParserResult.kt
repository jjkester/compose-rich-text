package nl.jjkester.crt.api.parser

import nl.jjkester.crt.api.model.Node

/**
 * Result of parsing rich text.
 */
public interface ParserResult {

    /**
     * Root node of the parsed rich text.
     */
    public val rootNode: Node

    /**
     * Optionally collected metrics during parsing.
     */
    public val metrics: ParserMetrics?
}
