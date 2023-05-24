package sv.app.squircleshape.demo.presentation.preview

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlin.random.Random

class PreviewScreenViewModel : ViewModel() {

    private val _state = MutableStateFlow(PreviewScreenState())
    val state = _state.asStateFlow()

    fun onUiAction(uiAction: PreviewScreenUiAction) {
        when (uiAction) {
            is PreviewScreenUiAction.SetCornerRadius -> setCornerRadius(uiAction.value)
            is PreviewScreenUiAction.SetCornerSmoothing -> setCornerSmoothing(uiAction.value)
            is PreviewScreenUiAction.Reset -> resetState()
            is PreviewScreenUiAction.Random -> setRandom()
        }
    }

    private fun setCornerRadius(value: Float) {
        _state.update { it.copy(cornerRadius = value) }
    }

    private fun setCornerSmoothing(value: Float) {
        _state.update { it.copy(cornerSmoothing = value) }
    }

    private fun resetState() {
        _state.update { PreviewScreenState() }
    }

    private fun setRandom() {
        setCornerRadius(value = Random.nextFloat())
        setCornerSmoothing(value = Random.nextFloat().coerceAtLeast(0.55f))
    }

}