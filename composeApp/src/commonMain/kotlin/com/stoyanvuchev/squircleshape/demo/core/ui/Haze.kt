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

package com.stoyanvuchev.squircleshape.demo.core.ui

import androidx.compose.foundation.border
import androidx.compose.foundation.shape.CornerBasedShape
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.dropShadow
import androidx.compose.ui.draw.innerShadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.shadow.Shadow
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.HazeStyle
import dev.chrisbanes.haze.HazeTint
import dev.chrisbanes.haze.hazeEffect

val LocalHazeState = compositionLocalOf { HazeState() }

fun Modifier.floatingComponentHazeEffect(
    shape: CornerBasedShape
): Modifier = composed {
    this
        .dropShadow(
            shape = shape,
            shadow = Shadow(
                radius = 24.dp,
                color = Theme.colorScheme.shadow,
                offset = DpOffset(x = 0.dp, y = 4.dp)
            )
        )
        .clip(shape)
        .hazeEffect(
            state = LocalHazeState.current,
            style = HazeStyle(
                backgroundColor = Theme.colorScheme.surface,
                tint = HazeTint(
                    color = Theme.colorScheme.surfaceElevationMedium.copy(.75f)
                ),
                blurRadius = 12.dp
            )
        )
        .innerShadow(
            shape = shape,
            shadow = Shadow(
                radius = 16.dp,
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Theme.colorScheme.surface.copy(.5f),
                        Theme.colorScheme.surface.copy(0f)
                    )
                ),
                offset = DpOffset(x = 0.dp, y = 2.dp)
            )
        )
        .innerShadow(
            shape = shape,
            shadow = Shadow(
                radius = 16.dp,
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Theme.colorScheme.surfaceElevationHigh.copy(0f),
                        Theme.colorScheme.surfaceElevationHigh
                    )
                ),
                offset = DpOffset(x = 0.dp, y = (-2).dp)
            )
        )
        .border(
            width = 1.dp,
            brush = Brush.verticalGradient(
                colors = listOf(
                    Theme.colorScheme.outline,
                    Theme.colorScheme.outline.copy(0f)
                )
            ),
            shape = shape
        )
}