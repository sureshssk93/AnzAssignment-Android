package anz.project.userapp.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable

private val DarkColorPalette = darkColors(

    primary = AnzBlueColor,
    primaryVariant = AnzWhiteColor,
    secondary = BlackColor
)

private val LightColorPalette = lightColors(
    primary = AnzWhiteColor,
    primaryVariant = AnzBlueColor,
    secondary = BlackColor
)

@Composable
fun AnzAppTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    MaterialTheme(
        colors = colors,
        typography = Typography,
        content = content
    )
}
