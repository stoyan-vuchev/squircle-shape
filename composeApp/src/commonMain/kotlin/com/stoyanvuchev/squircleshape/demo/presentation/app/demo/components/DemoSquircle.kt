/*
 * MIT License
 *
 * Copyright (c) 2026 Stoyan Vuchev
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package com.stoyanvuchev.squircleshape.demo.presentation.app.demo.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.stoyanvuchev.squircleshape.demo.core.ui.Theme
import com.stoyanvuchev.squircleshape.demo.core.ui.component.icon.AsyncIcon
import com.stoyanvuchev.squircleshape.demo.core.ui.component.layout.spacer.HorizontalSpacer
import com.stoyanvuchev.squircleshape.demo.core.ui.component.layout.spacer.VerticalSpacer
import com.stoyanvuchev.squircleshape.demo.core.ui.component.text.Text
import com.stoyanvuchev.squircleshape.demo.core.ui.shape.LocalShapeData
import com.stoyanvuchev.squircleshape.demo.core.ui.util.window.LocalWindowState
import squircleshape.composeapp.generated.resources.Res
import squircleshape.composeapp.generated.resources.flowers
import sv.lib.squircleshape.SquircleShape
import kotlin.math.roundToInt

@Composable
fun DemoSquircle() = when {
    LocalWindowState.current.isLargeWidth -> LargeDemoSquircle()
    LocalWindowState.current.isMediumWidth -> MediumDemoSquircle()
    else -> CompactDemoSquircle()
}

@Composable
private fun LargeDemoSquircle() = Row(
    modifier = Modifier
        .padding(horizontal = 10.dp)
        .fillMaxWidth(),
    verticalAlignment = Alignment.CenterVertically
) {

    val percent by LocalShapeData.current.percent.collectAsStateWithLifecycle()
    val smoothing by LocalShapeData.current.smoothing.collectAsStateWithLifecycle()
    val squircleShape by remember {
        derivedStateOf {
            SquircleShape(
                percent = percent / 2,
                smoothing = smoothing
            )
        }
    }

    AsyncIcon(
        icon = { Res.drawable.flowers },
        modifier = Modifier
            .padding(vertical = 20.dp)
            .fillMaxWidth(.25f)
            .aspectRatio(1f)
            .clip(shape = squircleShape)
            .border(
                shape = squircleShape,
                width = 1.dp,
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Theme.colorScheme.outline,
                        Theme.colorScheme.outline.copy(0f)
                    )
                )
            ),
        tint = Color.Unspecified,
        contentScale = ContentScale.Crop,
        size = null
    )

    HorizontalSpacer(width = 64.dp)

    DemoSquircleSliders(
        modifier = Modifier.width(256.dp)
    )

}

@Composable
private fun MediumDemoSquircle() = Row(
    modifier = Modifier.fillMaxWidth(),
    verticalAlignment = Alignment.CenterVertically
) {

    val percent by LocalShapeData.current.percent.collectAsStateWithLifecycle()
    val smoothing by LocalShapeData.current.smoothing.collectAsStateWithLifecycle()
    val squircleShape by remember {
        derivedStateOf {
            SquircleShape(
                percent = percent / 2,
                smoothing = smoothing
            )
        }
    }

    AsyncIcon(
        icon = { Res.drawable.flowers },
        modifier = Modifier
            .padding(vertical = 20.dp)
            .weight(.5f)
            .aspectRatio(1f)
            .clip(shape = squircleShape)
            .border(
                shape = squircleShape,
                width = 1.dp,
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Theme.colorScheme.outline,
                        Theme.colorScheme.outline.copy(0f)
                    )
                )
            ),
        tint = Color.Unspecified,
        contentScale = ContentScale.Crop,
        size = null
    )

    HorizontalSpacer(width = 20.dp)

    DemoSquircleSliders(
        modifier = Modifier
            .weight(.5f)
    )

}

@Composable
private fun CompactDemoSquircle() = Column(
    modifier = Modifier.fillMaxWidth()
) {

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 20.dp),
        contentAlignment = Alignment.Center
    ) {

        val percent by LocalShapeData.current.percent.collectAsStateWithLifecycle()
        val smoothing by LocalShapeData.current.smoothing.collectAsStateWithLifecycle()
        val squircleShape by remember {
            derivedStateOf {
                SquircleShape(
                    percent = percent / 2,
                    smoothing = smoothing
                )
            }
        }

        AsyncIcon(
            icon = { Res.drawable.flowers },
            modifier = Modifier
                .fillMaxWidth(.67f)
                .aspectRatio(1f)
                .clip(shape = squircleShape)
                .border(
                    shape = squircleShape,
                    width = 1.dp,
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            Theme.colorScheme.outline,
                            Theme.colorScheme.outline.copy(0f)
                        )
                    )
                ),
            tint = Color.Unspecified,
            contentScale = ContentScale.Crop,
            size = null
        )

    }

    VerticalSpacer(height = 20.dp)

    DemoSquircleSliders(modifier = Modifier.fillMaxWidth())

    VerticalSpacer(height = 20.dp)

}

@Composable
private fun DemoSquircleSliders(
    modifier: Modifier = Modifier
) = Column(
    modifier = Modifier
        .padding(horizontal = 20.dp)
        .then(modifier)
) {

    val shapeData = LocalShapeData.current
    val radius by shapeData.percent.collectAsStateWithLifecycle()
    val radiusFloat by remember {
        derivedStateOf { radius.toFloat() / 100 }
    }

    Text(
        modifier = Modifier.padding(horizontal = 8.dp),
        text = "Radius: $radius%",
        style = Theme.typography.label
    )

    Slider(
        modifier = Modifier.fillMaxWidth(),
        value = radiusFloat,
        onValueChange = remember {
            { shapeData.setPercent((it * 100).roundToInt()) }
        },
        colors = SliderDefaults.colors(
            thumbColor = Theme.colorScheme.primary,
            activeTrackColor = Theme.colorScheme.primary,
            inactiveTrackColor = Theme.colorScheme.primary.copy(.33f),
            activeTickColor = Theme.colorScheme.surface,
            inactiveTickColor = Theme.colorScheme.surface
        )
    )

    VerticalSpacer(height = 20.dp)

    val smoothing by shapeData.smoothing.collectAsStateWithLifecycle()
    val smoothingFloat by remember {
        derivedStateOf { smoothing.toFloat() / 100 }
    }

    Text(
        modifier = Modifier.padding(horizontal = 8.dp),
        text = "Smoothing: $smoothing%",
        style = Theme.typography.label
    )

    Slider(
        modifier = Modifier.fillMaxWidth(),
        value = smoothingFloat,
        onValueChange = remember {
            { shapeData.setSmoothing((it * 100).roundToInt()) }
        },
        colors = SliderDefaults.colors(
            thumbColor = Theme.colorScheme.primary,
            activeTrackColor = Theme.colorScheme.primary,
            inactiveTrackColor = Theme.colorScheme.primary.copy(.33f),
            activeTickColor = Theme.colorScheme.surface,
            inactiveTickColor = Theme.colorScheme.surface
        )
    )

}