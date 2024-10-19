package nl.jjkester.crt.api.parser

import assertk.assertThat
import assertk.assertions.isEqualTo
import nl.jjkester.crt.api.annotations.InternalParserApi
import org.junit.jupiter.api.Test
import kotlin.time.Duration.Companion.milliseconds

@OptIn(InternalParserApi::class)
class ParserMetricsTest {

    @Test
    fun totalTime() {
        val systemUnderTest = ParserMetrics(
            fileReadTime = 10.milliseconds,
            sourceParseTime = 100.milliseconds,
            intermediateTransformTime = 1000.milliseconds
        )

        assertThat(systemUnderTest.totalTime).isEqualTo(1110.milliseconds)
    }
}
