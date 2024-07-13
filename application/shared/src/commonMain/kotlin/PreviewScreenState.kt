import androidx.compose.runtime.Stable
import sv.lib.squircleshape.CornerSmoothing

@Stable
data class PreviewScreenState(
    val cornerRadius: Int = 100,
    val cornerSmoothing: Float = CornerSmoothing.Medium
)