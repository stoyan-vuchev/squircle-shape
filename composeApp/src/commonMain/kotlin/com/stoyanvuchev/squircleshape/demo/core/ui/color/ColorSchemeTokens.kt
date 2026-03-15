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

package com.stoyanvuchev.squircleshape.demo.core.ui.color

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color

@Immutable
sealed class ColorSchemeTokens(
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
    val outline: Color,
    val shadow: Color
) {

    data object Light : ColorSchemeTokens(
        primary = Color(0xFF00598D),
        onPrimary = Color(0xFFE3FAFD),
        accent = Color(0xFF005A1F),
        onAccent = Color(0xFFEDF8F1),
        error = Color(0xFF960017),
        onError = Color(0xFFFFF2F1),
        surface = Color(0xFFDAE4E7),
        onSurface = Color(0xFF264249),
        surfaceElevationLow = Color(0xFFE6EDEF),
        onSurfaceElevationLow = Color(0xFF060F12),
        surfaceElevationMedium = Color(0xFFF3F6F7),
        onSurfaceElevationMedium = Color(0xFF020608),
        surfaceElevationHigh = Color(0xFFFFFFFF),
        onSurfaceElevationHigh = Color(0xFF020608),
        outline = Color.White.copy(alpha = .33f),
        shadow = Color(0x291E373D)
    )

    data object Dark : ColorSchemeTokens(
        primary = Color(0xFF00C8F7),
        onPrimary = Color(0xFF000914),
        accent = Color(0xFF38C284),
        onAccent = Color(0xFF000901),
        error = Color(0xFFFF857F),
        onError = Color(0xFF160001),
        surface = Color(0xFF060F12),
        onSurface = Color(0xFFA9C2C8),
        surfaceElevationLow = Color(0xFF0B181C),
        onSurfaceElevationLow = Color(0xFFE6EDEF),
        surfaceElevationMedium = Color(0xFF112227),
        onSurfaceElevationMedium = Color(0xFFF3F6F7),
        surfaceElevationHigh = Color(0xFF182C32),
        onSurfaceElevationHigh = Color(0xFFF3F6F7),
        outline = Color.White.copy(alpha = .08f),
        shadow = Color(0xFF060F12)
    )

}