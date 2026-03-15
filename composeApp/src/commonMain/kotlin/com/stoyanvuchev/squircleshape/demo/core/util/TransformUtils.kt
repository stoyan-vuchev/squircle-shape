/*
 * Copyright 2025 Assertive UI (assertiveui.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.stoyanvuchev.squircleshape.demo.core.util

/**
 * A utility function to transform a [Float] fraction value from one range to another.
 *
 * @param value The input value to be transformed.
 * @param startX The start of the input range.
 * @param endX The end of the input range.
 * @param startY The start of the output range.
 * @param endY The end of the output range.
 *
 * @return The transformed value.
 */
fun transformFraction(
    value: Float,
    startX: Float = 0f,
    endX: Float = 1f,
    startY: Float,
    endY: Float
): Float {

    // Check if startX is less than endX
    val newStartX = if (startX <= endX) startX else endX
    val newEndX = if (startX <= endX) endX else startX

    // Check if startY is less than endY
    val newStartY = if (startY <= endY) startY else endY
    val newEndY = if (startY <= endY) endY else startY

    // Transform the value to the new range
    return ((value - newStartX) / (newEndX - newStartX)) * (newEndY - newStartY) + newStartY

}

/**
 * Computes a triangular wave function for a given fraction value.
 *
 * This function smoothly interpolates the value between `1f` and `0f` based on the input fraction:
 * - Starts at `1f` when `fraction = 0f`
 * - Decreases linearly to `0f` when `fraction = 0.5f`
 * - Increases back to `1f` when `fraction = 1f`
 *
 * Mathematically, it follows the equation:
 * ```
 * f(x) = 1 - 2 * |x - 0.5|
 * ```
 *
 * @param fraction A floating-point value in the range `[0f, 1f]`, representing the input fraction.
 * @return A floating-point value in the range `[0f, 1f]`, following a triangular wave pattern.
 */
fun triangleFraction(value: Float): Float {
    return 1f - 2f * kotlin.math.abs(value - 0.5f)
}