import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import sv.lib.squircleshape.CornerSmoothing
import sv.lib.squircleshape.SquircleShape
import kotlin.math.roundToInt
import kotlin.random.Random

@Composable
fun App() {

    var aspectRatio by remember { mutableFloatStateOf(1f) }
    var cornerRadius by remember { mutableIntStateOf(100) }
    var cornerSmoothing by remember { mutableFloatStateOf(CornerSmoothing.Medium) }
    val state by rememberUpdatedState(
        PreviewScreenState(
            aspectRatio = aspectRatio,
            cornerRadius = cornerRadius,
            cornerSmoothing = cornerSmoothing
        )
    )

    val shapes by rememberUpdatedState(
        Shapes(
            large = SquircleShape(
                percent = state.cornerRadius,
                cornerSmoothing = state.cornerSmoothing
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
                            cornerSmoothing = uiAction.value
                        }

                        is PreviewScreenUiAction.Reset -> {
                            aspectRatio = 1f
                            cornerRadius = 100
                            cornerSmoothing = CornerSmoothing.Medium
                        }

                        is PreviewScreenUiAction.Random -> {
                            aspectRatio = Random.nextFloat().coerceIn(.25f, 1.75f)
                            cornerRadius = (Random.nextFloat() * 100).roundToInt()
                            cornerSmoothing = Random.nextFloat().coerceIn(.55f, 1f)
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