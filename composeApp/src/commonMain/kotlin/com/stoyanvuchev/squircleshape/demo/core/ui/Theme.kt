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

package com.stoyanvuchev.squircleshape.demo.core.ui

import androidx.compose.foundation.shape.CornerBasedShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import com.stoyanvuchev.squircleshape.demo.core.ui.color.ColorScheme
import com.stoyanvuchev.squircleshape.demo.core.ui.color.LocalColorScheme
import com.stoyanvuchev.squircleshape.demo.core.ui.shape.LocalRangedShape
import com.stoyanvuchev.squircleshape.demo.core.ui.shape.LocalUniversalShape
import com.stoyanvuchev.squircleshape.demo.core.ui.typography.LocalTypography
import com.stoyanvuchev.squircleshape.demo.core.ui.typography.Typography

object Theme {

    val universalShape: CornerBasedShape
        @Composable
        @ReadOnlyComposable
        get() = LocalUniversalShape.current

    val rangedShape: CornerBasedShape
        @Composable
        @ReadOnlyComposable
        get() = LocalRangedShape.current

    val colorScheme: ColorScheme
        @Composable
        @ReadOnlyComposable
        get() = LocalColorScheme.current

    val typography: Typography
        @Composable
        @ReadOnlyComposable
        get() = LocalTypography.current

}