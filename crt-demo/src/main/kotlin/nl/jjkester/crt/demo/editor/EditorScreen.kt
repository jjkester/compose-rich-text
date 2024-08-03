package nl.jjkester.crt.demo.editor

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Button
import androidx.compose.material3.FilledTonalIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.min
import nl.jjkester.crt.api.parser.Parser
import nl.jjkester.crt.compose.RichText
import nl.jjkester.crt.compose.rememberRichTextState
import nl.jjkester.crt.demo.rememberMaterialRichTextStyle
import nl.jjkester.crt.demo.rememberSnackbarIntentClickHandler
import nl.jjkester.crt.demo.showcases.showcaseContentPadding
import nl.jjkester.crt.markdown.MarkdownParserFactory

@Composable
fun EditorScreen(editorFormat: EditorFormat) {
    val (value, setValue) = rememberSaveable { mutableStateOf("") }

    BoxWithConstraints(modifier = Modifier.fillMaxSize()) {
        val isHorizontal = rememberUpdatedState(maxWidth > maxHeight).value

        val (layoutState, setLayoutState) = rememberSaveable(stateSaver = EditorLayoutStateSaver) {
            mutableStateOf(EditorLayoutState.Editor)
        }

        val editorWeight by animateFloatAsState(
            targetValue = if (layoutState.showEditor) 1f else 0f,
            animationSpec = spring(),
            label = "editorWeight"
        )
        val previewWeight by animateFloatAsState(
            targetValue = if (layoutState.showPreview) 1f else 0f,
            animationSpec = spring(),
            label = "previewWeight"
        )

        if (isHorizontal) {
            val density = LocalDensity.current
            val (editorButtonsHeight, setEditorButtonsHeight) = remember { mutableStateOf(0.dp) }

            Row(modifier = Modifier.fillMaxSize()) {
                if (editorWeight > 0) {
                    EditorPane(
                        value = value,
                        onValueChange = setValue,
                        modifier = Modifier
                            .fillMaxHeight()
                            .weight(editorWeight),
                        contentPadding = PaddingValues(bottom = editorButtonsHeight)
                    )
                }
                if (previewWeight > 0) {
                    PreviewPane(
                        source = value,
                        editorFormat = editorFormat,
                        modifier = Modifier
                            .fillMaxHeight()
                            .weight(previewWeight),
                        contentPadding = PaddingValues(bottom = editorButtonsHeight)
                    )
                }
            }
            EditorButtons(
                layoutState = layoutState,
                isHorizontal = isHorizontal,
                onLayoutStateChange = setLayoutState,
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .onSizeChanged { size ->
                        with(density) {
                            setEditorButtonsHeight(size.height.toDp() - min(size.height.toDp(), 16.dp))
                        }
                    }
            )
        } else {
            Column(modifier = Modifier.fillMaxSize()) {
                if (editorWeight > 0) {
                    EditorPane(
                        value = value,
                        onValueChange = setValue,
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(editorWeight)
                    )
                }
                if (previewWeight > 0) {
                    PreviewPane(
                        source = value,
                        editorFormat = editorFormat,
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(previewWeight)
                    )
                }
                EditorButtons(
                    layoutState = layoutState,
                    isHorizontal = isHorizontal,
                    onLayoutStateChange = setLayoutState
                )
            }
        }
    }
}

@Composable
private fun EditorButtons(
    layoutState: EditorLayoutState,
    isHorizontal: Boolean,
    onLayoutStateChange: (EditorLayoutState) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.End),
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp)
            .safeDrawingPadding()
    ) {
        Button(
            onClick = { onLayoutStateChange(layoutState.toggle()) },
            modifier = Modifier.weight(1f)
        ) {
            AnimatedContent(
                targetState = layoutState,
                label = "toggleButtonText"
            ) { targetLayoutState ->
                Text(
                    text = when (targetLayoutState) {
                        is EditorLayoutState.Editor -> "Show rendered result"
                        is EditorLayoutState.Preview -> "Back to editor"
                        is EditorLayoutState.Split -> "Close preview"
                    }
                )
            }
        }

        FilledTonalIconButton(
            onClick = { onLayoutStateChange(layoutState.split()) }
        ) {
            Crossfade(
                targetState = layoutState.showPreview,
                label = "splitButtonIcon"
            ) { targetIsSplit ->
                Icon(
                    imageVector = Icons.Default.run {
                        if (targetIsSplit) {
                            if (isHorizontal) Icons.AutoMirrored.Filled.KeyboardArrowRight else KeyboardArrowDown
                        } else {
                            if (isHorizontal) Icons.AutoMirrored.Filled.KeyboardArrowLeft else KeyboardArrowUp
                        }
                    },
                    contentDescription = "Side-by-side"
                )
            }
        }
    }
}

@Composable
private fun EditorPane(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp)
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isFocused = interactionSource.collectIsFocusedAsState().value
    val textStyle = TextStyle() + rememberMaterialRichTextStyle().code

    BasicTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier,
        textStyle = textStyle,
        interactionSource = interactionSource,
        cursorBrush = SolidColor(textStyle.color)
    ) { innerTextField ->
        Surface(tonalElevation = 8.dp) {
            Box(
                modifier = Modifier
                    .verticalScroll(rememberScrollState())
                    .consumeWindowInsets(contentPadding)
                    .padding(showcaseContentPadding)
                    .padding(contentPadding)
            ) {
                innerTextField()
                AnimatedVisibility(
                    visible = value.isEmpty() && !isFocused,
                    enter = fadeIn(),
                    exit = fadeOut()
                ) {
                    Text(
                        text = "(Start editing here)",
                        style = textStyle.run { copy(color = color.run { copy(alpha = alpha * .7f) }) }
                    )
                }
            }
        }
    }
}

@Composable
private fun PreviewPane(
    source: String,
    editorFormat: EditorFormat,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp)
) {
    val parserFactory = rememberParserFactory(editorFormat)
    val snackbarHostState = remember { SnackbarHostState() }

    Scaffold(
        modifier = modifier,
        snackbarHost = { SnackbarHost(snackbarHostState) },
        contentWindowInsets = WindowInsets(0, 0, 0, 0)
    ) { paddingValues ->
        Surface(
            color = MaterialTheme.colorScheme.background
        ) {
            RichText(
                state = rememberRichTextState(source, parserFactory),
                onClick = rememberSnackbarIntentClickHandler(snackbarHostState),
                richTextStyle = rememberMaterialRichTextStyle(),
                modifier = Modifier
                    .padding(paddingValues)
                    .verticalScroll(rememberScrollState())
                    .consumeWindowInsets(contentPadding)
                    .padding(showcaseContentPadding)
                    .padding(contentPadding)
            )
        }
    }
}

@Composable
private fun rememberParserFactory(editorFormat: EditorFormat): () -> Parser<*> = remember(editorFormat) {
    when (editorFormat) {
        EditorFormat.Markdown -> MarkdownParserFactory()::create
    }
}

@Preview
@Composable
private fun EditorScreenPreview() {
    EditorScreen(EditorFormat.entries.first())
}
