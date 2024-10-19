package nl.jjkester.crt.api.annotations

/**
 * Marker for APIs that should only be called by parser implementations.
 */
@RequiresOptIn(
    message = "This is an internal API for factory callers. Use of this API outside of this purpose is highly discouraged.",
    level = RequiresOptIn.Level.ERROR
)
public annotation class InternalFactoryApi

/**
 * Marker for APIs that should only be called by parser implementations.
 */
@RequiresOptIn(
    message = "This is an internal API for parser implementations. Use of this API outside of this purpose is highly discouraged.",
    level = RequiresOptIn.Level.ERROR
)
public annotation class InternalParserApi

/**
 * Marker for APIs that should only be called by renderer implementations.
 */
@RequiresOptIn(
    message = "This is an internal API for renderer implementations. Use of this API outside of this purpose is highly discouraged.",
    level = RequiresOptIn.Level.ERROR
)
public annotation class InternalRendererApi
