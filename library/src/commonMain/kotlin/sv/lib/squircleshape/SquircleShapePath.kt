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
 *  @param upscaleCornerSize Whether to upscale the corner size in order to preserve the roundness when a larger corner smoothing is applied.
 *
 **/
fun squircleShapePath(
    size: Size,
    topLeftCorner: Float,
    topRightCorner: Float,
    bottomLeftCorner: Float,
    bottomRightCorner: Float,
    smoothing: Int = Smoothing.Medium,
    upscaleCornerSize: Boolean = true
): Path {

    val topLeft = clampedCornerRadius(
        size = size,
        cornerSize = topLeftCorner,
        upscaleCornerSize = upscaleCornerSize,
        smoothing = smoothing
    )

    val topRight = clampedCornerRadius(
        size = size,
        cornerSize = topRightCorner,
        upscaleCornerSize = upscaleCornerSize,
        smoothing = smoothing
    )

    val bottomLeft = clampedCornerRadius(
        size = size,
        cornerSize = bottomLeftCorner,
        upscaleCornerSize = upscaleCornerSize,
        smoothing = smoothing
    )

    val bottomRight = clampedCornerRadius(
        size = size,
        cornerSize = bottomRightCorner,
        upscaleCornerSize = upscaleCornerSize,
        smoothing = smoothing
    )

    val smoothingFactor = 1f - convertIntBasedSmoothingToFloat(smoothing)
    return squircleShapePathImpl(
        size = size,
        topLeftCorner = topLeft,
        topRightCorner = topRight,
        bottomRightCorner = bottomRight,
        bottomLeftCorner = bottomLeft,
        smoothingFactor = smoothingFactor
    )

}

/**
 *
 *  The path used for drawing a typical Squircle shape.
 *
 *  @param size The size of the shape in pixels.
 *  @param topLeftCorner The top left corner radius in pixels.
 *  @param topRightCorner The top right corner radius in pixels.
 *  @param bottomLeftCorner The bottom left corner radius in pixels.
 *  @param bottomRightCorner The bottom right corner radius in pixels.
 *  @param cornerSmoothing (0.55f - rounded corner shape, 1f - fully pronounced squircle).
 *
 **/
