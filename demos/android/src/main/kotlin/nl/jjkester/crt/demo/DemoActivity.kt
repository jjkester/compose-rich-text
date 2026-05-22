package nl.jjkester.crt.demo

import DemoTheme
import android.graphics.Color
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import nl.jjkester.crt.demo.main.MainNavigation
import nl.jjkester.crt.demo.markdown.MarkdownShowcase

class DemoActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge(systemBarStyle, systemBarStyle)
        actionBar?.hide()

        super.onCreate(savedInstanceState)

        setContent {
            val showcases = remember {
                mutableStateListOf(
                    MarkdownShowcase,
                )
            }

            DemoTheme(dynamicColor = false) {
                MainNavigation(showcases = showcases)
            }
        }
    }

    companion object {
        private val systemBarStyle = SystemBarStyle.auto(
            lightScrim = Color.TRANSPARENT,
            darkScrim = Color.TRANSPARENT
        )
    }
}
