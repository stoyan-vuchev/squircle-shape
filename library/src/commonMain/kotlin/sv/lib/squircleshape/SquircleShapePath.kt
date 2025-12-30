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

package sv.lib.squircleshape

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.DrawStyle
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.unit.LayoutDirection
import sv.lib.squircleshape.util.clampedCornerRadius
import sv.lib.squircleshape.util.convertIntBasedSmoothingToFloat

/**
 *
 *  The path used for drawing a typical Squircle shape.
 *
 *  @param size The size of the shape in pixels.
 *  @param topLeftCorner The top left corner radius in pixels.
 *  @param topRightCorner The top right corner radius in pixels.
 *  @param bottomLeftCorner The bottom left corner radius in pixels.
 *  @param bottomRightCorner The bottom right corner radius in pixels.
 *  @param smoothing The corner smoothing from 0 to 100.
 *
 **/
fun squircleShapePath(
    size: Size,
    topLeftCorner: Float,
    topRightCorner: Float,
    bottomLeftCorner: Float,
    bottomRightCorner: Float,
    smoothing: Int = CornerSmoothing.Medium
): Path {

    val topLeft = clampedCornerRadius(
        size = size,
        cornerSize = topLeftCorner
    )

    val topRight = clampedCornerRadius(
        size = size,
        cornerSize = topRightCorner
    )

    val bottomLeft = clampedCornerRadius(
        size = size,
        cornerSize = bottomLeftCorner
    )

    val bottomRight = clampedCornerRadius(
        size = size,
        cornerSize = bottomRightCorner
    )

    val smoothingFactor = 1f - convertIntBasedSmoothingToFloat(smoothing)
    return Path().apply {

        // Factor to control edge pinch (adjust between 0.1–0.3 for subtle effect).
        val edgePinch = .2f
        val width = size.width
        val height = size.height

        moveTo(x = topLeft, y = 0f)

        // Top edge
        cubicTo(
            x1 = width * edgePinch,
            y1 = 0f,
            x2 = width - topRight * (1 - edgePinch),
            y2 = 0f,
            x3 = width - topRight,
            y3 = 0f
        )

        // Top-right corner
        cubicTo(
            x1 = width - topRight * smoothingFactor,
            y1 = 0f,
            x2 = width,
            y2 = topRight * smoothingFactor,
            x3 = width,
            y3 = topRight
        )

        // Right edge
        cubicTo(
            x1 = width,
            y1 = height * edgePinch,
            x2 = width,
            y2 = height - bottomRight * (1 - edgePinch),
            x3 = width,
            y3 = height - bottomRight
        )

        // Bottom-right corner
        cubicTo(
            x1 = width,
            y1 = height - bottomRight * smoothingFactor,
            x2 = width - bottomRight * smoothingFactor,
            y2 = height,
            x3 = width - bottomRight,
            y3 = height
        )

        // Bottom edge
        cubicTo(
            x1 = width * (1 - edgePinch),
            y1 = height,
            x2 = bottomLeft * (1 - edgePinch),
            y2 = height,
            x3 = bottomLeft,
            y3 = height
        )

        // Bottom-left corner
        cubicTo(
            x1 = bottomLeft * smoothingFactor,
            y1 = height,
            x2 = 0f,
            y2 = height - bottomLeft * smoothingFactor,
            x3 = 0f,
            y3 = height - bottomLeft
        )

        // Left edge
        cubicTo(
            x1 = 0f,
            y1 = height * (1 - edgePinch),
            x2 = 0f,
            y2 = topLeft * (1 - edgePinch),
            x3 = 0f,
            y3 = topLeft
        )

        // Top-left corner
        cubicTo(
            x1 = 0f,
            y1 = topLeft * smoothingFactor,
            x2 = topLeft * smoothingFactor,
            y2 = 0f,
            x3 = topLeft,
            y3 = 0f
        )

        close()

    }

}

