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
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp
import com.stoyanvuchev.squircleshape.demo.core.ui.Theme
import com.stoyanvuchev.squircleshape.demo.core.ui.component.layout.spacer.HorizontalSpacer
import com.stoyanvuchev.squircleshape.demo.core.ui.component.text.Text
import sv.lib.squircleshape.drawSquircle

@Composable
fun SquircleToRoundedCornerShapeComparison() = Row(
    modifier = Modifier
        .padding(horizontal = 8.dp, vertical = 20.dp),
    verticalAlignment = Alignment.CenterVertically
) {

    val squareColor = Theme.colorScheme.accent

    Canvas(
        modifier = Modifier
            .padding(4.dp)
            .size(80.dp)
    ) {

        val cornerRadius = size.minDimension / 2.67f

        drawRoundRect(
            color = squareColor,
            size = size,
            cornerRadius = CornerRadius(
                x = cornerRadius,
                y = cornerRadius
            ),
            style = Stroke(width = 4.dp.toPx())
        )

    }

    HorizontalSpacer(width = 20.dp)

    Text(
        text = "→",
        style = Theme.typography.titleLarge
    )

    HorizontalSpacer(width = 20.dp)

    val squircleColor = Theme.colorScheme.primary

    Canvas(
        modifier = Modifier
            .padding(4.dp)
            .size(80.dp)
    ) {

        val cornerRadius = size.minDimension / 2

        drawSquircle(
            color = squircleColor,
            size = this.size,
            topLeftCorner = cornerRadius,
            topRightCorner = cornerRadius,
            bottomRightCorner = cornerRadius,
            bottomLeftCorner = cornerRadius,
            smoothing = 50,
            style = Stroke(width = 4.dp.toPx())
        )

    }

}