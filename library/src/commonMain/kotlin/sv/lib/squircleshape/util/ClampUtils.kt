/*
 * Copyright (c) 2023-2025 Stoyan Vuchev
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

package sv.lib.squircleshape.util

import androidx.compose.runtime.Stable
import androidx.compose.ui.geometry.Size

/**
 *
 *  Clamps the corner radius from 0.0f to the size of the smallest axis.
 *
 *  @param cornerSize The corner radius in pixels.
 *  @param size The size of the shape.
 *
 **/
@Stable
internal fun clampedCornerRadius(
    cornerSize: Float,
    size: Size
): Float {
    val smallestAxis = size.minDimension / 2
    return cornerSize.coerceIn(0f, smallestAxis)
}

/**
 *
 *  Clamps the corner radius from 0.0f to the size of the smallest axis.
 *
 *  @param size The size of the shape.
 *  @param cornerSize The corner radius in pixels.
 *  @param upscaleCornerSize Whether to upscale the corner size in order to preserve the roundness when a larger corner smoothing is applied.
 *  @param smoothing The corner smoothing of the shape from 0 to 100.
 *
 **/
@Stable
internal fun clampedCornerRadius(
    size: Size,
    cornerSize: Float,
    upscaleCornerSize: Boolean,
    smoothing: Int,
): Float {
    val smallestAxis = size.minDimension / 2
    val factor = if (!upscaleCornerSize) 1f
    else transformFraction(
        value = smoothing.toFloat(),
        startX = 0f,
        endX = 100f,
        startY = 1f,
        endY = 2.33f
    ) * transformFraction(
        value = cornerSize,
        startX = 0f,
        endX = smallestAxis,
        startY = 1f,
        endY = .8f
    )
    return (cornerSize * factor).coerceIn(0f, smallestAxis)
}

/**
 *
 *  Clamps the corner smoothing from 0 to 100.
 *
 *  @param smoothing The corner smoothing.
 *
 **/
@Stable
internal fun clampedSmoothing(smoothing: Int): Int = smoothing.coerceIn(0, 100)

/**
 *
 *  Clamps the corner smoothing gradually from maximum to minimum based on the corner radius.
 *
 *  @param radius The corner smoothing.
 *  @param cornerThreshold The corner threshold.
 *
 **/
@Stable
internal fun clampedSmoothing(
    radius: Float,
    cornerThreshold: Float
): Float = transformFraction(
    value = radius.coerceAtMost(cornerThreshold),
    startX = 0f,
    endX = cornerThreshold,
    startY = 0f,
    endY = .45f
)