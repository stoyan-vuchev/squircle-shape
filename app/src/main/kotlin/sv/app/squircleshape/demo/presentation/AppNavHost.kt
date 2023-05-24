package sv.app.squircleshape.demo.presentation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import sv.app.squircleshape.demo.DestinationScreen
import sv.app.squircleshape.demo.core.LocalNavHostController
import sv.app.squircleshape.demo.presentation.preview.PreviewScreen
import sv.app.squircleshape.demo.presentation.preview.PreviewScreenViewModel

@Composable
fun AppNavHost() {

    NavHost(
        modifier = Modifier.fillMaxSize(),
        navController = LocalNavHostController.current,
        startDestination = DestinationScreen.Preview.route,
    ) {

        composable(
            route = DestinationScreen.Preview.route,
            content = {

                val viewModel = viewModel<PreviewScreenViewModel>()
                val state by viewModel.state.collectAsStateWithLifecycle()

                PreviewScreen(
                    state = state,
                    onUiAction = viewModel::onUiAction
                )

            }
        )

    }

}