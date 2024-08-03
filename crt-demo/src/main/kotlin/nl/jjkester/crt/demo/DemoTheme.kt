import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.googlefonts.Font
import androidx.compose.ui.text.googlefonts.GoogleFont
import nl.jjkester.crt.demo.R

@Composable
fun DemoTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = when {
            dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
                val context = LocalContext.current
                if (darkTheme) {
                    dynamicDarkColorScheme(context)
                } else {
                    dynamicLightColorScheme(context)
                }
            }

            darkTheme -> darkScheme
            else -> lightScheme
        },
        typography = typography,
        content = content
    )
}

private val primaryLight = Color(0xFF043E67)
private val onPrimaryLight = Color(0xFFFFFFFF)
private val primaryContainerLight = Color(0xFF35628D)
private val onPrimaryContainerLight = Color(0xFFFFFFFF)
private val secondaryLight = Color(0xFF34536C)
private val onSecondaryLight = Color(0xFFFFFFFF)
private val secondaryContainerLight = Color(0xFF597892)
private val onSecondaryContainerLight = Color(0xFFFFFFFF)
private val tertiaryLight = Color(0xFF5D5F5F)
private val onTertiaryLight = Color(0xFFFFFFFF)
private val tertiaryContainerLight = Color(0xFFE6E6E6)
private val onTertiaryContainerLight = Color(0xFF494A4B)
private val errorLight = Color(0xFFBA1A1A)
private val onErrorLight = Color(0xFFFFFFFF)
private val errorContainerLight = Color(0xFFFFDAD6)
private val onErrorContainerLight = Color(0xFF410002)
private val backgroundLight = Color(0xFFF9F9FD)
private val onBackgroundLight = Color(0xFF1A1C1F)
private val surfaceLight = Color(0xFFFCF8F8)
private val onSurfaceLight = Color(0xFF1C1B1B)
private val surfaceVariantLight = Color(0xFFE0E3E3)
private val onSurfaceVariantLight = Color(0xFF444748)
private val outlineLight = Color(0xFF747878)
private val outlineVariantLight = Color(0xFFC4C7C8)
private val scrimLight = Color(0xFF000000)
private val inverseSurfaceLight = Color(0xFF313030)
private val inverseOnSurfaceLight = Color(0xFFF4F0EF)
private val inversePrimaryLight = Color(0xFF9FCAFB)
private val surfaceDimLight = Color(0xFFDDD9D9)
private val surfaceBrightLight = Color(0xFFFCF8F8)
private val surfaceContainerLowestLight = Color(0xFFFFFFFF)
private val surfaceContainerLowLight = Color(0xFFF6F3F2)
private val surfaceContainerLight = Color(0xFFF1EDEC)
private val surfaceContainerHighLight = Color(0xFFEBE7E7)
private val surfaceContainerHighestLight = Color(0xFFE5E2E1)

private val primaryDark = Color(0xFF9FCAFB)
private val onPrimaryDark = Color(0xFF003256)
private val primaryContainerDark = Color(0xFF164872)
private val onPrimaryContainerDark = Color(0xFFC8E0FF)
private val secondaryDark = Color(0xFFAACAE7)
private val onSecondaryDark = Color(0xFF11334A)
private val secondaryContainerDark = Color(0xFF597892)
private val onSecondaryContainerDark = Color(0xFFFFFFFF)
private val tertiaryDark = Color(0xFFFFFFFF)
private val onTertiaryDark = Color(0xFF2F3131)
private val tertiaryContainerDark = Color(0xFFD4D4D4)
private val onTertiaryContainerDark = Color(0xFF3E4040)
private val errorDark = Color(0xFFFFB4AB)
private val onErrorDark = Color(0xFF690005)
private val errorContainerDark = Color(0xFF93000A)
private val onErrorContainerDark = Color(0xFFFFDAD6)
private val backgroundDark = Color(0xFF111416)
private val onBackgroundDark = Color(0xFFE2E2E6)
private val surfaceDark = Color(0xFF141313)
private val onSurfaceDark = Color(0xFFE5E2E1)
private val surfaceVariantDark = Color(0xFF444748)
private val onSurfaceVariantDark = Color(0xFFC4C7C8)
private val outlineDark = Color(0xFF8E9192)
private val outlineVariantDark = Color(0xFF444748)
private val scrimDark = Color(0xFF000000)
private val inverseSurfaceDark = Color(0xFFE5E2E1)
private val inverseOnSurfaceDark = Color(0xFF313030)
private val inversePrimaryDark = Color(0xFF35618C)
private val surfaceDimDark = Color(0xFF141313)
private val surfaceBrightDark = Color(0xFF3A3939)
private val surfaceContainerLowestDark = Color(0xFF0E0E0E)
private val surfaceContainerLowDark = Color(0xFF1C1B1B)
private val surfaceContainerDark = Color(0xFF201F1F)
private val surfaceContainerHighDark = Color(0xFF2A2A2A)
private val surfaceContainerHighestDark = Color(0xFF353434)

