package nl.jjkester.crt.markdown

import nl.jjkester.crt.api.model.Node
import nl.jjkester.crt.api.parser.ParserMetrics
import nl.jjkester.crt.api.parser.ParserResult

/**
 * Result of parsing Markdown.
 *
 * @property rootNode Root node of the parsed result.
 * @property metrics Metrics collected while parsing.
 */
public class MarkdownParserResult(override val rootNode: Node, override val metrics: ParserMetrics) : ParserResult
