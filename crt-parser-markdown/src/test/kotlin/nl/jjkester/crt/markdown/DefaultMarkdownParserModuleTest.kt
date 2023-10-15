package nl.jjkester.crt.markdown

import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.isNull
import assertk.assertions.isSameAs
import nl.jjkester.crt.api.annotations.InternalFactoryApi
import nl.jjkester.crt.api.annotations.InternalParserApi
import nl.jjkester.crt.api.factory.*
import nl.jjkester.crt.api.model.Node
import org.commonmark.node.*
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource
import org.junit.jupiter.params.provider.ValueSource
import org.mockito.kotlin.*
import nl.jjkester.crt.api.model.Link as NodeLink
import nl.jjkester.crt.api.model.ListItem as NodeListItem
import nl.jjkester.crt.api.model.Paragraph as NodeParagraph
import nl.jjkester.crt.api.model.Text as NodeText
import org.commonmark.node.Node as CommonMarkNode

@OptIn(InternalFactoryApi::class, InternalParserApi::class)
internal class DefaultMarkdownParserModuleTest {

    private val nodeFactory = mock<NodeFactory>()
    private val childParser = mock<MarkdownChildParser>()
    private val childParserFactory = mock<((CommonMarkNode) -> Node?) -> MarkdownChildParser> {
        on { invoke(any()) } doReturn childParser
    }

    @ParameterizedTest(name = "{displayName} {0}")
    @MethodSource("testCases")
    fun <M : CommonMarkNode, C : Node, N : Node> `parse supported nodes`(testCase: TestCase<M, C, N>) {
        with(testCase) {
            val systemUnderTest = DefaultMarkdownParserModule(true, nodeFactory, childParserFactory)

            val parseFunction = mock<(CommonMarkNode) -> Node?>()

            nodeFactory.stub { on { nodeFactoryFunction(parsedChildren) } doReturn node }
            childParserFunction?.let { function ->
                childParser.stub { on { function(markdownNode) } doReturn parsedChildren }
            }

            val result = systemUnderTest.parse(markdownNode, parseFunction)

            assertThat(result).isEqualTo(node)

            verify(childParserFactory).invoke(parseFunction)
            childParserFunction?.let { function ->
                verify(childParser).function(markdownNode)
            }
            verify(nodeFactory).nodeFactoryFunction(parsedChildren)
            verifyNoMoreInteractions(childParserFactory, childParser, nodeFactory)
        }
    }

    @ParameterizedTest(name = "{displayName} {0}")
    @ValueSource(booleans = [true, false])
    fun `parse fallback HtmlBlock`(fallback: Boolean) {
        val systemUnderTest = DefaultMarkdownParserModule(fallback, nodeFactory, childParserFactory)

        val markdownNode = HtmlBlock().apply { literal = "html literal" }
        val text = mock<NodeText>()
        val paragraph = mock<NodeParagraph>()

        nodeFactory.stub {
            on { text(any(), anyOrNull()) } doReturn text
            on { paragraph(any(), anyOrNull()) } doReturn paragraph
        }

        val result = systemUnderTest.parse(markdownNode) { null }

        assertThat(result).apply {
            if (fallback) isSameAs(paragraph) else isNull()
        }

        if (fallback) {
            verify(nodeFactory).text("html literal")
            verify(nodeFactory).paragraph(listOf(text))
        }
        verifyNoMoreInteractions(nodeFactory, childParser)
    }

    @ParameterizedTest(name = "{displayName} {0}")
    @ValueSource(booleans = [true, false])
    fun `parse fallback HtmlInline`(fallback: Boolean) {
        val systemUnderTest = DefaultMarkdownParserModule(fallback, nodeFactory, childParserFactory)

        val markdownNode = HtmlInline().apply { literal = "html literal" }
        val node = mock<NodeText>()

        nodeFactory.stub {
            on { text(any(), anyOrNull()) } doReturn node
        }

        val result = systemUnderTest.parse(markdownNode) { null }

        assertThat(result).apply {
            if (fallback) isSameAs(node) else isNull()
        }

        if (fallback) verify(nodeFactory).text("html literal")
        verifyNoMoreInteractions(nodeFactory, childParser)
    }

    @ParameterizedTest(name = "{displayName} {0}")
    @ValueSource(booleans = [true, false])
    fun `parse fallback Image without title`(fallback: Boolean) {
        val systemUnderTest = DefaultMarkdownParserModule(fallback, nodeFactory, childParserFactory)

        val markdownNode = Image("destination", null)
        val text = mock<NodeText>()
        val link = mock<NodeLink>()

        nodeFactory.stub {
            on { text(any(), anyOrNull()) } doReturn text
            on { link(anyValue(), any(), anyOrNull()) } doReturn link
        }

        val result = systemUnderTest.parse(markdownNode) { null }

        assertThat(result).apply {
            if (fallback) isSameAs(link) else isNull()
        }

        if (fallback) {
            verify(nodeFactory).text("destination")
            verify(nodeFactory).link("destination", listOf(text))
        }
        verifyNoMoreInteractions(nodeFactory, childParser)
    }

