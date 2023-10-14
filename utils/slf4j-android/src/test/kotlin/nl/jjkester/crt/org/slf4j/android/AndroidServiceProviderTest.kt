package nl.jjkester.crt.org.slf4j.android

import assertk.all
import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.isInstanceOf
import org.junit.jupiter.api.Test
import org.slf4j.helpers.BasicMarkerFactory
import org.slf4j.spi.MDCAdapter

class AndroidServiceProviderTest {

    private val systemUnderTest = AndroidServiceProvider()

    @Test
    fun getLoggerFactory() {
        val result = systemUnderTest.loggerFactory

        assertThat(result.getLogger("logger name")).all {
            isInstanceOf<AndroidLogger>()
            transform { it.name }.isEqualTo("logger name")
        }
    }

    @Test
    fun getMarkerFactory() {
        assertThat(systemUnderTest.markerFactory).isInstanceOf<BasicMarkerFactory>()
    }

    @Test
    fun getMDCAdapter() {
        assertThat(systemUnderTest.mdcAdapter).isInstanceOf<MDCAdapter>()
    }

    @Test
    fun getRequestedApiVersion() {
        assertThat(systemUnderTest.requestedApiVersion).isEqualTo(BuildConfig.SLF4J_API_VERSION)
    }
}
