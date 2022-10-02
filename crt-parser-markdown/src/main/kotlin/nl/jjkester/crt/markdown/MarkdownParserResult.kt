package nl.jjkester.crt.markdown

import nl.jjkester.crt.api.model.tree.Node
import nl.jjkester.crt.api.parser.ParserMetrics
import nl.jjkester.crt.api.parser.ParserResult

public class MarkdownParserResult(override val rootNode: Node, override val metrics: ParserMetrics) : ParserResult
