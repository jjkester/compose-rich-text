package nl.jjkester.crt.demo.markdown

import nl.jjkester.crt.demo.R
import nl.jjkester.crt.demo.readRawResource
import nl.jjkester.crt.demo.rememberMaterialRichTextStyles
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
            val (documentStyles, textStyles) = rememberMaterialRichTextStyles()
            Markdown(
                text = readRawResource(R.raw.markdown_commonmark_benchmark),
                documentStyles = documentStyles,
                textStyles = textStyles,
                onClick = rememberSnackbarIntentClickHandler()
            )
        },
        Example(
            name = "GitHub Flavoured Markdown",
            description = "Showing all features in GitHub flavoured Markdown in the same order as the documentation"
        ) {
            val (documentStyles, textStyles) = rememberMaterialRichTextStyles()
            Markdown(
                text = readRawResource(R.raw.markdown_gfm_docs),
                documentStyles = documentStyles,
                textStyles = textStyles,
                onClick = rememberSnackbarIntentClickHandler()
            )
        }
    )
)
