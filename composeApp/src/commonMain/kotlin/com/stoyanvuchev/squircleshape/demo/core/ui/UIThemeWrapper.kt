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

package com.stoyanvuchev.squircleshape.demo.core.ui

import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.retain.retain
import androidx.compose.ui.UiComposable
import androidx.compose.ui.unit.DpSize
import com.stoyanvuchev.squircleshape.demo.core.ui.color.ColorSchemeTokens
import com.stoyanvuchev.squircleshape.demo.core.ui.color.LocalBackgroundColor
import com.stoyanvuchev.squircleshape.demo.core.ui.color.LocalColorScheme
import com.stoyanvuchev.squircleshape.demo.core.ui.color.LocalContentColor
import com.stoyanvuchev.squircleshape.demo.core.ui.color.colorSchemeFromTokens
import com.stoyanvuchev.squircleshape.demo.core.ui.shape.LocalShapeData
import com.stoyanvuchev.squircleshape.demo.core.ui.shape.LocalShapes
import com.stoyanvuchev.squircleshape.demo.core.ui.shape.ShapeData
import com.stoyanvuchev.squircleshape.demo.core.ui.shape.Shapes
import com.stoyanvuchev.squircleshape.demo.core.ui.typography.LocalTextStyle
import com.stoyanvuchev.squircleshape.demo.core.ui.typography.LocalTypography
import com.stoyanvuchev.squircleshape.demo.core.ui.typography.defaultTypography
import com.stoyanvuchev.squircleshape.demo.core.ui.util.window.LocalWindowState
import com.stoyanvuchev.squircleshape.demo.core.ui.util.window.rememberWindowState

@Composable
@UiComposable
fun UIThemeWrapper(
    themeMode: ThemeMode = ThemeMode.SYSTEM,
    content: @Composable () -> Unit
) = BoxWithConstraints {

    val typography = defaultTypography()
    val windowState by rememberWindowState(
        rootSize = DpSize(
            width = maxWidth,
            height = maxHeight
        )
    )

    val darkTheme = isInDarkThemeMode(themeMode)
    val colorScheme = remember(darkTheme) {
        colorSchemeFromTokens(
            if (darkTheme) ColorSchemeTokens.Dark
            else ColorSchemeTokens.Light
        )
    }

    val shapeData = retain { ShapeData() }

    CompositionLocalProvider(
        LocalShapeData provides shapeData,
        LocalShapes provides Shapes(),
        LocalThemeMode provides themeMode,
        LocalColorScheme provides colorScheme,
        LocalBackgroundColor provides colorScheme.surface,
        LocalContentColor provides colorScheme.onSurface,
        LocalTypography provides typography,
        LocalTextStyle provides typography.bodySmall,
        LocalWindowState provides windowState,
        content = content
    )

}