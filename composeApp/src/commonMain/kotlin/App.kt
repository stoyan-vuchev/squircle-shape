import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.koin.compose.KoinApplication
import org.koin.compose.viewmodel.koinViewModel
import org.koin.dsl.KoinAppDeclaration
import sv.lib.squircleshape.SquircleShape

@Composable
fun App(
    colorScheme: ColorScheme = if (isSystemInDarkTheme()) darkColorScheme() else lightColorScheme(),
    koinAppDeclaration: KoinAppDeclaration? = null
) = KoinApplication(
    application = {
        koinAppDeclaration?.invoke(this)
        modules(appModule)
    }
) {

    val viewModel = koinViewModel<PreviewScreenViewModel>()
    val state by viewModel.state.collectAsStateWithLifecycle()
    val shapes by rememberUpdatedState(
        Shapes(
            large = SquircleShape(
                percent = state.cornerRadius,
                smoothing = state.smoothing
            )
        )
    )

    MaterialTheme(
        shapes = shapes,
        colorScheme = colorScheme,
        content = {

            PreviewScreen(
                state = state,
                onUiAction = viewModel::onUiAction
            )

        }
    )

}