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

package com.stoyanvuchev.squircleshape.demo.core.ui.typography

import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.unit.sp

object TypographyTokens {

    val titleLarge: TextStyle
        get() = DefaultTextStyle.copy(
            fontFamily = DefaultFontFamily,
            fontSize = 32.sp,
            lineHeight = 40.sp
        )

    val titleSmall: TextStyle
        get() = DefaultTextStyle.copy(
            fontFamily = DefaultFontFamily,
            fontSize = 18.sp,
            lineHeight = 24.sp
        )

    val bodyLarge: TextStyle
        get() = DefaultTextStyle.copy(
            fontFamily = DefaultFontFamily,
            fontSize = 14.sp,
            lineHeight = 20.sp
        )

    val bodySmall: TextStyle
        get() = DefaultTextStyle.copy(
            fontFamily = DefaultFontFamily,
            fontSize = 14.sp,
            lineHeight = 20.sp
        )

}

internal val DefaultLineHeightStyle
    get() = LineHeightStyle(
        alignment = LineHeightStyle.Alignment.Center,
        trim = LineHeightStyle.Trim.None
    )

internal val DefaultTextStyle
    get() = TextStyle.Default.copy(
        platformStyle = null,
        lineHeightStyle = DefaultLineHeightStyle,
    )

internal val DefaultFontFamily
    get() = FontFamily.SansSerif