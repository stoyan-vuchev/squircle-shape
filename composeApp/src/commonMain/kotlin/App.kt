import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import sv.lib.squircleshape.Smoothing
import sv.lib.squircleshape.SquircleShape
import kotlin.math.roundToInt
import kotlin.random.Random

@Composable
fun App() {

    var aspectRatio by remember { mutableFloatStateOf(1f) }
    var cornerRadius by remember { mutableIntStateOf(100) }
    var smoothing by remember { mutableIntStateOf(Smoothing.Medium) }
    var upscaleCornerRadius by remember { mutableStateOf(true) }

    val state by rememberUpdatedState(
        PreviewScreenState(
            aspectRatio = aspectRatio,
            cornerRadius = cornerRadius,
            smoothing = smoothing,
            upscaleCornerRadius = upscaleCornerRadius
        )
    )

    val shapes by rememberUpdatedState(
        Shapes(
            large = SquircleShape(
                percent = state.cornerRadius,
                smoothing = state.smoothing,
                upscaleCornerSize = state.upscaleCornerRadius
            )
        )
    )

    MaterialTheme(
        shapes = shapes,
        colorScheme = if (isSystemInDarkTheme()) darkColorScheme() else lightColorScheme(),
        content = {

            val scope = rememberCoroutineScope()
            val onUiAction = remember<(PreviewScreenUiAction) -> Unit> {
                { uiAction ->

                    when (uiAction) {

                        is PreviewScreenUiAction.SetAspectRatio -> {
                            aspectRatio = uiAction.value
                        }

                        is PreviewScreenUiAction.SetCornerRadius -> {
                            cornerRadius = (uiAction.value * 100).roundToInt()
                        }

                        is PreviewScreenUiAction.SetCornerSmoothing -> {
                            smoothing = uiAction.value
                        }

                        is PreviewScreenUiAction.SetUpscaleCornerRadius -> {
                            upscaleCornerRadius = uiAction.value
                        }

                        is PreviewScreenUiAction.Reset -> {
                            aspectRatio = 1f
                            cornerRadius = 100
                            smoothing = Smoothing.Medium
                            upscaleCornerRadius = true
                        }

                        is PreviewScreenUiAction.Random -> {
                            aspectRatio = Random.nextFloat().coerceIn(.25f, 1.75f)
                            cornerRadius = (Random.nextFloat() * 100).roundToInt()
                            smoothing = Random.nextInt(0, 100)
                        }

                    }

                }
            }

            PreviewScreen(
                state = state,
                onUiAction = { uiAction ->
                    scope.launch {
                        withContext(Dispatchers.Default) {
                            onUiAction(uiAction)
                        }
                    }
                }
            )

        }
    )

}