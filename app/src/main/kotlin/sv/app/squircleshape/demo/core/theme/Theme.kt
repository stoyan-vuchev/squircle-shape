package sv.app.squircleshape.demo.core.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import sv.app.squircleshape.demo.core.LocalNavHostController

@Composable
fun SquircleShapeDemoTheme(
    navHostController: NavHostController = rememberNavController(),
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {

    val colorScheme = when {

        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context)
            else dynamicLightColorScheme(context)
        }

        darkTheme -> darkColorScheme()
        else -> lightColorScheme()

    }

    CompositionLocalProvider(
        LocalNavHostController provides navHostController,
        content = {

            MaterialTheme(
                colorScheme = colorScheme,
                typography = Typography,
                content = content
            )

        }
    )

}

@Composable
fun ColorScheme.swapBackgroundForSurfaceVariant(): Color {
    return if (isSystemInDarkTheme()) background else surfaceVariant
}