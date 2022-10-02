package nl.jjkester.crt.demo.markdown

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import nl.jjkester.crt.demo.R
import nl.jjkester.crt.demo.readRawResource
import nl.jjkester.crt.demo.rememberSnackbarIntentClickHandler
import nl.jjkester.crt.demo.showcases.Example
import nl.jjkester.crt.demo.showcases.Showcase

val MarkdownShowcase = Showcase(
    name = "Markdown",
    description = "Markdown is a lightweight markup language for creating formatted text using a plain-text editor",
    examples = listOf(
        Example(
            name = "CommonMark benchmark",
            description = "The benchmark document of the CommonMark specification"
        ) {
            LazyMarkdown(
                text = readRawResource(R.raw.markdown_commonmark_benchmark),
                contentPadding = PaddingValues(16.dp),
                onClick = rememberSnackbarIntentClickHandler()
            )
        },
        Example(
            name = "GitHub Flavoured Markdown",
            description = "Showing all features in GitHub flavoured Markdown in the same order as the documentation"
        ) {
            Markdown(
                text = readRawResource(R.raw.markdown_gfm_docs),
                modifier = Modifier
                    .verticalScroll(rememberScrollState())
                    .padding(15.dp),
                onClick = rememberSnackbarIntentClickHandler()
            )
        }
    )
)
