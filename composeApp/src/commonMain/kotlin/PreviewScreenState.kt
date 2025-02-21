import androidx.compose.runtime.Stable
import sv.lib.squircleshape.Smoothing

@Stable
data class PreviewScreenState(
    val aspectRatio: Float = 1f,
    val cornerRadius: Int = 100,
    val smoothing: Int = Smoothing.Medium,
    val upscaleCornerRadius: Boolean = true
)