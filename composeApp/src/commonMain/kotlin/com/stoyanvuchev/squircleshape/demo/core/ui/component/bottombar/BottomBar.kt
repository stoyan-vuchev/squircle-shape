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

package com.stoyanvuchev.squircleshape.demo.core.ui.component.bottombar

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateIntAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.innerShadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.shadow.Shadow
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import com.stoyanvuchev.squircleshape.demo.core.ui.LocalHazeState
import com.stoyanvuchev.squircleshape.demo.core.ui.Theme
import com.stoyanvuchev.squircleshape.demo.core.ui.floatingComponentHazeEffect
import com.stoyanvuchev.squircleshape.demo.core.util.Platform
import com.stoyanvuchev.squircleshape.demo.core.util.getPlatform
import dev.chrisbanes.haze.HazeProgressive
import dev.chrisbanes.haze.HazeStyle
import dev.chrisbanes.haze.HazeTint
import dev.chrisbanes.haze.hazeEffect

@Composable
fun BottomBar(
    modifier: Modifier = Modifier,
    selectedItemIndex: Int,
    itemsCount: Int,
    insets: WindowInsets = BottomBarUtils.windowInsets(),
    action: @Composable (RowScope.() -> Unit)? = null,
    navList: @Composable (ColumnScope.() -> Unit)? = null,
    items: @Composable RowScope.() -> Unit
) {

    val bottomPadding = remember {
        if (getPlatform() == Platform.IOS) 0.dp else 20.dp
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .hazeEffect(
                state = LocalHazeState.current,
                style = HazeStyle(
                    backgroundColor = Theme.colorScheme.surface,
                    tint = HazeTint(
                        color = Theme.colorScheme.surface.copy(.8f)
                    ),
                    blurRadius = 12.dp,
                    noiseFactor = 0f
                )
            ) {
                progressive = HazeProgressive.verticalGradient(
                    startIntensity = 0f,
                    endIntensity = 1f
                )
            }
            .padding(horizontal = 40.dp)
            .padding(
                top = 64.dp,
                bottom = bottomPadding
            )
            .windowInsetsPadding(insets)
            .then(modifier)
    ) {

        navList?.let { navList() }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(BottomBarUtils.containerHeight),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {

            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(1f)
                    .floatingComponentHazeEffect(Theme.shapes.medium)
            ) {

                var maxWidth by remember { mutableIntStateOf(0) }
                val targetItem by remember(itemsCount, selectedItemIndex) {
                    derivedStateOf {
                        (maxWidth / itemsCount) * selectedItemIndex
                    }
                }

                val offsetX by animateIntAsState(
                    targetValue = targetItem,
                    animationSpec = spring(
                        dampingRatio = Spring.DampingRatioLowBouncy,
                        stiffness = Spring.StiffnessMediumLow
                    )
                )

                Box(
                    modifier = Modifier
                        .graphicsLayer { this.translationX = offsetX.toFloat() }
                        .fillMaxHeight()
                        .fillMaxWidth(1f / itemsCount)
                        .padding(4.dp)
                        .clip(shape = Theme.shapes.small)
                        .background(color = Theme.colorScheme.primary.copy(alpha = .16f))
                        .innerShadow(
                            shape = Theme.shapes.small,
                            shadow = Shadow(
                                radius = 16.dp,
                                brush = Brush.verticalGradient(
                                    colors = listOf(
                                        Theme.colorScheme.primary.copy(.0f),
                                        Theme.colorScheme.primary.copy(.1f)
                                    )
                                ),
                                offset = DpOffset(x = 0.dp, y = (-2).dp)
                            )
                        )
                        .border(
                            width = 1.dp,
                            brush = Brush.verticalGradient(
                                colors = listOf(
                                    Theme.colorScheme.primary.copy(.1f),
                                    Theme.colorScheme.accent.copy(.0f)
                                )
                            ),
                            shape = Theme.shapes.small
                        )
                )

                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .onSizeChanged { maxWidth = it.width },
                    content = items
                )

            }

            action?.let { action() }

        }

    }

}