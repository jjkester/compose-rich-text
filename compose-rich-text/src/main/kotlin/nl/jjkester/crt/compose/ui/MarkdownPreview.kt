package nl.jjkester.crt.compose.ui

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview

@Preview(showBackground = true)
@Composable
private fun InlineMarkdownPreview() {
    val markdown = """
        This is a simple test. This test contains *emphasis*, __strong emphasis__ and _a **combination**_ of those.
        This text follows a soft line break,\
        but a hard line break was inserted after the comma.
        `Some code is _also_ expected`, even **strong `code`**.
         Finally we [close](link1) with [a _formatted_ link](link2).
    """.trimIndent()

    Box(Modifier.verticalScroll(rememberScrollState())) {
        InlineMarkdown(
            text = markdown,
            onClick = toastFunction()
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun MarkdownDocumentPreview() {
    val markdown = """
        # Lorem ipsum dolor sit amet, consecteur [email](mailto:me@example.com)
        
        Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore 
        magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea 
        commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat 
        nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit 
        anim id est laborum.
        
        > Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore 
        > magna aliqua.
        >
        > > Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.
        >
        > ## Heading in blockquote
        > Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur.
        
        Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.
        
        [lipsum.com](https://www.lipsum.com/)
        
        1. One
        1. Two
        3) Three
        
        - One
        - Two
        + Three
        
        ---
        
            Code
            Line 2 of code
        
        Paragraph
        
        ```kotlin
        val code = Code()
        ```
        
        1. One
        2. Two
           1. Two.One
           2. Two.Two
              - Two.Two.One
    """.trimIndent()

    Box(Modifier.verticalScroll(rememberScrollState())) {
        Markdown(
            text = markdown,
            onClick = toastFunction()
        )
    }
}

@Composable
private fun toastFunction(): (String) -> Unit {
    val context = LocalContext.current
    return { Toast.makeText(context, it, Toast.LENGTH_SHORT).show() }
}
