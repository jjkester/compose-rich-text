package nl.jjkester.crt.api.parser

import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.isNotNull
import kotlinx.io.Source
import kotlinx.io.readString
import nl.jjkester.crt.api.model.Node
import org.junit.jupiter.api.Test
import java.util.UUID

class ParserJvmTest {

    @Test
    fun `parse input stream`() {
        val parser = TestParser()
        val randomString = UUID.randomUUID().toString()

        randomString.byteInputStream().use { inputStream ->
            assertThat(parser.parse(inputStream))
                .transform { it.source }.isNotNull()
                .transform { it.readString() }.isEqualTo(randomString)
        }
    }

    private class TestParser : Parser<TestParser.Result> {
        override fun parse(input: String): Result = Result(null)

        override fun parse(source: Source): Result = Result(source)

        class Result(val source: Source?) : ParserResult {
            override val rootNode: Node
                get() = error("Not implemented")
            override val metrics: ParserMetrics?
                get() = error("Not implemented")
        }
    }
}
