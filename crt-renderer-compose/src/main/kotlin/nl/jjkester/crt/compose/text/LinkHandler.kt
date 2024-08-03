package nl.jjkester.crt.compose.text

import androidx.compose.runtime.compositionLocalOf
import nl.jjkester.crt.api.model.Link

/**
 * Determines the action to be performed when a link is clicked.
 */
public fun interface LinkHandler {

    /**
     * Returns the action to be performed when a link to the [destination] is clicked.
     *
     * A `null` return value indicates that the link will be attempted to be opened using
     * [androidx.compose.ui.platform.UriHandler]. When a function is returned, the returned function is called instead.
     */
    public fun getAction(destination: Link.Destination): (() -> Unit)?

    public companion object {

        /**
         * Default link handler that opens all links using [androidx.compose.ui.platform.UriHandler].
         */
        public val Default: LinkHandler = LinkHandler { null }
    }
}

internal val LocalLinkHandler = compositionLocalOf { LinkHandler.Default }
