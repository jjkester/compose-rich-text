package nl.jjkester.crt.org.slf4j.android

import org.slf4j.ILoggerFactory
import org.slf4j.IMarkerFactory
import org.slf4j.helpers.BasicMDCAdapter
import org.slf4j.helpers.BasicMarkerFactory
import org.slf4j.spi.MDCAdapter
import org.slf4j.spi.SLF4JServiceProvider

internal class AndroidServiceProvider : SLF4JServiceProvider {
    override fun getLoggerFactory(): ILoggerFactory = ILoggerFactory { AndroidLogger(it) }
    override fun getMarkerFactory(): IMarkerFactory = BasicMarkerFactory()
    override fun getMDCAdapter(): MDCAdapter = BasicMDCAdapter()
    override fun getRequestedApiVersion(): String = BuildConfig.SLF4J_API_VERSION
    override fun initialize() {}
}
