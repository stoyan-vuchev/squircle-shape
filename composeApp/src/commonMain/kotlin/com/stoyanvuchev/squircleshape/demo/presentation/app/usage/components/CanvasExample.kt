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

package com.stoyanvuchev.squircleshape.demo.presentation.app.usage.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp
import com.stoyanvuchev.squircleshape.demo.core.ui.Theme
import sv.lib.squircleshape.drawSquircle

@Composable
fun CanvasExample() = Box(
    modifier = Modifier
        .size(256.dp)
        .aspectRatio(1f),
    contentAlignment = Alignment.Center
) {

    val primaryColor = Theme.colorScheme.primary
    val accentColor = Theme.colorScheme.accent

    Canvas(
        modifier = Modifier.size(128.dp),
        onDraw = {

            val squircleSize = this.size * .73f
            val cornerRadius = squircleSize.minDimension
            val smoothing = 50
            val borderWidth = 8.dp.toPx()

            val secondarySquircleOffset = Offset(
                x = this.size.width - squircleSize.width,
                y = 0f
            )

            drawSquircle(
                color = accentColor,
                size = squircleSize,
                topLeft = secondarySquircleOffset,
                topLeftCorner = cornerRadius,
                topRightCorner = cornerRadius,
                bottomRightCorner = cornerRadius,
                bottomLeftCorner = cornerRadius,
                smoothing = smoothing,
                style = Stroke(width = borderWidth)
            )

            val primarySquircleOffset = Offset(
                x = 0f,
                y = this.size.height - squircleSize.height
            )

            drawSquircle(
                color = primaryColor,
                size = squircleSize,
                topLeft = primarySquircleOffset,
                topLeftCorner = cornerRadius,
                topRightCorner = cornerRadius,
                bottomRightCorner = cornerRadius,
                bottomLeftCorner = cornerRadius,
                smoothing = smoothing,
                style = Stroke(width = borderWidth)
            )

        }
    )

}