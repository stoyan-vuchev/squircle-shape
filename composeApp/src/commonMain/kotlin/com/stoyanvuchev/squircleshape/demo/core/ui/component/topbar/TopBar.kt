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

package com.stoyanvuchev.squircleshape.demo.core.ui.component.topbar

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.stoyanvuchev.squircleshape.demo.core.ui.LocalHazeState
import com.stoyanvuchev.squircleshape.demo.core.ui.Theme
import com.stoyanvuchev.squircleshape.demo.core.ui.component.layout.spacer.HorizontalSpacer
import com.stoyanvuchev.squircleshape.demo.core.ui.component.text.Text
import com.stoyanvuchev.squircleshape.demo.core.util.transformFraction
import dev.chrisbanes.haze.HazeProgressive
import dev.chrisbanes.haze.HazeStyle
import dev.chrisbanes.haze.HazeTint
import dev.chrisbanes.haze.hazeEffect

/**
 * A collapsible Top Bar that reacts to scroll-driven [TopBarState].
 *
 * This composable:
 * - Expands and collapses vertically based on [TopBarState.heightOffset]
 * - Interpolates the title text style between large and small typography
 * - Supports optional primary and secondary action slots
 * - Respects safe drawing insets
 *
 * The actual collapse logic is handled externally (see topBarStickyHeader).
 * This composable is purely visual and state-driven.
 *
 * @param title The title text displayed in the top bar.
 * @param primaryActions Optional composable content aligned to the start (navigation/actions).
 * @param secondaryActions Optional composable content aligned to the end (actions/menu).
 * @param insets Safe drawing insets applied to the content area.
 */
@Composable
fun TopBar(
    title: String,
    primaryActions: (@Composable RowScope.() -> Unit)? = null,
    secondaryActions: (@Composable RowScope.() -> Unit)? = null,
    insets: WindowInsets = TopBarUtils.windowInsets()
) {

    val state = LocalTopBarState.current
    val collapsedFraction by remember {
        derivedStateOf { state.collapsedFraction }
    }

    val translationY by remember {
        derivedStateOf { state.heightOffsetLimit - state.heightOffset }
    }

    val startIntensity by remember {
        derivedStateOf { if (collapsedFraction == 1f) 1f else 0f }
    }

    Box(
        modifier = Modifier
            .graphicsLayer {
                this.translationY = translationY
            }
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
                    startIntensity = startIntensity,
                    endIntensity = 0f
                )
            },
        contentAlignment = Alignment.BottomStart
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(TopBarUtils.smallContainerHeight())
                .windowInsetsPadding(insets),
            verticalAlignment = Alignment.CenterVertically
        ) {

            primaryActions?.let {
                HorizontalSpacer(width = 24.dp)
                primaryActions()
            } ?: HorizontalSpacer(width = 4.dp)

            val largeTitleAlpha by remember {
                derivedStateOf {
                    transformFraction(
                        value = 1f - collapsedFraction,
                        startX = 1f,
                        endX = .5f,
                        startY = 1f,
                        endY = 0f
                    )
                }
            }

            Text(
                modifier = Modifier
                    .graphicsLayer { this.alpha = largeTitleAlpha }
                    .padding(horizontal = 24.dp)
                    .weight(1f),
                text = title,
                style = Theme.typography.titleLarge.copy(
                    shadow = Shadow(
                        color = Theme.colorScheme.surface,
                        blurRadius = 1f
                    )
                ),
                color = Theme.colorScheme.onSurfaceElevationHigh,
                overflow = TextOverflow.Ellipsis
            )

            secondaryActions?.let {
                secondaryActions()
                HorizontalSpacer(width = 24.dp)
            } ?: HorizontalSpacer(width = 4.dp)

        }

        val smallTitleAlpha by remember {
            derivedStateOf {
                transformFraction(
                    value = collapsedFraction,
                    startX = .5f,
                    endX = 1f,
                    startY = 0f,
                    endY = 1f
                )
            }
        }

        Text(
            modifier = Modifier
                .graphicsLayer { this.alpha = smallTitleAlpha }
                .padding(horizontal = 24.dp)
                .windowInsetsPadding(insets)
                .align(Alignment.Center),
            text = title,
            style = Theme.typography.titleSmall.copy(
                shadow = Shadow(
                    color = Theme.colorScheme.surface,
                    blurRadius = 1f
                )
            ),
            color = Theme.colorScheme.onSurfaceElevationHigh,
            overflow = TextOverflow.Ellipsis
        )

    }

}