/*
 * Copyright (c) 2023-2025 Stoyan Vuchev, Sylvain BARRÉ-PERSYN
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
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.unit.LayoutDirection
import sv.lib.squircleshape.gentleSquircleShapePath
import sv.lib.squircleshape.squircleShapePath
import sv.lib.squircleshape.SquircleShape
import sv.lib.squircleshape.GentleSquircleShape

/**
 * Creates an outline for a [SquircleShape], considering corner radii, smoothing, and layout direction.
 *
 * @param size The overall size of the shape as a [Size] object.
 * @param topStart The radius of the top-start corner in pixels. In left-to-right layouts, this corresponds to the top-left corner.
 * @param topEnd The radius of the top-end corner in pixels. In left-to-right layouts, this corresponds to the top-right corner.
 * @param bottomEnd The radius of the bottom-end corner in pixels. In left-to-right layouts, this corresponds to the bottom-right corner.
 * @param bottomStart The radius of the bottom-start corner in pixels. In left-to-right layouts, this corresponds to the bottom-left corner.
 * @param smoothing An Integer value between 0 and 100 that determines the smoothing of the corners.
 * @param upscaleCornerSize A Boolean value indicating whether to upscale the corner size.
 * @param layoutDirection The layout direction of the shape, specified as [LayoutDirection.Ltr] or [LayoutDirection.Rtl].
 *                        This affects how the corner radii are applied.
 * @return An [Outline] object representing the shape's boundary:
 *         - Returns a generic path representing the squircle shape.
 *
 * ##### Behavior:
 * - Clamps the corner radii to ensure they fit within the size of the shape.
 * - Adjusts corner radii based on the layout direction, swapping left and right corners in RTL layouts.
 * - Applies corner smoothing using the provided `smoothing` parameter to create a squircle-like effect.
 */
@Stable
internal fun createSquircleShapeOutline(
    size: Size,
    topStart: Float,
    topEnd: Float,
    bottomEnd: Float,
    bottomStart: Float,
    smoothing: Int,
    upscaleCornerSize: Boolean,
    layoutDirection: LayoutDirection
): Outline {
    val isRtl = layoutDirection == LayoutDirection.Rtl
    return Outline.Generic(
        path = squircleShapePath(
            size = size,
            topLeftCorner = if (isRtl) topStart else topEnd,
            topRightCorner = if (isRtl) topEnd else topStart,
            bottomLeftCorner = if (isRtl) bottomStart else bottomEnd,
            bottomRightCorner = if (isRtl) bottomEnd else bottomStart,
            smoothing = clampedSmoothing(smoothing),
            upscaleCornerSize = upscaleCornerSize
        )
    )
}

/**
 * Creates an outline for a [GentleSquircleShape], considering corner radii and layout direction.
 *
 * @param size The overall size of the shape as a [Size] object.
 * @param topStart The radius of the top-start corner in pixels. In left-to-right layouts, this corresponds to the top-left corner.
 * @param topEnd The radius of the top-end corner in pixels. In left-to-right layouts, this corresponds to the top-right corner.
 * @param bottomEnd The radius of the bottom-end corner in pixels. In left-to-right layouts, this corresponds to the bottom-right corner.
 * @param bottomStart The radius of the bottom-start corner in pixels. In left-to-right layouts, this corresponds to the bottom-left corner.
 * @param layoutDirection The layout direction of the shape, specified as [LayoutDirection.Ltr] or [LayoutDirection.Rtl].
 *                        This affects how the corner radii are applied.
 * @return An [Outline] object representing the shape's boundary:
 *         - Returns a generic path representing the gentle squircle shape.
 *
 * ##### Behavior:
 * - Clamps the corner radii to ensure they fit within the size of the shape.
 * - Adjusts corner radii based on the layout direction, swapping left and right corners in RTL layouts.
 */
@Stable
internal fun createGentleSquircleShapeOutline(
    size: Size,
    topStart: Float,
    topEnd: Float,
    bottomEnd: Float,
    bottomStart: Float,
    layoutDirection: LayoutDirection
): Outline {
    val isRtl = layoutDirection == LayoutDirection.Rtl
    return Outline.Generic(
        path = gentleSquircleShapePath(
            size = size,
            topLeftCorner = if (isRtl) topStart else topEnd,
            topRightCorner = if (isRtl) topEnd else topStart,
            bottomLeftCorner = if (isRtl) bottomStart else bottomEnd,
            bottomRightCorner = if (isRtl) bottomEnd else bottomStart
        )
    )
}