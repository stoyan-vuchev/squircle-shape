import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

@Composable
fun ColorScheme.swapBackgroundForSurfaceVariant(): Color {
    return if (isSystemInDarkTheme()) background else surfaceVariant
}