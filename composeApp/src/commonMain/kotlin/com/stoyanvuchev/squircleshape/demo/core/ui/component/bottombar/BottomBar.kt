/*
 * Copyright 2026 Assertive UI (assertiveui.com)
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

package com.stoyanvuchev.squircleshape.demo.core.ui.component.bottombar

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateIntAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.unit.dp
import com.stoyanvuchev.squircleshape.demo.core.ui.Theme
import com.stoyanvuchev.squircleshape.demo.core.ui.component.bottombar.BottomBarUtils.bottomBarBlurModifier
import com.stoyanvuchev.squircleshape.demo.core.ui.component.layout.spacer.HorizontalSpacer

@Composable
fun BottomBar(
    modifier: Modifier = Modifier,
    selectedItemIndex: () -> Int,
    itemsCount: () -> Int,
    insets: WindowInsets = BottomBarUtils.windowInsets(),
    action: @Composable (RowScope.() -> Unit)? = null,
    items: @Composable RowScope.() -> Unit
) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(BottomBarUtils.containerHeight())
            .windowInsetsPadding(insets)
            .padding(horizontal = 40.dp)
            .padding(bottom = 20.dp)
            .then(modifier),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {

        Box(
            modifier = Modifier
                .fillMaxHeight()
                .weight(1f)
                .bottomBarBlurModifier()
        ) {

            var maxWidth by remember { mutableIntStateOf(0) }
            val targetItem by remember {
                derivedStateOf {
                    (maxWidth / itemsCount()) * selectedItemIndex()
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
                    .fillMaxWidth(1f / itemsCount())
                    .padding(4.dp)
                    .clip(shape = Theme.universalShape)
                    .background(color = Theme.colorScheme.primary.copy(alpha = .16f))
                    .border(
                        width = 1.dp,
                        brush = Brush.verticalGradient(
                            colors = listOf(
                                Theme.colorScheme.outline,
                                Theme.colorScheme.outline.copy(0f)
                            )
                        ),
                        shape = Theme.universalShape
                    )
            )

            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .onSizeChanged { maxWidth = it.width },
                content = items
            )

        }

        action?.let {
            HorizontalSpacer(width = 16.dp)
            action()
        }

    }

}