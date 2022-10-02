package nl.jjkester.crt.demo

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import nl.jjkester.crt.demo.main.MainNavigation
import nl.jjkester.crt.demo.markdown.MarkdownShowcase

class DemoActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MaterialTheme(
                colorScheme = if (Build.VERSION.SDK_INT < Build.VERSION_CODES.S) {
                    lightColorScheme()
                } else {
                    dynamicLightColorScheme(this)
                }
            ) {
                MainNavigation(
                    showcases = listOf(
                        MarkdownShowcase,
                    )
                )
            }
        }
    }
}
