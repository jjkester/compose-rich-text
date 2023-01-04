package nl.jjkester.crt.markdown

import nl.jjkester.crt.api.model.Node
import nl.jjkester.crt.api.parser.ParserModule
import org.commonmark.parser.Parser.ParserExtension
import org.commonmark.node.Node as CommonMarkNode

public interface MarkdownParserModule : ParserModule<CommonMarkNode, Node> {

    public val extension: ParserExtension?
        get() = null
}
