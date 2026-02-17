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

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontVariation
import androidx.compose.ui.unit.sp
import org.jetbrains.compose.resources.Font
import squircleshape.composeapp.generated.resources.Res
import squircleshape.composeapp.generated.resources.roboto_flex

@Stable
@Composable
fun defaultTypography(): Typography {
    val typography by rememberUpdatedState(
        Typography().run {
            copy(
                titleLarge = titleLarge.copy(
                    fontFamily = FontFamily(
                        robotoFlexStyle(
                            weight = 500,
                            width = 150f
                        )
                    ),
                    fontSize = 32.sp,
                    lineHeight = 40.sp
                ),
                titleSmall = titleSmall.copy(
                    fontFamily = FontFamily(
                        robotoFlexStyle(
                            weight = 500,
                            width = 150f
                        )
                    ),
                    fontSize = 20.sp,
                    lineHeight = 28.sp
                ),
                bodyLarge = bodyLarge.copy(
                    fontFamily = FontFamily(
                        robotoFlexStyle(
                            weight = 500,
                            width = 128f
                        )
                    ),
                    fontSize = 16.sp,
                    lineHeight = 24.sp
                ),
                bodySmall = bodySmall.copy(
                    fontFamily = FontFamily(
                        robotoFlexStyle(
                            weight = 400,
                            width = 100f,
                            grade = 25f   // subtle optical boost for small text
                        )
                    ),
                    fontSize = 14.sp,
                    lineHeight = 20.sp
                ),
            )
        }
    )
    return typography
}

@Composable
private fun robotoFlexStyle(
    weight: Int,
    slant: Float = 0f,
    width: Float = 100f,
    grade: Float = 0f
): Font {
    return Font(
        Res.font.roboto_flex,
        variationSettings = FontVariation.Settings(
            FontVariation.weight(weight),
            FontVariation.slant(slant),
            FontVariation.width(width),
            FontVariation.Setting("GRAD", grade)
        )
    )
}

@Immutable
data class Typography(
    val titleLarge: TextStyle = TypographyTokens.titleLarge,
    val titleSmall: TextStyle = TypographyTokens.titleSmall,
    val bodyLarge: TextStyle = TypographyTokens.bodyLarge,
    val bodySmall: TextStyle = TypographyTokens.bodySmall
)

val LocalTypography = staticCompositionLocalOf { Typography() }
val LocalTextStyle = compositionLocalOf { DefaultTextStyle }