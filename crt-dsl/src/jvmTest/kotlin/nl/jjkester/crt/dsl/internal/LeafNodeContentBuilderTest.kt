package nl.jjkester.crt.dsl.internal

import assertk.assertThat
import assertk.assertions.isEmpty
import assertk.assertions.isEqualTo
import org.junit.jupiter.api.Test

class LeafNodeContentBuilderTest {

    @Test
    fun `no calls`() {
        val systemUnderTest = LeafNodeContentBuilder()

        assertThat(systemUnderTest.string).isEmpty()
    }

    @Test
    fun `single call`() {
        val systemUnderTest = LeafNodeContentBuilder().apply { +"Hello, world" }

        assertThat(systemUnderTest.string).isEqualTo("Hello, world")
    }

    @Test
    fun `multiple calls`() {
        val systemUnderTest = LeafNodeContentBuilder().apply {
            +"Hello, "
            +"world"
        }

        assertThat(systemUnderTest.string).isEqualTo("Hello, world")
    }
}