private val lightScheme = lightColorScheme(
    primary = primaryLight,
    onPrimary = onPrimaryLight,
    primaryContainer = primaryContainerLight,
    onPrimaryContainer = onPrimaryContainerLight,
    secondary = secondaryLight,
    onSecondary = onSecondaryLight,
    secondaryContainer = secondaryContainerLight,
    onSecondaryContainer = onSecondaryContainerLight,
    tertiary = tertiaryLight,
    onTertiary = onTertiaryLight,
    tertiaryContainer = tertiaryContainerLight,
    onTertiaryContainer = onTertiaryContainerLight,
    error = errorLight,
    onError = onErrorLight,
    errorContainer = errorContainerLight,
    onErrorContainer = onErrorContainerLight,
    background = backgroundLight,
    onBackground = onBackgroundLight,
    surface = surfaceLight,
    onSurface = onSurfaceLight,
    surfaceVariant = surfaceVariantLight,
    onSurfaceVariant = onSurfaceVariantLight,
    outline = outlineLight,
    outlineVariant = outlineVariantLight,
    scrim = scrimLight,
    inverseSurface = inverseSurfaceLight,
    inverseOnSurface = inverseOnSurfaceLight,
    inversePrimary = inversePrimaryLight,
    surfaceDim = surfaceDimLight,
    surfaceBright = surfaceBrightLight,
    surfaceContainerLowest = surfaceContainerLowestLight,
    surfaceContainerLow = surfaceContainerLowLight,
    surfaceContainer = surfaceContainerLight,
    surfaceContainerHigh = surfaceContainerHighLight,
    surfaceContainerHighest = surfaceContainerHighestLight,
)

private val darkScheme = darkColorScheme(
    primary = primaryDark,
    onPrimary = onPrimaryDark,
    primaryContainer = primaryContainerDark,
    onPrimaryContainer = onPrimaryContainerDark,
    secondary = secondaryDark,
    onSecondary = onSecondaryDark,
    secondaryContainer = secondaryContainerDark,
    onSecondaryContainer = onSecondaryContainerDark,
    tertiary = tertiaryDark,
    onTertiary = onTertiaryDark,
    tertiaryContainer = tertiaryContainerDark,
    onTertiaryContainer = onTertiaryContainerDark,
    error = errorDark,
    onError = onErrorDark,
    errorContainer = errorContainerDark,
    onErrorContainer = onErrorContainerDark,
    background = backgroundDark,
    onBackground = onBackgroundDark,
    surface = surfaceDark,
    onSurface = onSurfaceDark,
    surfaceVariant = surfaceVariantDark,
    onSurfaceVariant = onSurfaceVariantDark,
    outline = outlineDark,
    outlineVariant = outlineVariantDark,
    scrim = scrimDark,
    inverseSurface = inverseSurfaceDark,
    inverseOnSurface = inverseOnSurfaceDark,
    inversePrimary = inversePrimaryDark,
    surfaceDim = surfaceDimDark,
    surfaceBright = surfaceBrightDark,
    surfaceContainerLowest = surfaceContainerLowestDark,
    surfaceContainerLow = surfaceContainerLowDark,
    surfaceContainer = surfaceContainerDark,
    surfaceContainerHigh = surfaceContainerHighDark,
    surfaceContainerHighest = surfaceContainerHighestDark,
)

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

private val typography = Typography().run {
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
}
