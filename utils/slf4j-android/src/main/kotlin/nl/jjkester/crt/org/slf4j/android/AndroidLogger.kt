package nl.jjkester.crt.org.slf4j.android

import android.util.Log
import org.slf4j.Marker
import org.slf4j.event.Level
import org.slf4j.helpers.LegacyAbstractLogger
import org.slf4j.helpers.MessageFormatter

internal class AndroidLogger(private val tag: String) : LegacyAbstractLogger() {
    override fun isTraceEnabled(): Boolean = isLoggable(Log.VERBOSE)
    override fun isDebugEnabled(): Boolean = isLoggable(Log.DEBUG)
    override fun isInfoEnabled(): Boolean = isLoggable(Log.INFO)
    override fun isWarnEnabled(): Boolean = isLoggable(Log.WARN)
    override fun isErrorEnabled(): Boolean = isLoggable(Log.ERROR)
    private fun isLoggable(level: Int) = Log.isLoggable(tag, level)
    override fun getFullyQualifiedCallerName(): String? = null
    override fun handleNormalizedLoggingCall(
        level: Level?,
        marker: Marker?,
        messagePattern: String?,
        arguments: Array<out Any>?,
        throwable: Throwable?
    ) {
        val message = MessageFormatter.basicArrayFormat(messagePattern, arguments)
        when (level) {
            Level.TRACE -> Log.v(tag, message, throwable)
            Level.DEBUG -> Log.d(tag, message, throwable)
            Level.INFO -> Log.i(tag, message, throwable)
            Level.WARN -> Log.w(tag, message, throwable)
            Level.ERROR -> Log.e(tag, message, throwable)
            null -> Unit
        }
    }
}
