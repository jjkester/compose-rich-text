package nl.jjkester.crt.demo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
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
                Scaffold(modifier = Modifier.safeDrawingPadding()) { contentPadding ->
                    Box(modifier = Modifier.padding(contentPadding)) {
                        MainNavigation(
                            showcases = showcases
                        )
                    }
                }
            }
        }
    }
}
