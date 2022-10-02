package nl.jjkester.crt.demo.main

import androidx.compose.runtime.Composable
import nl.jjkester.crt.demo.R
import nl.jjkester.crt.demo.markdown.Markdown
import nl.jjkester.crt.demo.readRawResource
import nl.jjkester.crt.demo.rememberIntentClickHandler

@Composable
fun Readme() {
    Markdown(
        text = readRawResource(id = R.raw.main_readme),
        onClick = rememberIntentClickHandler()
    )
}
