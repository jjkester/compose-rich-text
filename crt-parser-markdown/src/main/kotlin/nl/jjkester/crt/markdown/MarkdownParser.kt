package nl.jjkester.crt.markdown

import nl.jjkester.crt.api.factory.DefaultNodeFactory
import nl.jjkester.crt.api.model.Node
import nl.jjkester.crt.api.parser.Parser
import nl.jjkester.crt.api.parser.ParserMetrics
import java.io.InputStream
import kotlin.time.Duration
import kotlin.time.ExperimentalTime
import kotlin.time.measureTime
import org.commonmark.node.Node as CommonMarkNode
import org.commonmark.parser.Parser as CommonMarkParser

public class MarkdownParser(
    private val parserModules: List<MarkdownParserModule>
) : Parser<MarkdownParserResult> {

    private val parser: CommonMarkParser by lazy { buildParser() }

    override fun parse(input: String): MarkdownParserResult = parseInternal {
        parser.parse(input)
    }

    override fun parse(inputStream: InputStream): MarkdownParserResult = parseInternal {
        inputStream.reader().use { reader ->
            parser.parseReader(reader)
        }
    }

    @OptIn(ExperimentalTime::class)
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

    private fun transform(node: CommonMarkNode): Node? {
        return parserModules.firstNotNullOfOrNull { it.parse(node, ::transform) }
    }

    private fun buildParser(): CommonMarkParser {
        return CommonMarkParser.Builder()
            .extensions(parserModules.mapNotNull(MarkdownParserModule::extension))
            .build()
    }
}
