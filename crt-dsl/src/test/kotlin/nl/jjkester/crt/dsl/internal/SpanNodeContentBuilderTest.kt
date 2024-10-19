package nl.jjkester.crt.dsl.internal

import assertk.all
import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.isInstanceOf
import assertk.assertions.single
import nl.jjkester.crt.api.model.Code
import nl.jjkester.crt.api.model.Language
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

class SpanNodeContentBuilderTest {

    @Nested
    inner class `Code function` {

        @Test
        fun string() {
            val systemUnderTest = SpanNodeContentBuilder().apply { code("<html></html>", "html") }

            assertThat(systemUnderTest.children).single().isInstanceOf<Code>().all {
                transform { it.content }.isEqualTo("<html></html>")
                transform { it.languageHint }.isEqualTo(Language("html"))
            }
        }

        @Test
        fun builder() {
            val systemUnderTest = SpanNodeContentBuilder().apply {
                code("html") {
                    +"<html>"
                    +"</html>"
                }
            }

            assertThat(systemUnderTest.children).single().isInstanceOf<Code>().all {
                transform { it.content }.isEqualTo("<html></html>")
                transform { it.languageHint }.isEqualTo(Language("html"))
            }
        }
    }
}
