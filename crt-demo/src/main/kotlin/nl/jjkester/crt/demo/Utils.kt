package nl.jjkester.crt.demo

import androidx.annotation.RawRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import nl.jjkester.crt.compose.style.RichTextStyle
import nl.jjkester.crt.compose.style.rememberBasicRichTextStyle
import java.io.InputStream

@Composable
fun rememberSnackbarIntentClickHandler(snackbarHostState: SnackbarHostState): (String) -> Unit {
    val uriHandler = LocalUriHandler.current
    val coroutineScope = rememberCoroutineScope()

    return remember(uriHandler, snackbarHostState) {
        {
            coroutineScope.launch {
                val action = snackbarHostState.showSnackbar(
                    message = it,
                    actionLabel = "Open",
                    withDismissAction = true
                )

                if (action == SnackbarResult.ActionPerformed) {
                    uriHandler.openUri(it)
                }
            }
        }
    }
}

@Composable
fun openRawResource(@RawRes id: Int): InputStream {
    val context = LocalContext.current
    return remember(context, id) { context.resources.openRawResource(id) }
}

@Composable
fun rememberMaterialRichTextStyle(): RichTextStyle {
    val colorScheme = MaterialTheme.colorScheme
    val typography = MaterialTheme.typography
    val dividerColor = DividerDefaults.color
    val basicStyle = rememberBasicRichTextStyle(
        color = LocalContentColor.current,
        spacing = PaddingValues(horizontal = 32.dp, vertical = 16.dp)
    )

    return remember(colorScheme, typography) {
        basicStyle.run {
            copy(
                h1 = h1 + typography.displaySmall,
                h2 = h2 + typography.titleLarge,
                h3 = h3 + typography.titleMedium,
                h4 = h4 + typography.titleSmall,
                h5 = h5 + typography.labelMedium,
                h6 = h6 + typography.labelSmall,
                paragraph = paragraph + typography.bodyMedium,
                blockquote = blockquote.run {
                    copy(
                        text = text + typography.bodyMedium,
                        border = BorderStroke(
                            width = DividerDefaults.Thickness * 2,
                            color = dividerColor
                        )
                    )
                },
                divider = BorderStroke(
                    width = DividerDefaults.Thickness,
                    color = dividerColor
                ),
                link = link.copy(color = colorScheme.primary)
            )
        }
    }
}
