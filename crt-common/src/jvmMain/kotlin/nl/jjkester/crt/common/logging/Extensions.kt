package nl.jjkester.crt.common.logging

import org.slf4j.Logger
import org.slf4j.LoggerFactory

public inline val <reified T : Any> T.logger: Logger
    get() = LoggerFactory.getLogger(T::class.java)

public inline fun Logger.trace(throwable: Throwable? = null, lazyMessage: () -> String) {
    if (isTraceEnabled) trace(lazyMessage(), throwable)
}

public inline fun Logger.debug(throwable: Throwable? = null, lazyMessage: () -> String) {
    if (isDebugEnabled) debug(lazyMessage(), throwable)
}

public inline fun Logger.info(throwable: Throwable? = null, lazyMessage: () -> String) {
    if (isInfoEnabled) info(lazyMessage(), throwable)
}

public inline fun Logger.warn(throwable: Throwable? = null, lazyMessage: () -> String) {
    if (isWarnEnabled) warn(lazyMessage(), throwable)
}

public inline fun Logger.error(throwable: Throwable? = null, lazyMessage: () -> String) {
    if (isErrorEnabled) error(lazyMessage(), throwable)
}