    @ParameterizedTest(name = "{displayName} {0}")
    @ValueSource(booleans = [true, false])
    fun `parse fallback Image with title`(fallback: Boolean) {
        val systemUnderTest = DefaultMarkdownParserModule(fallback, nodeFactory, childParserFactory)

        val markdownNode = Image("destination", "title")
        val text = mock<NodeText>()
        val link = mock<NodeLink>()

        nodeFactory.stub {
            on { text(any(), anyOrNull()) } doReturn text
            on { link(anyValue(), any(), anyOrNull()) } doReturn link
        }

        val result = systemUnderTest.parse(markdownNode) { null }

        assertThat(result).apply {
            if (fallback) isSameAs(link) else isNull()
        }

        if (fallback) {
            verify(nodeFactory).text("title")
            verify(nodeFactory).link("destination", listOf(text))
        }
        verifyNoMoreInteractions(nodeFactory, childParser)
    }

    companion object {

        @JvmStatic
        private fun testCases() = listOf(
            TestCase.withBlockChildren(BlockQuote(), NodeFactory::blockquote),
            TestCase.withListItemChildren(BulletList(), NodeFactory::unorderedList),
            TestCase.withoutChildren(Code("code literal")) {
                code("code literal")
            },
            TestCase.withBlockChildren(Document(), NodeFactory::container),
            TestCase.withSpanChildren(Emphasis(), NodeFactory::emphasis),
            TestCase.withoutChildren(FencedCodeBlock().apply {
                literal = "code literal"
                info = "language"
            }) {
                codeBlock("code literal", "language")
            },
            TestCase.withoutChildren(FencedCodeBlock().apply {
                literal = "code literal${System.lineSeparator()}"
                info = "language"
            }) {
                codeBlock("code literal", "language")
            },
            TestCase.withoutChildren(HardLineBreak()) {
                text(System.lineSeparator())
            },
            TestCase.withSpanChildren(Heading().apply { level = 3 }) {
                heading(3, it)
            },
            TestCase.withSpanChildren(Heading().apply { level = 42 }) {
                heading(42, it)
            },
            TestCase.withoutChildren(IndentedCodeBlock().apply { literal = "code literal" }) {
                codeBlock("code literal")
            },
            TestCase.withoutChildren(IndentedCodeBlock().apply { literal = "code literal${System.lineSeparator()}" }) {
                codeBlock("code literal")
            },
            TestCase.withSpanChildren(Link("destination", null)) {
                link("destination", it)
            },
            TestCase.withBlockChildren(ListItem(), NodeFactory::listItem),
            TestCase.withListItemChildren(OrderedList(), NodeFactory::orderedList),
            TestCase.withSpanChildren(Paragraph(), NodeFactory::paragraph),
            TestCase.withoutChildren(SoftLineBreak()) {
                text(" ")
            },
            TestCase.withSpanChildren(StrongEmphasis(), NodeFactory::strongEmphasis),
            TestCase.withoutChildren(Text("text")) {
                text("text")
            },
            TestCase.withoutChildren(ThematicBreak(), NodeFactory::divider)
        )

        data class TestCase<M : CommonMarkNode, C : Node, out N : Node>(
            val markdownNode: M,
            val nodeFactoryFunction: NodeFactory.(List<C>) -> N,
            val childParserFunction: (MarkdownChildParser.(M) -> List<C>)?
        ) {
            val parsedChildren: List<C> = arrayListOf()
            val node: N = DefaultNodeFactory.nodeFactoryFunction(parsedChildren)

            override fun toString(): String = markdownNode::class.simpleName.orEmpty()

            companion object {
                fun <M : CommonMarkNode, N : Node> withoutChildren(
                    markdownNode: M,
                    nodeFactoryFunction: NodeFactory.() -> N
                ) = TestCase<_, Nothing, _>(markdownNode, { nodeFactoryFunction() }, null)

                fun <M : CommonMarkNode, N : Node> withSpanChildren(
                    markdownNode: M,
                    nodeFactoryFunction: NodeFactory.(List<Node.Span>) -> N
                ) = TestCase(markdownNode, nodeFactoryFunction, MarkdownChildParser::parseSpanChildren)

                fun <M : CommonMarkNode, N : Node> withBlockChildren(
                    markdownNode: M,
                    nodeFactoryFunction: NodeFactory.(List<Node.Block>) -> N
                ) = TestCase(markdownNode, nodeFactoryFunction, MarkdownChildParser::parseBlockChildren)

                fun <M : CommonMarkNode, N : Node> withListItemChildren(
                    markdownNode: M,
                    nodeFactoryFunction: NodeFactory.(List<NodeListItem>) -> N
                ) = TestCase(markdownNode, nodeFactoryFunction, MarkdownChildParser::parseListItemChildren)
            }
        }
    }
}
