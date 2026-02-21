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
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.lerp
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.stoyanvuchev.squircleshape.demo.core.ui.Theme
import com.stoyanvuchev.squircleshape.demo.core.ui.component.layout.spacer.HorizontalSpacer
import com.stoyanvuchev.squircleshape.demo.core.ui.component.text.Text

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
 * @param state The shared [TopBarState] driving height and typography interpolation.
 * @param insets Safe drawing insets applied to the content area.
 * @param modifier Optional modifier for additional styling or layout behavior.
 */
@Composable
fun TopBar(
    title: String,
    primaryActions: (@Composable RowScope.() -> Unit)? = null,
    secondaryActions: (@Composable RowScope.() -> Unit)? = null,
    state: TopBarState = LocalTopBarState.current,
    insets: WindowInsets = TopBarUtils.windowInsets(),
    modifier: Modifier = Modifier
) {

    val density = LocalDensity.current
    val minHeight = TopBarUtils.smallContainerHeight()
    val maxHeight = TopBarUtils.largeContainerHeight()
    val maxHeightPx = with(density) { maxHeight.toPx() }

    /**
     * Current dynamic height of the top bar.
     *
     * heightOffset increases as the list scrolls,
     * reducing the visible height of the top bar.
     */
    val currentHeight by remember {
        derivedStateOf {
            with(density) {
                (maxHeightPx - state.heightOffset).toDp()
            }
        }
    }

    /**
     * Title typography interpolation.
     *
     * Smoothly transitions between:
     * - Large title when expanded
     * - Small title when collapsed
     */
    val largeTitle = Theme.typography.titleLarge
    val smallTitle = Theme.typography.titleSmall
    val interpolatedStyle by remember {
        derivedStateOf {
            lerp(
                largeTitle,
                smallTitle,
                state.collapsedFraction
            )
        }
    }

    // Root container:
    // Height is dynamically controlled by collapse progress.
    // Content is aligned to the bottom to mimic a collapsing header.
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(currentHeight)
            .then(modifier),
        contentAlignment = Alignment.BottomStart
    ) {

        // Horizontal content row containing:
        //  - Primary actions
        //  - Title
        //  - Secondary actions
        // Height is fixed to the pinned (collapsed) height.
        Row(
            modifier = Modifier
                .windowInsetsPadding(insets)
                .height(minHeight),
            verticalAlignment = Alignment.CenterVertically
        ) {

            // Primary actions (start side).
            primaryActions?.let {
                HorizontalSpacer(width = 16.dp)
                primaryActions()
            } ?: HorizontalSpacer(width = 4.dp)

            // Displays the Title text.
            // Uses weight(1f) to occupy remaining horizontal space.
            // Ellipsis prevents layout jumps during collapse.
            Text(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .weight(1f),
                text = title,
                style = interpolatedStyle,
                overflow = TextOverflow.Ellipsis
            )

            // Secondary actions (end side).
            secondaryActions?.let {
                secondaryActions()
                HorizontalSpacer(width = 16.dp)
            } ?: HorizontalSpacer(width = 4.dp)

        }

    }

}