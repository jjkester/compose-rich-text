package nl.jjkester.crt.demo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import nl.jjkester.crt.demo.main.MainNavigation
import nl.jjkester.crt.demo.markdown.MarkdownShowcase

class DemoActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val showcases = remember {
                mutableStateListOf(
                    MarkdownShowcase,
                )
            }

            DemoTheme {
                MainNavigation(
                    showcases = showcases
                )
            }
        }
    }
}
