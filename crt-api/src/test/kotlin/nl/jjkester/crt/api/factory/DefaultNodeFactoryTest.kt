package nl.jjkester.crt.api.factory

import assertk.all
import assertk.assertThat
import assertk.assertions.isEmpty
import assertk.assertions.isEqualTo
import assertk.assertions.isNull
import assertk.assertions.isSameInstanceAs
import nl.jjkester.crt.api.annotations.InternalFactoryApi
import nl.jjkester.crt.api.model.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource

@OptIn(InternalFactoryApi::class)
class DefaultNodeFactoryTest {

    private val systemUnderTest = DefaultNodeFactory

    @Test
    fun `blockquote with default values`() {
        val result = systemUnderTest.blockquote()

        assertThat(result).all {
            transform { it.children }.isEmpty()
            transform { it.metadata }.isNull()
        }
    }

    @Test
    fun `blockquote with children and metadata`() {
        val children = TestUtils.blocks()
        val metadata = TestUtils.metadata()

        val result = systemUnderTest.blockquote(children, metadata)

        assertThat(result).all {
            transform { it.children }.isEqualTo(children)
            transform { it.metadata }.isSameInstanceAs(metadata)
        }
    }

    @Test
    fun `code with content`() {
        val content = "some code"

        val result = systemUnderTest.code(content)

        assertThat(result).all {
            transform { it.content }.isEqualTo(content)
            transform { it.languageHint }.isNull()
            transform { it.metadata }.isNull()
            transform { it.children }.isEmpty()
        }
    }

    @Test
    fun `code with content, language and metadata`() {
        val content = "some code"
        val language = Language("mylang")
        val metadata = TestUtils.metadata()

        val result = systemUnderTest.code(content, language, metadata)

        assertThat(result).all {
            transform { it.content }.isEqualTo(content)
            transform { it.languageHint }.isEqualTo(language)
            transform { it.metadata }.isSameInstanceAs(metadata)
            transform { it.children }.isEmpty()
        }
    }

    @Test
    fun `code block with content`() {
        val content = "some code"

        val result = systemUnderTest.codeBlock(content)

        assertThat(result).all {
            transform { it.content }.isEqualTo(content)
            transform { it.languageHint }.isNull()
            transform { it.metadata }.isNull()
            transform { it.children }.isEmpty()
        }
    }

    @Test
    fun `code block with content, language and metadata`() {
        val content = "some code"
        val language = Language("mylang")
        val metadata = TestUtils.metadata()

        val result = systemUnderTest.codeBlock(content, language, metadata)

        assertThat(result).all {
            transform { it.content }.isEqualTo(content)
            transform { it.languageHint }.isEqualTo(language)
            transform { it.metadata }.isSameInstanceAs(metadata)
            transform { it.children }.isEmpty()
        }
    }

    @Test
    fun `container with default values`() {
        val result = systemUnderTest.container()

        assertThat(result).all {
            transform { it.children }.isEmpty()
            transform { it.metadata }.isNull()
        }
    }

    @Test
    fun `container with children and metadata`() {
        val children = TestUtils.blocks()
        val metadata = TestUtils.metadata()

        val result = systemUnderTest.container(children, metadata)

        assertThat(result).all {
            transform { it.children }.isEqualTo(children)
            transform { it.metadata }.isSameInstanceAs(metadata)
        }
    }

    @Test
    fun `divider with default values`() {
        val result = systemUnderTest.divider()

        assertThat(result).all {
            transform { it.metadata }.isNull()
            transform { it.children }.isEmpty()
        }
    }

    @Test
    fun `divider with metadata`() {
        val metadata = TestUtils.metadata()

        val result = systemUnderTest.divider(metadata)

        assertThat(result).all {
            transform { it.metadata }.isSameInstanceAs(metadata)
            transform { it.children }.isEmpty()
        }
    }

    @Test
    fun `emphasis with default values`() {
        val result = systemUnderTest.emphasis()

        assertThat(result).all {
            transform { it.children }.isEmpty()
            transform { it.metadata }.isNull()
        }
    }

    @Test
    fun `emphasis with children and metadata`() {
        val children = TestUtils.spans()
        val metadata = TestUtils.metadata()

        val result = systemUnderTest.emphasis(children, metadata)

        assertThat(result).all {
            transform { it.children }.isEqualTo(children)
            transform { it.metadata }.isSameInstanceAs(metadata)
        }
    }

    @ParameterizedTest(name = "{displayName} {0}")
    @MethodSource("headingLevels")
    fun `heading with level`(level: Heading.Level) {
        val result = systemUnderTest.heading(level)

        assertThat(result).all {
            transform { it.level }.isEqualTo(level)
            transform { it.children }.isEmpty()
            transform { it.metadata }.isNull()
        }
    }

