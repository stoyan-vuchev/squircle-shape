/*
 * Copyright 2025 Assertive UI (assertiveui.com)
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

package com.stoyanvuchev.squircleshape.demo.core.ui.component.topbar

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.lerp
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.stoyanvuchev.squircleshape.demo.core.ui.Theme
import com.stoyanvuchev.squircleshape.demo.core.ui.component.text.Text
import com.stoyanvuchev.squircleshape.demo.core.util.transformFraction

@Composable
fun CollapsibleTopBar(
    modifier: Modifier = Modifier,
    title: String,
    scrollBehavior: TopBarScrollBehavior,
    maxHeight: Dp = TopBarUtils.largeContainerHeight(),
    pinnedHeight: Dp = TopBarUtils.smallContainerHeight,
    primaryActions: (@Composable RowScope.() -> Unit)? = null,
    secondaryActions: (@Composable RowScope.() -> Unit)? = null,
    insets: WindowInsets = TopBarUtils.windowInsets()
) {

    val density = LocalDensity.current
    val state = scrollBehavior.state

    val maxHeightPx by remember { derivedStateOf { with(density) { maxHeight.toPx() } } }
    val pinnedHeightPx by remember {
        derivedStateOf {
            with(density) {
                pinnedHeight.toPx() + insets.getTop(this)
            }
        }
    }

    LaunchedEffect(maxHeightPx, pinnedHeightPx) {
        state.heightOffsetLimit = pinnedHeightPx - maxHeightPx
    }

    val height by remember {
        derivedStateOf {
            with(density) {
                transformFraction(
                    value = 1f - state.collapsedFraction,
                    startX = 1f,
                    endX = 0f,
                    startY = pinnedHeightPx,
                    endY = maxHeightPx
                ).toDp()
            }
        }
    }

    Box(
        modifier = modifier
            .height(height)
            .fillMaxWidth()
            .clipToBounds()
    ) {

        val color = Theme.colorScheme.surfaceElevationHigh
        val rowBG by remember {
            derivedStateOf {
                color.copy(
                    alpha = 1f - transformFraction(
                        value = .67f - state.collapsedFraction.coerceAtMost(.67f),
                        startX = .67f,
                        endX = 0f,
                        startY = 1f,
                        endY = 0f
                    )
                )
            }
        }

        Row(
            modifier = Modifier
                .windowInsetsPadding(insets)
                .height(pinnedHeight)
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .padding(horizontal = 20.dp, vertical = 16.dp)
                .clip(Theme.rangedShape)
                .background(rowBG),
            verticalAlignment = Alignment.CenterVertically
        ) {

            if (primaryActions != null) {
                primaryActions()
                Spacer(modifier = Modifier.width(16.dp))
            } else {
                Spacer(modifier = Modifier.width(20.dp))
            }

            CollapsibleTitle(
                title = title,
                progress = { state.collapsedFraction }
            )

            Spacer(Modifier.weight(1f))

            if (secondaryActions != null) {
                Spacer(modifier = Modifier.width(16.dp))
                secondaryActions()
            }

        }

    }
}

@Composable
private fun CollapsibleTitle(
    title: String,
    progress: () -> Float
) {

    val largeStyle = Theme.typography.titleLarge
    val smallStyle = Theme.typography.titleSmall
    val textStyle by remember {
        derivedStateOf {
            lerp(
                start = largeStyle,
                stop = smallStyle,
                fraction = progress()
            )
        }
    }

    Text(
        text = title,
        style = textStyle
    )

}