/**
 *
 * Draws a Squircle with the given [Color]. Whether the Squircle is
 * filled or stroked (or both) is controlled by [Paint.style].
 *
 * @param color The color to be applied to the Squircle.
 * @param topLeft Offset from the local origin of 0, 0 relative to the current translation.
 * @param size Dimensions of the Squircle to draw.
 * @param topLeftCorner The top left corner radius in pixels.
 * @param topRightCorner The top right corner radius in pixels.
 * @param bottomLeftCorner The bottom left corner radius in pixels.
 * @param bottomRightCorner The bottom right corner radius in pixels.
 * @param smoothing The smoothing factor from 0 to 100.
 * @param alpha Opacity to be applied to Squircle from 0.0f to 1.0f representing fully transparent to fully opaque respectively.
 * @param style Specifies whether the Squircle is stroked or filled in.
 * @param colorFilter ColorFilter to apply to the [color] when drawn into the destination.
 * @param blendMode Blending algorithm to be applied to the color.
 *
 */
fun DrawScope.drawSquircle(
    color: Color,
    topLeft: Offset = Offset.Zero,
    size: Size,
    topLeftCorner: Float,
    topRightCorner: Float,
    bottomLeftCorner: Float,
    bottomRightCorner: Float,
    smoothing: Int = CornerSmoothing.Medium,
    style: DrawStyle = Fill,
    alpha: Float = 1.0f,
    colorFilter: ColorFilter? = null,
    blendMode: BlendMode = DrawScope.DefaultBlendMode
) {

    val isRtl = this.layoutDirection == LayoutDirection.Rtl
    val path = squircleShapePath(
        size = size,
        topLeftCorner = if (isRtl) topRightCorner else topLeftCorner,
        topRightCorner = if (isRtl) topLeftCorner else topRightCorner,
        bottomLeftCorner = if (isRtl) bottomRightCorner else bottomLeftCorner,
        bottomRightCorner = if (isRtl) bottomLeftCorner else bottomRightCorner,
        smoothing = smoothing
    )

    translate(
        left = topLeft.x,
        top = topLeft.y
    ) {

        drawPath(
            path = path,
            color = color,
            alpha = alpha,
            style = style,
            colorFilter = colorFilter,
            blendMode = blendMode
        )

    }

}

/**
 *
 * Draws a Squircle with the given [Brush]. Whether the Squircle is
 * filled or stroked (or both) is controlled by [Paint.style].
 *
 * @param brush The brush to be applied to the Squircle.
 * @param topLeft Offset from the local origin of 0, 0 relative to the current translation.
 * @param size Dimensions of the Squircle to draw.
 * @param topLeftCorner The top left corner radius in pixels.
 * @param topRightCorner The top right corner radius in pixels.
 * @param bottomLeftCorner The bottom left corner radius in pixels.
 * @param bottomRightCorner The bottom right corner radius in pixels.
 * @param smoothing The smoothing factor from 0 to 100.
 * @param alpha Opacity to be applied to Squircle from 0.0f to 1.0f representing fully transparent to fully opaque respectively.
 * @param style Specifies whether the Squircle is stroked or filled in.
 * @param colorFilter ColorFilter to apply to the [brush] when drawn into the destination.
 * @param blendMode Blending algorithm to be applied to the color.
 *
 */
fun DrawScope.drawSquircle(
    brush: Brush,
    topLeft: Offset = Offset.Zero,
    size: Size,
    topLeftCorner: Float,
    topRightCorner: Float,
    bottomLeftCorner: Float,
    bottomRightCorner: Float,
    smoothing: Int = CornerSmoothing.Medium,
    style: DrawStyle = Fill,
    alpha: Float = 1.0f,
    colorFilter: ColorFilter? = null,
    blendMode: BlendMode = DrawScope.DefaultBlendMode
) {

    val isRtl = this.layoutDirection == LayoutDirection.Rtl
    val path = squircleShapePath(
        size = size,
        topLeftCorner = if (isRtl) topRightCorner else topLeftCorner,
        topRightCorner = if (isRtl) topLeftCorner else topRightCorner,
        bottomLeftCorner = if (isRtl) bottomRightCorner else bottomLeftCorner,
        bottomRightCorner = if (isRtl) bottomLeftCorner else bottomRightCorner,
        smoothing = smoothing
    )

    translate(
        left = topLeft.x,
        top = topLeft.y
    ) {

        drawPath(
            path = path,
            brush = brush,
            alpha = alpha,
            style = style,
            colorFilter = colorFilter,
            blendMode = blendMode
        )

    }

}