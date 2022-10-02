package nl.jjkester.crt.demo

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.annotation.RawRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.DividerDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarResult
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import nl.jjkester.crt.compose.style.BlockquoteStyle
import nl.jjkester.crt.compose.style.RichTextStyle
import nl.jjkester.crt.compose.style.rememberBasicRichTextStyle

@Composable
fun rememberIntentClickHandler(uri: String): () -> Unit {
    val context = LocalContext.current
    return remember(context) { { context.openLink(uri) } }
}

@Composable
fun rememberIntentClickHandler(): (String) -> Unit {
    val context = LocalContext.current
    return remember(context) { { context.openLink(it) } }
}

@Composable
fun rememberSnackbarIntentClickHandler(): (String) -> Unit {
    val context = LocalContext.current
    val snackbarHostState = LocalSnackbarHostState.current
    val coroutineScope = rememberCoroutineScope()

    return remember(context, snackbarHostState) {
        {
            coroutineScope.launch {
                val action = snackbarHostState.showSnackbar(
                    message = it,
                    actionLabel = "Open",
                    withDismissAction = true
                )

                if (action == SnackbarResult.ActionPerformed) {
                    context.openLink(it)
                }
            }
        }
    }
}

fun Context.openLink(uri: String, fallback: () -> Unit = {}) {
    try {
        startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(uri)))
    } catch (e: ActivityNotFoundException) {
        fallback()
    }
}

@Composable
fun readRawResource(@RawRes id: Int): String =
    LocalContext.current.resources.openRawResource(id)
        .reader()
        .use { it.readText() }

@Composable
fun rememberMaterialRichTextStyle(): RichTextStyle {
    val colorScheme = MaterialTheme.colorScheme
    val typography = MaterialTheme.typography
    val dividerColor = DividerDefaults.color
    val basicStyle = rememberBasicRichTextStyle(spacing = PaddingValues(16.dp))

    return remember(colorScheme, typography) {
        basicStyle.copy(
            h1 = typography.displaySmall,
            h2 = typography.titleLarge,
            h3 = typography.titleMedium,
            h4 = typography.titleSmall,
            h5 = typography.labelMedium,
            h6 = typography.labelSmall,
            paragraph = typography.bodyMedium,
            blockquote = BlockquoteStyle(
                text = typography.bodyMedium.run {
                    copy(color = color.copy(alpha = .66f))
                },
                border = BorderStroke(
                    width = DividerDefaults.Thickness * 2,
                    color = dividerColor
                )
            ),
            divider = BorderStroke(
                width = DividerDefaults.Thickness,
                color = dividerColor
            ),
            link = basicStyle.link.copy(
                color = colorScheme.primary
            )
        )
    }
}
