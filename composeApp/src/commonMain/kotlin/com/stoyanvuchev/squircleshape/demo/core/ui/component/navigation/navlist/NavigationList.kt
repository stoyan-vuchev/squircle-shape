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

package com.stoyanvuchev.squircleshape.demo.core.ui.component.navigation.navlist

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateIntAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
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
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import com.stoyanvuchev.squircleshape.demo.core.ui.Theme
import com.stoyanvuchev.squircleshape.demo.core.ui.floatingComponentHazeEffect

@Composable
fun NavigationList(
    modifier: Modifier = Modifier,
    selectedItemIndex: Int,
    itemsCount: Int,
    items: @Composable NavigationListScope.() -> Unit
) {

    Box(
        modifier = Modifier
            .then(modifier)
            .floatingComponentHazeEffect(Theme.shapes.medium)
    ) {

        var maxHeight by remember { mutableIntStateOf(0) }
        var maxWidth by remember { mutableIntStateOf(0) }
        val targetItem by remember(itemsCount, selectedItemIndex) {
            derivedStateOf {
                (maxHeight / itemsCount) * selectedItemIndex
            }
        }

        val layoutHeight = remember(itemsCount) { 56.dp * itemsCount }
        val offsetY by animateIntAsState(
            targetValue = targetItem,
            animationSpec = spring(
                dampingRatio = Spring.DampingRatioLowBouncy,
                stiffness = Spring.StiffnessMediumLow
            )
        )

        Box(
            modifier = Modifier
                .graphicsLayer { this.translationY = offsetY.toFloat() }
                .height(56.dp)
                .width(with(LocalDensity.current) { maxWidth.toDp() })
                .padding(4.dp)
                .clip(shape = Theme.shapes.small)
                .background(color = Theme.colorScheme.accent.copy(alpha = .16f))
                .innerShadow(
                    shape = Theme.shapes.small,
                    shadow = Shadow(
                        radius = 16.dp,
                        brush = Brush.verticalGradient(
                            colors = listOf(
                                Theme.colorScheme.accent.copy(.0f),
                                Theme.colorScheme.accent.copy(.1f)
                            )
                        ),
                        offset = DpOffset(x = 0.dp, y = (-2).dp)
                    )
                )
                .border(
                    width = 1.dp,
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            Theme.colorScheme.accent.copy(.1f),
                            Theme.colorScheme.accent.copy(.0f)
                        )
                    ),
                    shape = Theme.shapes.small
                )
        )

        Column(
            modifier = Modifier
                .height(layoutHeight)
                .onSizeChanged {
                    maxHeight = it.height
                    maxWidth = it.width
                },
            horizontalAlignment = Alignment.Start,
            content = items
        )

    }

}

typealias NavigationListScope = ColumnScope