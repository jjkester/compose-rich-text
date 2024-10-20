package nl.jjkester.crt.dsl.internal

import assertk.all
import assertk.assertThat
import assertk.assertions.hasSize
import assertk.assertions.index
import assertk.assertions.isEmpty
import assertk.assertions.isEqualTo
import assertk.assertions.single
import nl.jjkester.crt.api.model.ListItem
import nl.jjkester.crt.api.model.Paragraph
import nl.jjkester.crt.api.model.Text
import org.junit.jupiter.api.Test

class ListNodeContentBuilderTest {

    @Test
    fun `no items`() {
        val systemUnderTest = ListNodeContentBuilder()

        assertThat(systemUnderTest.children).isEmpty()
    }

    @Test
    fun `one item`() {
        val systemUnderTest = ListNodeContentBuilder().apply { listItem { paragraph { +"Hello, world" } } }

        assertThat(systemUnderTest.children).single()
            .transform { it.childParagraphText }.isEqualTo("Hello, world")
    }

    @Test
    fun `two items`() {
        val systemUnderTest = ListNodeContentBuilder().apply {
            listItem { paragraph { +"Foo" } }
            listItem { paragraph { +"Bar" } }
        }

        assertThat(systemUnderTest.children).all {
            hasSize(2)
            index(0).transform { it.childParagraphText }.isEqualTo("Foo")
            index(1).transform { it.childParagraphText }.isEqualTo("Bar")
        }
    }

    private val ListItem.childParagraphText: String
        get() = children.filterIsInstance<Paragraph>()
            .firstOrNull()
            ?.children
            ?.filterIsInstance<Text>()
            ?.firstOrNull()
            ?.text
            .orEmpty()
}
