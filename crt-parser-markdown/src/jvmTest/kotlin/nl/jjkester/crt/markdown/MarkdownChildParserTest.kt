package nl.jjkester.crt.markdown

import assertk.assertThat
import assertk.assertions.containsExactly
import assertk.assertions.isEqualTo
import nl.jjkester.crt.api.model.*
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource
import org.mockito.kotlin.*
import org.commonmark.node.Block as CommonMarkBlock
import org.commonmark.node.ListItem as CommonMarkListItem
import org.commonmark.node.Node as CommonMarkNode

internal class MarkdownChildParserTest {

    private val parseFunction = mock<(CommonMarkNode) -> Node?>()

    private val systemUnderTest = MarkdownChildParser(parseFunction)

    @ParameterizedTest(name = "{displayName} {0}")
    @MethodSource("testCases")
    fun `valid input and valid output`(testCase: TestCase) = with(testCase) {
        val secondMarkdownChildNode = validInput()
        val firstMarkdownChildNode = validInput().stub {
            on { next } doReturn secondMarkdownChildNode
        }
        val markdownNode = mock<CommonMarkNode> {
            on { firstChild } doReturn firstMarkdownChildNode
        }

        parseFunction.stub { on { invoke(any()) } doReturnConsecutively validOutputs.toList() }

        val result = systemUnderTest.function(markdownNode)

        assertThat(result).isEqualTo(validOutputs.toList())

        verify(parseFunction).invoke(firstMarkdownChildNode)
        verify(parseFunction).invoke(secondMarkdownChildNode)
        verifyNoMoreInteractions(parseFunction)
    }

    @ParameterizedTest(name = "{displayName} {0}")
    @MethodSource("testCases")
    fun `valid input and valid and absent output`(testCase: TestCase) = with(testCase) {
        val secondMarkdownChildNode = validInput()
        val firstMarkdownChildNode = validInput().stub {
            on { next } doReturn secondMarkdownChildNode
        }
        val markdownNode = mock<CommonMarkNode> {
            on { firstChild } doReturn firstMarkdownChildNode
        }

        parseFunction.stub { on { invoke(secondMarkdownChildNode) } doReturn validOutputs.second }

        val result = systemUnderTest.function(markdownNode)

        assertThat(result).containsExactly(validOutputs.second)

        verify(parseFunction).invoke(firstMarkdownChildNode)
        verify(parseFunction).invoke(secondMarkdownChildNode)
        verifyNoMoreInteractions(parseFunction)
    }

    @ParameterizedTest(name = "{displayName} {0}")
    @MethodSource("testCases")
    fun `valid input and invalid output`(testCase: TestCase) = with(testCase) {
        val secondMarkdownChildNode = validInput()
        val firstMarkdownChildNode = validInput().stub {
            on { next } doReturn secondMarkdownChildNode
        }
        val markdownNode = mock<CommonMarkNode> {
            on { firstChild } doReturn firstMarkdownChildNode
        }

        parseFunction.stub {
            on { invoke(firstMarkdownChildNode) } doReturn validOutputs.first
            on { invoke(secondMarkdownChildNode) } doReturn invalidOutput
        }

        val result = systemUnderTest.function(markdownNode)

        assertThat(result).containsExactly(validOutputs.first)

        verify(parseFunction).invoke(firstMarkdownChildNode)
        verify(parseFunction).invoke(secondMarkdownChildNode)
        verifyNoMoreInteractions(parseFunction)
    }

    @ParameterizedTest(name = "{displayName} {0}")
    @MethodSource("testCases")
    fun `invalid and valid input and valid, present output`(testCase: TestCase) = with(testCase) {
        val secondMarkdownChildNode = validInput()
        val firstMarkdownChildNode = invalidInput().stub {
            on { next } doReturn secondMarkdownChildNode
        }
        val markdownNode = mock<CommonMarkNode> {
            on { firstChild } doReturn firstMarkdownChildNode
        }

        parseFunction.stub { on { invoke(secondMarkdownChildNode) } doReturn validOutputs.second }

        val result = systemUnderTest.function(markdownNode)

        assertThat(result).containsExactly(validOutputs.second)

        verify(parseFunction).invoke(secondMarkdownChildNode)
        verifyNoMoreInteractions(parseFunction)
    }

    companion object {

        @JvmStatic
        private fun testCases() = listOf(
            TestCase(
                "parse block children",
                MarkdownChildParser::parseBlockChildren,
                { mock<CommonMarkBlock>() },
                { mock<CommonMarkNode>() },
                Container(emptyList(), null) to Paragraph(emptyList(), null),
                Emphasis(emptyList(), null)
            ),
            TestCase(
                "parse span children",
                MarkdownChildParser::parseSpanChildren,
                { mock<CommonMarkNode>() },
                { mock<CommonMarkBlock>() },
                Emphasis(emptyList(), null) to StrongEmphasis(emptyList(), null),
                Container(emptyList(), null)
            ),
            TestCase(
                "parse list item children",
                MarkdownChildParser::parseListItemChildren,
                { mock<CommonMarkListItem>() },
                { mock<CommonMarkNode>() },
                ListItem(emptyList(), null) to ListItem(emptyList(), null),
                Container(emptyList(), null)
            ),
        )
    }

    data class TestCase(
        val name: String,
        val function: MarkdownChildParser.(CommonMarkNode) -> List<Node>,
        val validInput: () -> CommonMarkNode,
        val invalidInput: () -> CommonMarkNode,
        val validOutputs: Pair<Node, Node>,
        val invalidOutput: Node
    ) {
        override fun toString(): String = name
    }
}
