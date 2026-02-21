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

package com.stoyanvuchev.squircleshape.demo.core.ui.component.siderail

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateIntAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
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
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import com.stoyanvuchev.squircleshape.demo.core.ui.Theme
import com.stoyanvuchev.squircleshape.demo.core.ui.component.layout.spacer.VerticalSpacer
import com.stoyanvuchev.squircleshape.demo.core.ui.component.siderail.SideRailUtils.sideRailBlurModifier

@Composable
fun SideRail(
    modifier: Modifier = Modifier,
    selectedItemIndex: () -> Int,
    itemsCount: () -> Int,
    insets: WindowInsets = SideRailUtils.windowInsets(),
    action: @Composable (ColumnScope.() -> Unit)? = null,
    items: @Composable ColumnScope.() -> Unit
) {

    Column(
        modifier = Modifier
            .fillMaxHeight()
            .windowInsetsPadding(insets)
            .padding(vertical = 40.dp)
            .padding(start = 20.dp)
            .then(modifier),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Box(
            modifier = Modifier.sideRailBlurModifier()
        ) {

            var maxHeight by remember { mutableIntStateOf(0) }
            var maxWidth by remember { mutableIntStateOf(0) }
            val targetItem by remember {
                derivedStateOf {
                    (maxHeight / itemsCount()) * selectedItemIndex()
                }
            }

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

            Column(
                modifier = Modifier
                    .height(56.dp * itemsCount())
                    .onSizeChanged {
                        maxHeight = it.height
                        maxWidth = it.width
                    },
                content = items
            )

        }

        action?.let {
            VerticalSpacer(height = 16.dp)
            action()
        }

    }

}