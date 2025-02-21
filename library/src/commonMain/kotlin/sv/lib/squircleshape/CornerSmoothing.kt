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

import androidx.compose.foundation.shape.RoundedCornerShape

/**
 * A collection of commonly used corner smoothing [Int] values for a [SquircleShape].
 */
object Smoothing {

    /**
     * Does not apply corner smoothing.
     * The result will be similar to a [RoundedCornerShape].
     */
    val None: Int get() = 0

    /**
     * Applies a small amount of corner smoothing,
     * resulting slightly pronounced [SquircleShape].
     */
    val Small: Int get() = 20

    /**
     * Applies a medium amount of corner smoothing,
     * resulting quite pronounced [SquircleShape].
     */
    val Medium: Int get() = 48

    /**
     * Applies a large amount of corner smoothing,
     * resulting highly pronounced [SquircleShape].
     */
    val Large: Int get() = 67

    /**
     * Applies a full amount of corner smoothing,
     * resulting fully pronounced [SquircleShape].
     */
    val Full: Int get() = 100

}

/**
 * A collection of commonly used corner smoothing [Float] values for a [SquircleShape].
 */
@Deprecated(
    message = "For a better smoothing control, " +
            "consider using the new Integer based Smoothing values. " +
            "The CornerSmoothing object will be removed in the future.",
    replaceWith = ReplaceWith("Smoothing.None, Smoothing.Small, Smoothing.Medium, Smoothing.Large, Smoothing.Full"),
    level = DeprecationLevel.WARNING
)
object CornerSmoothing {

    /**
     * Does not apply corner smoothing.
     * The result will be [RoundedCornerShape].
     */
    val None: Float get() = 0.55f

    /**
     * Applies a small amount of corner smoothing,
     * resulting slightly pronounced [SquircleShape].
     */
    val Small: Float get() = 0.67f

    /**
     * Applies a medium amount of corner smoothing,
     * resulting quite pronounced [SquircleShape].
     */
    val Medium: Float get() = 0.72f

    /**
     * Applies a high amount of corner smoothing,
     * resulting highly pronounced [SquircleShape].
     */
    val Large: Float get() = 0.8f

    /**
     * Applies a full amount of corner smoothing,
     * resulting fully pronounced [SquircleShape].
     */
    val Full: Float get() = 1f

}