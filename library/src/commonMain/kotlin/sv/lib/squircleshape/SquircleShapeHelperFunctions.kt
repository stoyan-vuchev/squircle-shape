/*
 * Copyright (c) 2023 Stoyan Vuchev
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

package sv.lib.squircleshape

import androidx.compose.runtime.Stable
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.geometry.toRect
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.unit.LayoutDirection

/**
 * Creates an outline for a [SquircleShape], considering corner radii, smoothing, and layout direction.
 *
 * @param size The overall size of the shape as a [Size] object.
 * @param topStart The radius of the top-start corner in pixels. In left-to-right layouts, this corresponds to the top-left corner.
 * @param topEnd The radius of the top-end corner in pixels. In left-to-right layouts, this corresponds to the top-right corner.
 * @param bottomEnd The radius of the bottom-end corner in pixels. In left-to-right layouts, this corresponds to the bottom-right corner.
 * @param bottomStart The radius of the bottom-start corner in pixels. In left-to-right layouts, this corresponds to the bottom-left corner.
 * @param cornerSmoothing A float value between 0 and 1 that determines the smoothing of the corners.
 *                        A value of 0 represents sharp corners, while 1 represents fully rounded transitions.
 * @param layoutDirection The layout direction of the shape, specified as [LayoutDirection.Ltr] or [LayoutDirection.Rtl].
 *                        This affects how the corner radii are applied.
 * @return An [Outline] object representing the shape's boundary:
 *         - Returns a rectangle if all corner radii are zero.
 *         - Returns a generic path representing the squircle shape if any corner radius is non-zero.
 *
 * ##### Behavior:
 * - Clamps the corner radii to ensure they fit within the size of the shape.
 * - Adjusts corner radii based on the layout direction, swapping left and right corners in RTL layouts.
 * - Applies corner smoothing using the provided `cornerSmoothing` parameter to create a squircle-like effect.
 */
@Stable
internal fun createSquircleShapeOutline(
    size: Size,
    topStart: Float,
    topEnd: Float,
    bottomEnd: Float,
    bottomStart: Float,
    cornerSmoothing: Float,
    layoutDirection: LayoutDirection
): Outline = if (topStart + topEnd + bottomEnd + bottomStart == 0.0f) {
    Outline.Rectangle(size.toRect())
} else {

    val isLtr = layoutDirection == LayoutDirection.Ltr
    val topLeftCorner = clampedCornerRadius(if (isLtr) topStart else topEnd, size)
    val topRightCorner = clampedCornerRadius(if (isLtr) topEnd else topStart, size)
    val bottomLeftCorner = clampedCornerRadius(if (isLtr) bottomStart else bottomEnd, size)
    val bottomRightCorner = clampedCornerRadius(if (isLtr) bottomEnd else bottomStart, size)

    Outline.Generic(
        path = squircleShapePath(
            size = size,
            topLeftCorner = topLeftCorner,
            topRightCorner = topRightCorner,
            bottomLeftCorner = bottomLeftCorner,
            bottomRightCorner = bottomRightCorner,
            cornerSmoothing = clampedCornerSmoothing(cornerSmoothing)
        )
    )

}

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
    return cornerSize.coerceIn(0.0f, smallestAxis)
}

/**
 *
 *  Clamps the corner smoothing from 0.55f to 1f.
 *
 *  @param cornerSmoothing (0.55f - rounded corner shape, 1f - fully pronounced squircle).
 *
 **/
@Stable
internal fun clampedCornerSmoothing(cornerSmoothing: Float) = cornerSmoothing.coerceIn(0.55f, 1f)