package nl.jjkester.crt.markdown

import kotlinx.io.Source
import kotlinx.io.asInputStream
import nl.jjkester.crt.api.annotations.InternalFactoryApi
import nl.jjkester.crt.api.annotations.InternalParserApi
import nl.jjkester.crt.api.factory.DefaultNodeFactory
import nl.jjkester.crt.api.model.Node
import nl.jjkester.crt.api.parser.Parser
import nl.jjkester.crt.api.parser.ParserMetrics
import kotlin.time.Duration
import kotlin.time.ExperimentalTime
import kotlin.time.measureTime
import org.commonmark.node.Node as CommonMarkNode
import org.commonmark.parser.Parser as CommonMarkParser

/**
 * Compose Rich Text parser for Markdown sources.
 *
 * An instance of [MarkdownParser] can be obtained through a [MarkdownParserFactory].
 *
 * @property parserModules Markdown parser modules to use while parsing. An empty list of modules will result in an
 * empty output.
 * @param commonMarkParserFactory Factory to create a configured CommonMark parser given the provided extensions.
 * @see MarkdownParserFactory
 */
public class MarkdownParser internal constructor(
    internal val parserModules: List<MarkdownParserModule>,
    commonMarkParserFactory: (List<CommonMarkParser.ParserExtension>) -> CommonMarkParser
) : Parser<MarkdownParserResult> {

    private val parser: CommonMarkParser by lazy {
        commonMarkParserFactory(parserModules.mapNotNull(MarkdownParserModule::extension))
    }

    override fun parse(input: String): MarkdownParserResult = parseInternal {
        parser.parse(input)
    }

    override fun parse(source: Source): MarkdownParserResult = parseInternal {
        source.asInputStream().reader().use { reader ->
            parser.parseReader(reader)
        }
    }

    @OptIn(ExperimentalTime::class, InternalFactoryApi::class, InternalParserApi::class)
    private inline fun parseInternal(parser: () -> CommonMarkNode?): MarkdownParserResult {
        val parserOutput: CommonMarkNode?
        val parseTime = measureTime { parserOutput = parser() }

        val transformOutput: Node?
        val transformTime = measureTime { transformOutput = parserOutput?.let(::transform) }

        return MarkdownParserResult(
            rootNode = transformOutput ?: DefaultNodeFactory.container(),
            metrics = ParserMetrics(
                fileReadTime = Duration.ZERO,
                sourceParseTime = parseTime,
                intermediateTransformTime = transformTime
            )
        )
    }

    @OptIn(InternalParserApi::class)
    private fun transform(node: CommonMarkNode): Node? {
        return parserModules.firstNotNullOfOrNull { it.parse(node, ::transform) }
    }
}
