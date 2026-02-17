/*
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

package com.stoyanvuchev.squircleshape.demo.core.ui.color

import androidx.compose.runtime.Stable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.graphics.Color

@Stable
data class ColorScheme(
    val primary: Color,
    val onPrimary: Color,
    val accent: Color,
    val onAccent: Color,
    val error: Color,
    val onError: Color,
    val surface: Color,
    val onSurface: Color,
    val surfaceElevationLow: Color,
    val onSurfaceElevationLow: Color,
    val surfaceElevationMedium: Color,
    val onSurfaceElevationMedium: Color,
    val surfaceElevationHigh: Color,
    val onSurfaceElevationHigh: Color,
    val outline: Color
)

fun colorSchemeFromTokens(tokens: ColorSchemeTokens) = ColorScheme(
    primary = tokens.primary,
    onPrimary = tokens.onPrimary,
    accent = tokens.accent,
    onAccent = tokens.onAccent,
    error = tokens.error,
    onError = tokens.onError,
    surface = tokens.surface,
    onSurface = tokens.onSurface,
    surfaceElevationLow = tokens.surfaceElevationLow,
    onSurfaceElevationLow = tokens.onSurfaceElevationLow,
    surfaceElevationMedium = tokens.surfaceElevationMedium,
    onSurfaceElevationMedium = tokens.onSurfaceElevationMedium,
    surfaceElevationHigh = tokens.surfaceElevationHigh,
    onSurfaceElevationHigh = tokens.onSurfaceElevationHigh,
    outline = tokens.outline
)

/**
 * Returns the "inverse" color of the given [color] within this [ColorScheme].
 *
 * The inverse is typically the associated content color for a background color
 * (e.g., `primary` ↔ `onPrimary`, `accent` ↔ `onAccent`, `error` ↔ `onError`).
 * If the color is not part of the palette, [Color.Unspecified] is returned.
 */
fun ColorScheme.inverseColor(color: Color) = when (color) {
    primary -> onPrimary
    onPrimary -> primary
    accent -> onAccent
    onAccent -> accent
    surface -> onSurface
    onSurface -> surface
    surfaceElevationLow -> onSurfaceElevationLow
    onSurfaceElevationLow -> surfaceElevationLow
    surfaceElevationMedium -> onSurfaceElevationMedium
    onSurfaceElevationMedium -> surfaceElevationMedium
    surfaceElevationHigh -> onSurfaceElevationHigh
    onSurfaceElevationHigh -> surfaceElevationHigh
    error -> onError
    onError -> error
    else -> Color.Unspecified
}

val LocalColorScheme = compositionLocalOf {
    colorSchemeFromTokens(tokens = ColorSchemeTokens.Light)
}

val LocalBackgroundColor = compositionLocalOf { Color.White }
val LocalContentColor = compositionLocalOf { Color.Black }