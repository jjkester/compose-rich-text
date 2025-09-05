package nl.jjkester.crt.markdown

import nl.jjkester.crt.api.model.Node
import nl.jjkester.crt.api.parser.ParserModule
import org.commonmark.parser.Parser.ParserExtension
import org.commonmark.node.Node as CommonMarkNode

/**
 * Parser module for the Markdown parser.
 *
 * Markdown parser modules can optionally provide an [extension] for the CommonMark parser to support additional
 * functionality.
 */
public interface MarkdownParserModule : ParserModule<CommonMarkNode, Node> {

    /**
     * CommonMark parser extension for this module.
     */
    public val extension: ParserExtension?
        get() = null
}
