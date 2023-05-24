package sv.app.squircleshape.demo.presentation.preview

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import sv.app.squircleshape.demo.R
import sv.app.squircleshape.demo.core.theme.SquircleShapeDemoTheme
import sv.app.squircleshape.demo.core.theme.swapBackgroundForSurfaceVariant
import sv.app.squircleshape.demo.core.ui_components.FixedSmallTopAppBar
import sv.app.squircleshape.demo.core.ui_components.SliderWithTitle
import sv.lib.squircleshape.SquircleShape
import sv.lib.squircleshape.drawSquircle
import kotlin.math.roundToInt

@Composable
fun PreviewScreen(
    state: PreviewScreenState,
    onUiAction: (PreviewScreenUiAction) -> Unit,
) {

    val color = MaterialTheme.colorScheme.primary
    val cornerRadius by rememberUpdatedState(state.cornerRadius)
    val cornerSmoothing by rememberUpdatedState(state.cornerSmoothing)

    val cornerRadiusSliderTitle by rememberUpdatedState(
        "${stringResource(id = R.string.corner_radius)}:   " +
                "${(state.cornerRadius * 100).roundToInt()}%"
    )

    val cornerSmoothingSliderTitle by rememberUpdatedState(
        "${stringResource(id = R.string.corner_smoothing)}:   " +
                "$cornerSmoothing"
    )

    Scaffold(
        containerColor = MaterialTheme.colorScheme.swapBackgroundForSurfaceVariant(),
        contentColor = MaterialTheme.colorScheme.onBackground,
        topBar = { FixedSmallTopAppBar(title = stringResource(id = R.string.app_name)) }
    ) { paddingValues ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                contentAlignment = Alignment.Center
            ) {

                Canvas(
                    modifier = Modifier.size(width = 150.dp, height = 150.dp),
                    onDraw = {

                        val smallestAxis = if (size.width < size.height) size.width
                        else size.height

                        val corner = (smallestAxis / 2f) * cornerRadius

                        drawSquircle(
                            color = color,
                            topLeft = Offset.Zero,
                            size = size,
                            topLeftCorner = corner,
                            topRightCorner = corner,
                            bottomLeftCorner = corner,
                            bottomRightCorner = corner,
                            cornerSmoothing = cornerSmoothing
                        )

                    }
                )

            }

            SliderWithTitle(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp),
                title = cornerRadiusSliderTitle,
                value = cornerRadius,
                onValueChange = remember { { onUiAction(PreviewScreenUiAction.SetCornerRadius(it)) } }
            )

            Spacer(modifier = Modifier.height(24.dp))

            SliderWithTitle(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp),
                title = cornerSmoothingSliderTitle,
                value = cornerSmoothing,
                valueRange = 0.55f..1f,
                onValueChange = remember { { onUiAction(PreviewScreenUiAction.SetCornerSmoothing(it)) } }
            )

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
                    shape = SquircleShape(),
                    content = { Text(text = stringResource(id = R.string.reset)) }
                )

                TextButton(
                    onClick = remember { { onUiAction(PreviewScreenUiAction.Random) } },
                    modifier = Modifier.weight(1f),
                    shape = SquircleShape(),
                    content = { Text(text = stringResource(id = R.string.random)) }
                )

            }

            Spacer(modifier = Modifier.height(16.dp))
            Spacer(modifier = Modifier.navigationBarsPadding())

        }

    }

}

@Preview(uiMode = UI_MODE_NIGHT_YES)
@Composable
private fun PreviewScreenPreview() {
    SquircleShapeDemoTheme {
        PreviewScreen(
            state = PreviewScreenState(),
            onUiAction = {}
        )
    }
}