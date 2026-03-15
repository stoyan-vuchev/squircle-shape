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

package com.stoyanvuchev.squircleshape.demo.presentation.app.faq.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.unit.dp
import com.stoyanvuchev.squircleshape.demo.core.ui.Theme
import com.stoyanvuchev.squircleshape.demo.core.ui.component.layout.spacer.VerticalSpacer
import sv.lib.squircleshape.drawSquircle
import kotlin.math.roundToInt

@Composable
fun CornerRadiusComparison() = Box(
    modifier = Modifier
        .padding(
            horizontal = 16.dp,
            vertical = 10.dp
        ),
) {

    Column(
        modifier = Modifier.width(360.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        var smoothing by remember { mutableIntStateOf(50) }
        val smoothingValue by remember {
            derivedStateOf { smoothing.toFloat() / 100 }
        }

        val onSmoothingValueChange = remember<(Float) -> Unit> {
            { newSmoothingValue ->
                smoothing = (newSmoothingValue * 100).roundToInt()
            }
        }

        Box(
            modifier = Modifier
                .width(196.dp)
                .height(64.dp)
                .clipToBounds()
        ) {

            val color = Theme.colorScheme.primary

            Canvas(
                modifier = Modifier.size(64.dp),
                onDraw = {

                    val newSize = size * 5f
                    val cornerRadius = newSize.minDimension / 4f
                    val thickness = 8.dp.toPx()

                    translate(
                        left = size.width,
                        top = thickness
                    ) {

                        rotate(
                            degrees = 45f
                        ) {

                            drawRoundRect(
                                color = color.copy(alpha = .33f),
                                size = newSize,
                                style = Stroke(width = thickness),
                                cornerRadius = CornerRadius(x = cornerRadius)
                            )

                            drawSquircle(
                                color = color.copy(alpha = .8f),
                                size = newSize,
                                style = Stroke(width = thickness),
                                topLeftCorner = cornerRadius,
                                topRightCorner = cornerRadius,
                                bottomRightCorner = cornerRadius,
                                bottomLeftCorner = cornerRadius,
                                smoothing = smoothing
                            )

                        }

                    }

                }
            )

            Text(
                modifier = Modifier.align(Alignment.BottomCenter),
                text = "$smoothing%",
                style = Theme.typography.bodyLarge,
                color = Theme.colorScheme.onSurface
            )

        }

        VerticalSpacer(height = 20.dp)

        Slider(
            value = smoothingValue,
            onValueChange = onSmoothingValueChange,
            colors = SliderDefaults.colors(
                thumbColor = Theme.colorScheme.primary,
                activeTrackColor = Theme.colorScheme.primary,
                inactiveTrackColor = Theme.colorScheme.primary.copy(.33f),
                activeTickColor = Theme.colorScheme.surface,
                inactiveTickColor = Theme.colorScheme.surface
            )
        )

        VerticalSpacer(height = 10.dp)

    }

}