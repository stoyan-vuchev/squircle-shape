package sv.app.squircleshape.demo.core

import androidx.compose.runtime.compositionLocalOf
import androidx.navigation.NavHostController

val LocalNavHostController = compositionLocalOf<NavHostController> {
    error("CompositionLocal LocalNavHostController is not provided.")
}