@Deprecated(
    message = "Consider using the new squircleShapePath() function " +
            "with an integer based corner smoothing " +
            "for a better control over the shape. \n" +
            "This function will be removed in the future.",
    replaceWith = ReplaceWith(
        """
            squircleShapePath(
                size = size,
                topLeftCorner = topLeftCorner,
                topRightCorner = topRightCorner,
                bottomLeftCorner = bottomLeftCorner,
                bottomRightCorner = bottomRightCorner,
                smoothing = 50,
                upscaleCornerSize = true
            )
        """
    ),
    level = DeprecationLevel.WARNING
)
fun squircleShapePath(
    size: Size,
    topLeftCorner: Float,
    topRightCorner: Float,
    bottomLeftCorner: Float,
    bottomRightCorner: Float,
    cornerSmoothing: Float = CornerSmoothing.Medium
): Path {
    val smoothingFactor = 1f - cornerSmoothing
    return squircleShapePathImpl(
        size = size,
        topLeftCorner = topLeftCorner,
        topRightCorner = topRightCorner,
        bottomRightCorner = bottomRightCorner,
        bottomLeftCorner = bottomLeftCorner,
        smoothingFactor = smoothingFactor
    )
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
 * @param upscaleCornerSize Whether to upscale the corner size in order to preserve the roundness when a larger corner smoothing is applied.
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
    smoothing: Int = Smoothing.Medium,
    upscaleCornerSize: Boolean = true,
    style: DrawStyle = Fill,
    alpha: Float = 1.0f,
    colorFilter: ColorFilter? = null,
    blendMode: BlendMode = DrawScope.DefaultBlendMode
) {

    val isRtl = this.layoutDirection == LayoutDirection.Rtl
    val path = squircleShapePath(
        size = size,
        topLeftCorner = if (isRtl) topLeftCorner else topRightCorner,
        topRightCorner = if (isRtl) topRightCorner else topLeftCorner,
        bottomLeftCorner = if (isRtl) bottomLeftCorner else bottomRightCorner,
        bottomRightCorner = if (isRtl) bottomRightCorner else bottomLeftCorner,
        smoothing = smoothing,
        upscaleCornerSize = upscaleCornerSize
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
 * @param cornerSmoothing (0.55f - rounded corner shape, 1f - fully pronounced squircle).
 * @param alpha Opacity to be applied to Squircle from 0.0f to 1.0f representing fully transparent to fully opaque respectively.
 * @param style Specifies whether the Squircle is stroked or filled in.
 * @param colorFilter ColorFilter to apply to the [color] when drawn into the destination.
 * @param blendMode Blending algorithm to be applied to the color.
 *
 */
@Deprecated(
    message = "Consider using the new drawSquircle() function " +
            "with an integer based corner smoothing " +
            "for a better control over the shape. \n" +
            "This function will be removed in the future.",
    replaceWith = ReplaceWith(
        """
            drawSquircle(
                color = Color.Blue,
                topLeft = topLeft,
                size = size,
                topLeftCorner = topStart.toPx(),
                topRightCorner = topEnd.toPx(),
                bottomLeftCorner = bottomStart.toPx(),
                bottomRightCorner = bottomEnd.toPx(),
                smoothing = 75,
                upscaleCornerSize = true
            )
        """
    ),
    level = DeprecationLevel.WARNING
)
fun DrawScope.drawSquircle(
    color: Color,
    topLeft: Offset = Offset.Zero,
    size: Size,
    topLeftCorner: Float,
    topRightCorner: Float,
    bottomLeftCorner: Float,
    bottomRightCorner: Float,
    cornerSmoothing: Float = 0.72f,
    style: DrawStyle = Fill,
    alpha: Float = 1.0f,
    colorFilter: ColorFilter? = null,
    blendMode: BlendMode = DrawScope.DefaultBlendMode,
) {

    val isRtl = this.layoutDirection == LayoutDirection.Rtl
    val path = squircleShapePath(
        size = size,
        topLeftCorner = if (isRtl) topLeftCorner else topRightCorner,
        topRightCorner = if (isRtl) topRightCorner else topLeftCorner,
        bottomLeftCorner = if (isRtl) bottomLeftCorner else bottomRightCorner,
        bottomRightCorner = if (isRtl) bottomRightCorner else bottomLeftCorner,
        cornerSmoothing = cornerSmoothing
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
 * @param upscaleCornerSize Whether to upscale the corner size in order to preserve the roundness when a larger corner smoothing is applied.
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
    smoothing: Int = Smoothing.Medium,
    upscaleCornerSize: Boolean = true,
    style: DrawStyle = Fill,
    alpha: Float = 1.0f,
    colorFilter: ColorFilter? = null,
    blendMode: BlendMode = DrawScope.DefaultBlendMode
) {

    val isRtl = this.layoutDirection == LayoutDirection.Rtl
    val path = squircleShapePath(
        size = size,
        topLeftCorner = if (isRtl) topLeftCorner else topRightCorner,
        topRightCorner = if (isRtl) topRightCorner else topLeftCorner,
        bottomLeftCorner = if (isRtl) bottomLeftCorner else bottomRightCorner,
        bottomRightCorner = if (isRtl) bottomRightCorner else bottomLeftCorner,
        smoothing = smoothing,
        upscaleCornerSize = upscaleCornerSize
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
 * @param cornerSmoothing (0.55f - rounded corner shape, 1f - fully pronounced squircle).
 * @param alpha Opacity to be applied to Squircle from 0.0f to 1.0f representing fully transparent to fully opaque respectively.
 * @param style Specifies whether the Squircle is stroked or filled in.
 * @param colorFilter ColorFilter to apply to the [brush] when drawn into the destination.
 * @param blendMode Blending algorithm to be applied to the color.
 *
 */
@Deprecated(
    message = "Consider using the new drawSquircle() function " +
            "with an integer based corner smoothing " +
            "for a better control over the shape. \n" +
            "This function will be removed in the future.",
    replaceWith = ReplaceWith(
        """
            drawSquircle(
                brush = Brush.radialGradient(colors),
                topLeft = topLeft,
                size = size,
                topLeftCorner = topStart.toPx(),
                topRightCorner = topEnd.toPx(),
                bottomLeftCorner = bottomStart.toPx(),
                bottomRightCorner = bottomEnd.toPx(),
                smoothing = 75,
                upscaleCornerSize = true
            )
        """
    ),
    level = DeprecationLevel.WARNING
)
fun DrawScope.drawSquircle(
    brush: Brush,
    topLeft: Offset = Offset.Zero,
    size: Size,
    topLeftCorner: Float,
    topRightCorner: Float,
    bottomLeftCorner: Float,
    bottomRightCorner: Float,
    cornerSmoothing: Float = 0.67f,
    style: DrawStyle = Fill,
    alpha: Float = 1.0f,
    colorFilter: ColorFilter? = null,
    blendMode: BlendMode = DrawScope.DefaultBlendMode,
    upscaleCornerSize: Boolean = true
) {

    val isRtl = this.layoutDirection == LayoutDirection.Rtl
    val path = squircleShapePath(
        size = size,
        topLeftCorner = if (isRtl) topLeftCorner else topRightCorner,
        topRightCorner = if (isRtl) topRightCorner else topLeftCorner,
        bottomLeftCorner = if (isRtl) bottomLeftCorner else bottomRightCorner,
        bottomRightCorner = if (isRtl) bottomRightCorner else bottomLeftCorner,
        cornerSmoothing = cornerSmoothing
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

/**
 * The implementation of a [squircleShapePath].
 *
 *  @param size The size of the shape in pixels.
 *  @param topLeftCorner The top left corner radius in pixels.
 *  @param topRightCorner The top right corner radius in pixels.
 *  @param bottomLeftCorner The bottom left corner radius in pixels.
 *  @param bottomRightCorner The bottom right corner radius in pixels.
 *  @param smoothingFactor The corner smoothing factor
 */
private fun squircleShapePathImpl(
    size: Size,
    topLeftCorner: Float,
    topRightCorner: Float,
    bottomLeftCorner: Float,
    bottomRightCorner: Float,
    smoothingFactor: Float,
): Path = Path().apply {

    // Extract the shape width & height
    val width = size.width
    val height = size.height

    // Set the starting point at the coordinate of (x = topLeft corner, y = 0).
    moveTo(
        x = topLeftCorner,
        y = 0f
    )

    // Draw a Line to the coordinate of (x = the width - the top right corner).
    lineTo(
        x = width - topRightCorner,
        y = 0f
    )

    // Draw a Cubic from the coordinate of (x1 = the width - the top right corner * (1 - the corner smoothing), y1 = 0)
    // with a mid point at the coordinate of (x2 = the width, y2 = the top right corner * (1 - the corner smoothing))
    // to the end point at the coordinate of (x3 = the width, y3 = the top right corner).
    cubicTo(
        x1 = width - topRightCorner * smoothingFactor,
        y1 = 0f,
        x2 = width,
        y2 = topRightCorner * smoothingFactor,
        x3 = width,
        y3 = topRightCorner
    )

    // Draw a Line to the coordinate of (x = the width, y = the height - the bottom right corner).
    lineTo(
        x = width,
        y = height - bottomRightCorner
    )

    // Draw a Cubic from the coordinate of (x1 = the width, y1 = the height - the bottom right corner * (1 - the corner smoothing))
    // with a mid point at the coordinate of (x2 = the width - the bottom right corner * (1 - the corner smoothing), y2 = the height)
    // to the end point at the coordinate of (x3 = the width - the bottom right corner, y3 = the height).
    cubicTo(
        x1 = width,
        y1 = height - bottomRightCorner * smoothingFactor,
        x2 = width - bottomRightCorner * smoothingFactor,
        y2 = height,
        x3 = width - bottomRightCorner,
        y3 = height
    )

    // Draw a Line to the coordinate of (x = the bottom left corner, y = the height).
    lineTo(
        x = bottomLeftCorner,
        y = height
    )

    // Draw a Cubic from the coordinate of (x1 = the bottom left corner * (1 - the corner smoothing), y1 = the height)
    // with a mid point at the coordinate of (x2 = 0, y2 = the height - the bottom left corner * (1 - the corner smoothing))
    // to the end point at the coordinate of (x3 = 0, y3 = the height - the bottom left corner).
    cubicTo(
        x1 = bottomLeftCorner * smoothingFactor,
        y1 = height,
        x2 = 0f,
        y2 = height - bottomLeftCorner * smoothingFactor,
        x3 = 0f,
        y3 = height - bottomLeftCorner
    )

    // Draw a Line to the coordinate of (x = 0, y = the top left corner).
    lineTo(
        x = 0f,
        y = topLeftCorner
    )

    // Draw a Cubic from the coordinate of (x1 = 0, y1 = the top left corner * (1 - the corner smoothing))
    // with a mid point at the coordinate of (x2 = the top left corner * (1 - the corner smoothing), y2 = 0)
    // to the end point at the coordinate of (x3 = the top left corner, y3 = 0.
    cubicTo(
        x1 = 0f,
        y1 = topLeftCorner * smoothingFactor,
        x2 = topLeftCorner * smoothingFactor,
        y2 = 0f,
        x3 = topLeftCorner,
        y3 = 0f
    )

    // Close the [Path].
    close()

}