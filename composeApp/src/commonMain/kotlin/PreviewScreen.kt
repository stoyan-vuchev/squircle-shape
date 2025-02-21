import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import sv.lib.squircleshape.drawSquircle
import kotlin.math.roundToInt

@Composable
fun PreviewScreen(
    state: PreviewScreenState,
    onUiAction: (PreviewScreenUiAction) -> Unit,
) {

    Scaffold(
        containerColor = MaterialTheme.colorScheme.swapBackgroundForSurfaceVariant(),
        contentColor = MaterialTheme.colorScheme.onBackground,
        topBar = { FixedSmallTopAppBar(title = "Squircle Shape") }
    ) { paddingValues ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {

            Box(
                modifier = Modifier
                    .defaultMinSize(256.dp)
                    .fillMaxWidth()
                    .weight(1f),
                contentAlignment = Alignment.Center
            ) {

                val color = MaterialTheme.colorScheme.primary
                val shape = MaterialTheme.shapes.large
                val density = LocalDensity.current

                Canvas(
                    modifier = Modifier
                        .height(128.dp)
                        .aspectRatio(state.aspectRatio),
                    onDraw = {

                        val landscapeSquircleSize = this.size
                        val landscapeSquircleOffset = Offset.Zero
                        val shapeSize = Size(
                            width = landscapeSquircleSize.width / 2,
                            height = landscapeSquircleSize.height / 2
                        )

                        drawSquircle(
                            color = color,
                            topLeft = landscapeSquircleOffset,
                            size = landscapeSquircleSize,
                            topLeftCorner = shape.topStart.toPx(shapeSize, density),
                            topRightCorner = shape.topEnd.toPx(shapeSize, density),
                            bottomLeftCorner = shape.bottomStart.toPx(shapeSize, density),
                            bottomRightCorner = shape.bottomEnd.toPx(shapeSize, density),
                            smoothing = state.smoothing,
                            upscaleCornerSize = state.upscaleCornerRadius
                        )

                    }
                )

            }

            val aspectRatioSliderTitle by remember(state.aspectRatio) {
                derivedStateOf {
                    "Aspect ratio:   ${state.aspectRatio.roundDecimalCountTo(2)}"
                }
            }

            SliderWithTitle(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp),
                title = aspectRatioSliderTitle,
                value = state.aspectRatio,
                valueRange = 0.25f..2.75f,
                onValueChange = remember {
                    { onUiAction(PreviewScreenUiAction.SetAspectRatio(it)) }
                }
            )

            Spacer(modifier = Modifier.height(24.dp))

            val cornerRadiusSliderTitle by remember(state.cornerRadius) {
                derivedStateOf {
                    "Corner radius:   ${state.cornerRadius}%"
                }
            }

            val cornerRadiusSliderValue by remember(state.cornerRadius) {
                derivedStateOf { state.cornerRadius.toFloat() / 100f }
            }

            SliderWithTitle(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp),
                title = cornerRadiusSliderTitle,
                value = cornerRadiusSliderValue,
                valueRange = 0f..1f,
                onValueChange = remember {
                    { onUiAction(PreviewScreenUiAction.SetCornerRadius(it)) }
                }
            )

            Spacer(modifier = Modifier.height(24.dp))

            val cornerSmoothingSliderTitle by remember(state.smoothing) {
                derivedStateOf {
                    "Corner smoothing:   ${state.smoothing}"
                }
            }

            SliderWithTitle(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp),
                title = cornerSmoothingSliderTitle,
                value = state.smoothing.toFloat() / 100f,
                valueRange = 0f..1f,
                onValueChange = remember {
                    {
                        onUiAction(
                            PreviewScreenUiAction.SetCornerSmoothing(
                                (it * 100f).roundToInt()
                            )
                        )
                    }
                }
            )

            Spacer(modifier = Modifier.height(24.dp))

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp),
            ) {

                Text(
                    text = "Upscale corner size",
                    style = MaterialTheme.typography.titleSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                Spacer(modifier = Modifier.height(12.dp))

                Switch(
                    checked = state.upscaleCornerRadius,
                    onCheckedChange = remember {
                        {
                            onUiAction(
                                PreviewScreenUiAction.SetUpscaleCornerRadius(it)
                            )
                        }
                    }
                )

            }

            Spacer(modifier = Modifier.height(24.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 32.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {

                TextButton(
                    onClick = remember { { onUiAction(PreviewScreenUiAction.Reset) } },
                    modifier = Modifier.weight(1f),
                    shape = MaterialTheme.shapes.large,
                    content = { Text(text = "Reset") }
                )

                TextButton(
                    onClick = remember { { onUiAction(PreviewScreenUiAction.Random) } },
                    modifier = Modifier.weight(1f),
                    shape = MaterialTheme.shapes.large,
                    content = { Text(text = "Random") }
                )

            }

            Spacer(modifier = Modifier.height(16.dp))
            Spacer(modifier = Modifier.navigationBarsPadding())

        }

    }

}