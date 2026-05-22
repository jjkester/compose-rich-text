package nl.jjkester.crt.markdown

import assertk.all
import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.isGreaterThan
import nl.jjkester.crt.api.annotations.InternalParserApi
import nl.jjkester.crt.api.model.Text
import org.commonmark.node.Node as CommonMarkNode
import org.commonmark.parser.Parser as CommonMarkParser
import org.junit.jupiter.api.Test
import org.mockito.kotlin.*
import kotlin.time.Duration

@OptIn(InternalParserApi::class)
class MarkdownParserTest {

    private val commonMarkExtension = mock<CommonMarkParser.ParserExtension>()
    private val firstParserModule = mock<MarkdownParserModule>()
    private val secondParserModule = mock<MarkdownParserModule> {
        on { extension } doReturn commonMarkExtension
    }
    private val commonMarkParser = mock<CommonMarkParser>()
    private val commonMarkParserFactory = mock<(Iterable<CommonMarkParser.ParserExtension>) -> CommonMarkParser> {
        on { invoke(any()) } doReturn commonMarkParser
    }

    private val systemUnderTest = MarkdownParser(
        listOf(firstParserModule, secondParserModule),
        commonMarkParserFactory
    )

    @Test
    fun `commonmark parser is lazily created with extensions`() {
        verifyNoInteractions(commonMarkParserFactory)

        systemUnderTest.parse("")

        verify(commonMarkParserFactory).invoke(listOf(commonMarkExtension))

        systemUnderTest.parse("")

        verifyNoMoreInteractions(commonMarkParserFactory)
    }

    @Test
    fun `parsing a string`() {
        val markdownNode = mock<CommonMarkNode>()
        val node = Text("result", null)

        commonMarkParser.stub { on { parse(any()) } doReturn markdownNode }
        firstParserModule.stub { on { parse(any(), any()) } doReturn node }

        val result = systemUnderTest.parse("input")

        assertThat(result).all {
            transform { it.rootNode }.isEqualTo(node)
            transform { it.metrics.totalTime }.isGreaterThan(Duration.ZERO)
        }

        verify(commonMarkParser).parse("input")
        verify(firstParserModule).parse(same(markdownNode), any())
        verify(secondParserModule, times(0)).parse(same(markdownNode), any())
    }

    @Test
    fun `parsing an input stream`() {
        val markdownNode = mock<CommonMarkNode>()
        val node = Text("result", null)
        val inputStream = "input".byteInputStream()

        commonMarkParser.stub { on { parseReader(any()) } doReturn markdownNode }
        firstParserModule.stub { on { parse(any(), any()) } doReturn node }

        val result = systemUnderTest.parse(inputStream)

        assertThat(result).all {
            transform { it.rootNode }.isEqualTo(node)
            transform { it.metrics }.all {
                transform { it.fileReadTime }.isEqualTo(Duration.ZERO)
                transform { it.sourceParseTime }.isGreaterThan(Duration.ZERO)
                transform { it.intermediateTransformTime }.isGreaterThan(Duration.ZERO)
                transform { it.totalTime }.isEqualTo(result.metrics.run { sourceParseTime + intermediateTransformTime })
            }
        }

        verify(commonMarkParser).parseReader(any()) // More precise assertion impossible
        verify(firstParserModule).parse(same(markdownNode), any())
        verify(secondParserModule, times(0)).parse(same(markdownNode), any())
    }
}