    @ParameterizedTest(name = "{displayName} {0}")
    @MethodSource("headingLevels")
    fun `heading with level, children and metadata`(level: Heading.Level) {
        val children = TestUtils.spans()
        val metadata = TestUtils.metadata()

        val result = systemUnderTest.heading(level, children, metadata)

        assertThat(result).all {
            transform { it.level }.isEqualTo(level)
            transform { it.children }.isEqualTo(children)
            transform { it.metadata }.isSameInstanceAs(metadata)
        }
    }

    @Test
    fun `link with destination`() {
        val destination = Link.Destination("https://github.com")

        val result = systemUnderTest.link(destination)

        assertThat(result).all {
            transform { it.destination }.isEqualTo(destination)
            transform { it.children }.isEmpty()
            transform { it.metadata }.isNull()
        }
    }

    @Test
    fun `link with destination, children and metadata`() {
        val destination = Link.Destination("https://github.com")
        val children = TestUtils.spans()
        val metadata = TestUtils.metadata()

        val result = systemUnderTest.link(destination, children, metadata)

        assertThat(result).all {
            transform { it.destination }.isEqualTo(destination)
            transform { it.children }.isEqualTo(children)
            transform { it.metadata }.isSameInstanceAs(metadata)
        }
    }

    @Test
    fun `list item with default values`() {
        val result = systemUnderTest.listItem()

        assertThat(result).all {
            transform { it.children }.isEmpty()
            transform { it.metadata }.isNull()
        }
    }

    @Test
    fun `list item with children and metadata`() {
        val children = TestUtils.blocks()
        val metadata = TestUtils.metadata()

        val result = systemUnderTest.listItem(children, metadata)

        assertThat(result).all {
            transform { it.children }.isEqualTo(children)
            transform { it.metadata }.isSameInstanceAs(metadata)
        }
    }

    @Test
    fun `ordered list with default values`() {
        val result = systemUnderTest.orderedList()

        assertThat(result).all {
            transform { it.children }.isEmpty()
            transform { it.metadata }.isNull()
        }
    }

    @Test
    fun `ordered list with children and metadata`() {
        val children = List(3) { ListItem(emptyList(), null) }
        val metadata = TestUtils.metadata()

        val result = systemUnderTest.orderedList(children, metadata)

        assertThat(result).all {
            transform { it.children }.isEqualTo(children)
            transform { it.metadata }.isSameInstanceAs(metadata)
        }
    }

    @Test
    fun `paragraph with default values`() {
        val result = systemUnderTest.paragraph()

        assertThat(result).all {
            transform { it.children }.isEmpty()
            transform { it.metadata }.isNull()
        }
    }

    @Test
    fun `paragraph with children and metadata`() {
        val children = TestUtils.spans()
        val metadata = TestUtils.metadata()

        val result = systemUnderTest.paragraph(children, metadata)

        assertThat(result).all {
            transform { it.children }.isEqualTo(children)
            transform { it.metadata }.isSameInstanceAs(metadata)
        }
    }

    @Test
    fun `strong emphasis with default values`() {
        val result = systemUnderTest.strongEmphasis()

        assertThat(result).all {
            transform { it.children }.isEmpty()
            transform { it.metadata }.isNull()
        }
    }

    @Test
    fun `strong emphasis with children and metadata`() {
        val children = TestUtils.spans()
        val metadata = TestUtils.metadata()

        val result = systemUnderTest.strongEmphasis(children, metadata)

        assertThat(result).all {
            transform { it.children }.isEqualTo(children)
            transform { it.metadata }.isSameInstanceAs(metadata)
        }
    }

    @Test
    fun `text with content`() {
        val content = "Hello, world"

        val result = systemUnderTest.text(content)

        assertThat(result).all {
            transform { it.text }.isEqualTo(content)
            transform { it.metadata }.isNull()
            transform { it.children }.isEmpty()
        }
    }

    @Test
    fun `text with content and metadata`() {
        val content = "Hello, world"
        val metadata = TestUtils.metadata()

        val result = systemUnderTest.text(content, metadata)

        assertThat(result).all {
            transform { it.text }.isEqualTo(content)
            transform { it.metadata }.isSameInstanceAs(metadata)
            transform { it.children }.isEmpty()
        }
    }

    @Test
    fun `unordered list with default values`() {
        val result = systemUnderTest.unorderedList()

        assertThat(result).all {
            transform { it.children }.isEmpty()
            transform { it.metadata }.isNull()
        }
    }

    @Test
    fun `unordered list with children and metadata`() {
        val children = List(3) { ListItem(emptyList(), null) }
        val metadata = TestUtils.metadata()

        val result = systemUnderTest.unorderedList(children, metadata)

        assertThat(result).all {
            transform { it.children }.isEqualTo(children)
            transform { it.metadata }.isSameInstanceAs(metadata)
        }
    }

    companion object {

        @JvmStatic
        private fun headingLevels() = TestUtils.headingLevels()
    }
}
