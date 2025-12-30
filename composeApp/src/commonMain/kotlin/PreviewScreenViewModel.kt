import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlin.math.roundToInt
import kotlin.random.Random

class PreviewScreenViewModel : ViewModel() {

    private val _state = MutableStateFlow(PreviewScreenState())
    val state = _state.asStateFlow()

    fun onUiAction(uiAction: PreviewScreenUiAction) {
        when (uiAction) {
            is PreviewScreenUiAction.Reset -> reset()
            is PreviewScreenUiAction.Random -> random()
            is PreviewScreenUiAction.SetAspectRatio -> setAspectRatio(uiAction.value)
            is PreviewScreenUiAction.SetCornerRadius -> setCornerRadius(uiAction.value)
            is PreviewScreenUiAction.SetCornerSmoothing -> setCornerSmoothing(uiAction.value)
        }
    }

    private fun reset() {
        _state.update { PreviewScreenState() }
    }

    private fun random() {
        _state.update {
            it.copy(
                aspectRatio = Random.nextFloat().coerceIn(.25f, 1.75f),
                cornerRadius = (Random.nextFloat() * 100).roundToInt(),
                smoothing = Random.nextInt(0, 100)
            )
        }
    }

    private fun setAspectRatio(newAspectRatio: Float) {
        _state.update {
            it.copy(
                aspectRatio = newAspectRatio
            )
        }
    }

    private fun setCornerRadius(newCornerRadius: Float) {
        _state.update {
            it.copy(
                cornerRadius = (newCornerRadius * 100).roundToInt()
            )
        }
    }

    private fun setCornerSmoothing(newCornerSmoothing: Int) {
        _state.update {
            it.copy(
                smoothing = newCornerSmoothing
            )
        }
    }

}