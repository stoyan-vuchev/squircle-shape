package sv.app.squircleshape.demo.presentation.preview

import androidx.compose.runtime.Stable

@Stable
data class PreviewScreenState(
    val cornerRadius: Float = 1f,
    val cornerSmoothing: Float = 0.72f
)