package nl.jjkester.crt.api.factory

import nl.jjkester.crt.api.annotations.InternalFactoryApi
import nl.jjkester.crt.api.model.Heading
import nl.jjkester.crt.api.model.Language
import nl.jjkester.crt.api.model.Link
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify

@OptIn(InternalFactoryApi::class)
class NodeFactoryKtTest {

    private val nodeFactory = mock<NodeFactory>()

    @Test
    fun code() {
        val content = "some code"
        val languageHint = "mylang"
        val metadata = TestUtils.metadata()

        nodeFactory.code(content, languageHint, metadata)

        verify(nodeFactory).code(content, Language(languageHint), metadata)
    }

    @Test
    fun `code block`() {
        val content = "some code"
        val languageHint = "mylang"
        val metadata = TestUtils.metadata()

        nodeFactory.codeBlock(content, languageHint, metadata)

        verify(nodeFactory).codeBlock(content, Language(languageHint), metadata)
    }

    @ParameterizedTest(name = "{displayName} [{0}]")
    @ValueSource(ints = [1, 2, 3, 4, 5, 6])
    fun `heading with valid level`(level: Int) {
        val children = TestUtils.spans()
        val metadata = TestUtils.metadata()

        nodeFactory.heading(level, children, metadata)

        verify(nodeFactory).heading(Heading.Level.fromIntOrNull(level)!!, children, metadata)
    }

    @ParameterizedTest(name = "{displayName} {0}")
    @ValueSource(ints = [0, 7])
    fun `heading with invalid level`(level: Int) {
        val children = TestUtils.spans()
        val metadata = TestUtils.metadata()

        nodeFactory.heading(level, children, metadata)

        verify(nodeFactory).heading(Heading.Level.Six, children, metadata)
    }

    @Test
    fun `link with destination href, children and metadata`() {
        val destinationHref = "https://github.com"
        val children = TestUtils.spans()
        val metadata = TestUtils.metadata()

        nodeFactory.link(destinationHref, children, metadata)

        verify(nodeFactory).link(Link.Destination(destinationHref), children, metadata)
    }

}
