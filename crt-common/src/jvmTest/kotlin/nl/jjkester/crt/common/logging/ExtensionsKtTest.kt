package nl.jjkester.crt.common.logging

import assertk.assertThat
import assertk.assertions.isInstanceOf
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource
import org.mockito.kotlin.*
import org.slf4j.Logger
import org.slf4j.helpers.NOPLogger

private typealias LoggerConfig = Logger.() -> Boolean
private typealias LoggerFunction = Logger.(String, Throwable?) -> Unit
private typealias LoggerExtension = Logger.(Throwable?, () -> String) -> Unit

class ExtensionsKtTest {

    private val mockLogger = mock<Logger>()

    @Test
    fun logger() {
        // No providers registered, so expect a NOP implementation as a proof of the extension function
        assertThat(logger).isInstanceOf<NOPLogger>()
    }

    @ParameterizedTest(name = "{displayName} {0}")
    @MethodSource("testCases")
    fun `log level not enabled`(testCase: TestCase) = with(testCase) {
        reset(mockLogger)
        mockLogger.stub { on(config) doReturn false }

        mockLogger.extension(Exception()) { "Message" }

        verify(mockLogger).config()
        verifyNoMoreInteractions(mockLogger)
    }

    @ParameterizedTest(name = "{displayName} {0}")
    @MethodSource("testCases")
    fun message(testCase: TestCase) = with(testCase) {
        reset(mockLogger)
        mockLogger.stub { on(config) doReturn true }

        mockLogger.extension(null) { "Message" }

        verify(mockLogger).function("Message", null)
        verify(mockLogger).config()
        verifyNoMoreInteractions(mockLogger)
    }


    @ParameterizedTest(name = "{displayName} {0}")
    @MethodSource("testCases")
    fun `throwable and message`(testCase: TestCase) = with(testCase) {
        val exception = Exception()

        reset(mockLogger)
        mockLogger.stub { on(config) doReturn true }

        mockLogger.extension(exception) { "Message" }

        verify(mockLogger).function("Message", exception)
        verify(mockLogger).config()
        verifyNoMoreInteractions(mockLogger)
    }

    companion object {
        @JvmStatic
        private fun testCases() = listOf(
            TestCase("trace", Logger::isTraceEnabled, Logger::trace, Logger::trace),
            TestCase("debug", Logger::isDebugEnabled, Logger::debug, Logger::debug),
            TestCase("info", Logger::isInfoEnabled, Logger::info, Logger::info),
            TestCase("warn", Logger::isWarnEnabled, Logger::warn, Logger::warn),
            TestCase("error", Logger::isErrorEnabled, Logger::error, Logger::error)
        )
    }

    data class TestCase(
        val name: String,
        val config: LoggerConfig,
        val function: LoggerFunction,
        val extension: LoggerExtension
    ) {
        override fun toString(): String = name
    }
}
