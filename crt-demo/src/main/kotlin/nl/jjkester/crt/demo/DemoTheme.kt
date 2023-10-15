package nl.jjkester.crt.demo

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.googlefonts.Font
import androidx.compose.ui.text.googlefonts.GoogleFont

@Composable
fun DemoTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = if (Build.VERSION.SDK_INT < Build.VERSION_CODES.S) {
            lightColorScheme()
        } else {
            if (isSystemInDarkTheme()) {
                dynamicDarkColorScheme(LocalContext.current)
            } else {
                dynamicLightColorScheme(LocalContext.current)
            }
        },
        typography = Typography().run {
            copy(
                displayLarge = displayLarge.copy(fontFamily = SourceSans, fontWeight = FontWeight.Bold),
                displayMedium = displayMedium.copy(fontFamily = SourceSans, fontWeight = FontWeight.Bold),
                displaySmall = displaySmall.copy(fontFamily = SourceSans, fontWeight = FontWeight.Bold),
                headlineLarge = headlineLarge.copy(fontFamily = SourceSans, fontWeight = FontWeight.Bold),
                headlineMedium = headlineMedium.copy(fontFamily = SourceSans, fontWeight = FontWeight.Bold),
                headlineSmall = headlineSmall.copy(fontFamily = SourceSans, fontWeight = FontWeight.Bold),
                titleLarge = titleLarge.copy(fontFamily = SourceSans, fontWeight = FontWeight.Bold),
                titleMedium = titleMedium.copy(fontFamily = SourceSans, fontWeight = FontWeight.Bold),
                bodyLarge = bodyLarge.copy(fontFamily = SourceSerif),
                bodyMedium = bodyMedium.copy(fontFamily = SourceSerif),
                bodySmall = bodySmall.copy(fontFamily = SourceSerif),
                labelLarge = labelLarge.copy(fontFamily = SourceSans),
                labelMedium = labelMedium.copy(fontFamily = SourceSans),
                labelSmall = labelSmall.copy(fontFamily = SourceSans),
            )
        },
        content = content
    )
}

private val fontProvider = GoogleFont.Provider(
    providerAuthority = "com.google.android.gms.fonts",
    providerPackage = "com.google.android.gms",
    certificates = R.array.com_google_android_gms_fonts_certs
)

private val SourceSans = GoogleFont("Source Sans Pro").let { googleFont ->
    FontFamily(
        Font(googleFont, fontProvider, FontWeight.Normal, FontStyle.Normal),
        Font(googleFont, fontProvider, FontWeight.Normal, FontStyle.Italic),
        Font(googleFont, fontProvider, FontWeight.Bold, FontStyle.Normal),
        Font(googleFont, fontProvider, FontWeight.Bold, FontStyle.Italic),
    )
}

private val SourceSerif = GoogleFont("Source Serif Pro").let { googleFont ->
    FontFamily(
        Font(googleFont, fontProvider, FontWeight.Normal, FontStyle.Normal),
        Font(googleFont, fontProvider, FontWeight.Normal, FontStyle.Italic),
        Font(googleFont, fontProvider, FontWeight.Bold, FontStyle.Normal),
        Font(googleFont, fontProvider, FontWeight.Bold, FontStyle.Italic),
    )
}
