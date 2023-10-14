package nl.jjkester.crt.org.slf4j.android

import android.util.Log
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource
import org.mockito.MockedStatic
import org.mockito.Mockito
import org.slf4j.Marker
import org.slf4j.MarkerFactory

internal class AndroidLoggerTest {

    private val systemUnderTest = AndroidLogger("tag")

    @ParameterizedTest
    @MethodSource("testCases")
    fun isEnabled(testCase: TestCase) = with(testCase) {
        logTest {
            systemUnderTest.isEnabled()

            verify { Log.isLoggable("tag", level) }
        }
    }

    @ParameterizedTest
    @MethodSource("testCases")
    fun handleNormalizedLoggingCall(testCase: TestCase) = with(testCase) {
        logTest {
            val throwable = Exception()

            `when`<Boolean> { Log.isLoggable("tag", level) }.thenReturn(true)

            systemUnderTest.log(MarkerFactory.getMarker("marker"), "Message {}", "argument", throwable)

            verify { logged("tag", "Message argument", throwable) }
        }
    }

    companion object {
        @JvmStatic
        private fun testCases() = listOf(
            TestCase(AndroidLogger::isTraceEnabled, AndroidLogger::trace, Log::v, Log.VERBOSE),
            TestCase(AndroidLogger::isDebugEnabled, AndroidLogger::debug, Log::d, Log.DEBUG),
            TestCase(AndroidLogger::isInfoEnabled, AndroidLogger::info, Log::i, Log.INFO),
            TestCase(AndroidLogger::isWarnEnabled, AndroidLogger::warn, Log::w, Log.WARN),
            TestCase(AndroidLogger::isErrorEnabled, AndroidLogger::error, Log::e, Log.ERROR)
        )

        private fun logTest(body: MockedStatic<Log>.() -> Unit) {
            Mockito.mockStatic(Log::class.java).use {
                it.body()
            }
        }
    }

    data class TestCase(
        val isEnabled: AndroidLogger.() -> Boolean,
        val log: AndroidLogger.(Marker, String, Any, Throwable) -> Unit,
        val logged: (String, String, Throwable) -> Unit,
        val level: Int
    )
}
