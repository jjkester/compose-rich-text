package nl.jjkester.crt.demo.main

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import nl.jjkester.crt.demo.R
import nl.jjkester.crt.demo.markdown.Markdown
import nl.jjkester.crt.demo.readRawResource
import nl.jjkester.crt.demo.rememberIntentClickHandler
import nl.jjkester.crt.demo.rememberMaterialRichTextStyles

@Composable
fun Readme(modifier: Modifier = Modifier) {
    val (documentStyles, textStyles) = rememberMaterialRichTextStyles()

    Markdown(
        text = readRawResource(id = R.raw.main_readme),
        documentStyles = documentStyles,
        textStyles = textStyles,
        modifier = modifier,
        onClick = rememberIntentClickHandler()
    )
}
