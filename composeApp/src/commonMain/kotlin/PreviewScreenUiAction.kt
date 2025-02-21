import androidx.compose.runtime.Immutable

@Immutable
sealed interface PreviewScreenUiAction {

    data class SetAspectRatio(val value: Float) : PreviewScreenUiAction
    data class SetCornerRadius(val value: Float) : PreviewScreenUiAction
    data class SetCornerSmoothing(val value: Int) : PreviewScreenUiAction
    data class SetUpscaleCornerRadius(val value: Boolean) : PreviewScreenUiAction

    data object Reset : PreviewScreenUiAction
    data object Random : PreviewScreenUiAction

}