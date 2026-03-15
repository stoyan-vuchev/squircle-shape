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

package com.stoyanvuchev.squircleshape.demo.presentation.defaults

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBars
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.BlurEffect
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import com.stoyanvuchev.squircleshape.demo.core.ui.Theme
import com.stoyanvuchev.squircleshape.demo.core.ui.component.topbar.LocalTopBarState
import com.stoyanvuchev.squircleshape.demo.core.ui.isInDarkThemeMode
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource

@Composable
fun DefaultBackgroundDecoration(
    safePadding: PaddingValues,
    icon: DrawableResource
) {

    val topBarState = LocalTopBarState.current
    val density = LocalDensity.current

    val alpha by remember {
        derivedStateOf { 1f - topBarState.collapsedFraction }
    }

    val blur by remember {
        derivedStateOf {

            val blurRadius = with(density) {
                16.dp.toPx() * topBarState.collapsedFraction
            }

            BlurEffect(
                radiusX = blurRadius,
                radiusY = blurRadius
            )

        }
    }

    BoxWithConstraints(
        modifier = Modifier
            .graphicsLayer {
                this.alpha = alpha
                this.renderEffect = blur
            }
            .fillMaxSize()
    ) {

        val brushTint by rememberUpdatedState(
            if (!isInDarkThemeMode()) Theme.colorScheme.primary.copy(.67f)
            else Theme.colorScheme.primary.copy(.25f)
        )

        val iconTint by rememberUpdatedState(
            if (!isInDarkThemeMode()) Theme.colorScheme.onPrimary
            else Theme.colorScheme.primary
        )

        val insets = WindowInsets.systemBars

        Box(
            modifier = Modifier
                .fillMaxSize()
                .drawBehind {
                    drawRect(
                        brush = Brush.radialGradient(
                            colors = listOf(
                                brushTint,
                                brushTint.copy(.0f),
                            ),
                            center = Offset(
                                x = size.width - 52.dp.toPx() - safePadding
                                    .calculateRightPadding(layoutDirection).toPx(),
                                y = 72.dp.toPx() + insets.getTop(density)
                            ),
                            radius = maxOf(size.width, size.height) / 3
                        )
                    )
                }
                .padding(safePadding)
                .padding(top = 20.dp),
            contentAlignment = Alignment.TopEnd
        ) {

            val scale by remember {
                derivedStateOf { 1f - topBarState.collapsedFraction }
            }

            val translationY by remember {
                derivedStateOf { insets.getTop(density).toFloat() }
            }

            Image(
                modifier = Modifier
                    .graphicsLayer {
                        this.translationY = translationY
                        this.scaleX = scale
                        this.scaleY = scale
                    }
                    .size(100.dp)
                    .padding(8.dp),
                painter = painterResource(icon),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                colorFilter = ColorFilter.tint(iconTint)
            )

        }

    }

}