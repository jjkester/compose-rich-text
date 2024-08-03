package nl.jjkester.crt.demo.showcases

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.exclude
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScaffoldDefaults
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import nl.jjkester.crt.demo.LocalSnackbarHostState

@Composable
fun ShowcaseScaffold(
    title: String,
    onNavigateBack: () -> Unit,
    content: @Composable () -> Unit
) {
    val topBarScrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
    val snackbarHostState = remember { SnackbarHostState() }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = title)
                },
                navigationIcon = {
                    IconButton(
                        onClick = onNavigateBack
                    ) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, "Back")
                    }
                },
                scrollBehavior = topBarScrollBehavior
            )
        },
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        },
        contentWindowInsets = ScaffoldDefaults.contentWindowInsets.exclude(WindowInsets.navigationBars)
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .consumeWindowInsets(paddingValues)
                .padding(paddingValues)
                .nestedScroll(topBarScrollBehavior.nestedScrollConnection)
        ) {
            CompositionLocalProvider(
                LocalSnackbarHostState provides snackbarHostState,
                content = content
            )
        }
    }
}

val showcaseContentPadding: PaddingValues
    @Composable
    get() = PaddingValues(16.dp) + WindowInsets.navigationBars.asPaddingValues()

private operator fun PaddingValues.plus(other: PaddingValues): PaddingValues = PaddingValues(
    start = this.calculateStartPadding(LayoutDirection.Ltr) +
        other.calculateStartPadding(LayoutDirection.Ltr),
    top = this.calculateTopPadding() + other.calculateTopPadding(),
    end = this.calculateEndPadding(LayoutDirection.Ltr) +
        other.calculateEndPadding(LayoutDirection.Ltr),
    bottom = this.calculateBottomPadding() + other.calculateBottomPadding(),
)
