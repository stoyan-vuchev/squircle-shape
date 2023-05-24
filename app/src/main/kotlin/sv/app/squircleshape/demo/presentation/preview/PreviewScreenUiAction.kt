package sv.app.squircleshape.demo.presentation.preview

import androidx.compose.runtime.Immutable

@Immutable
sealed interface PreviewScreenUiAction {

    data class SetCornerRadius(val value: Float) : PreviewScreenUiAction

    data class SetCornerSmoothing(val value: Float) : PreviewScreenUiAction

    object Reset : PreviewScreenUiAction

    object Random : PreviewScreenUiAction

}