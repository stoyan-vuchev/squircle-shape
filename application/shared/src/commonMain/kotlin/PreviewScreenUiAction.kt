import androidx.compose.runtime.Immutable

@Immutable
sealed interface PreviewScreenUiAction {

    data class SetCornerRadius(val value: Float) : PreviewScreenUiAction

    data class SetCornerSmoothing(val value: Float) : PreviewScreenUiAction

    data object Reset : PreviewScreenUiAction

    data object Random : PreviewScreenUiAction

}