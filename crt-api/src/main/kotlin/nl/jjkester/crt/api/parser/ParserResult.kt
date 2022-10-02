package nl.jjkester.crt.api.parser

import nl.jjkester.crt.api.model.tree.Node

public interface ParserResult {

    public val rootNode: Node

    public val metrics: ParserMetrics?
}
