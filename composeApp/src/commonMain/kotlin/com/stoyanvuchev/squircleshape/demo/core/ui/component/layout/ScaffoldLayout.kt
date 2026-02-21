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

package com.stoyanvuchev.squircleshape.demo.core.ui.component.layout

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.SubcomposeLayout
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.offset
import com.stoyanvuchev.squircleshape.demo.core.ui.LocalHazeState
import com.stoyanvuchev.squircleshape.demo.core.ui.Theme
import com.stoyanvuchev.squircleshape.demo.core.ui.color.LocalBackgroundColor
import com.stoyanvuchev.squircleshape.demo.core.ui.color.LocalContentColor
import com.stoyanvuchev.squircleshape.demo.core.ui.color.inverseColor
import com.stoyanvuchev.squircleshape.demo.core.ui.component.topbar.LocalTopBarState
import com.stoyanvuchev.squircleshape.demo.core.ui.component.topbar.TopBarState
import com.stoyanvuchev.squircleshape.demo.core.ui.component.topbar.rememberTopBarState
import com.stoyanvuchev.squircleshape.demo.core.ui.util.window.LocalWindowState
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.hazeSource
import dev.chrisbanes.haze.rememberHazeState


/**
 * A responsive scaffold layout used as the structural foundation of the app UI.
 *
 * Responsibilities:
 * - Hosts top bar, bottom bar, side rail, and main content
 * - Handles responsive layout behavior (compact vs expanded width)
 * - Provides shared [TopBarState] and [HazeState] through CompositionLocal
 * - Applies fading edge effects for top and bottom bars
 *
 * This is not a simple wrapper — it controls layout order and layering.
 */
@Composable
fun ScaffoldLayout(
    modifier: Modifier = Modifier,
    topBar: @Composable (() -> Unit)? = null,
    bottomBar: @Composable (() -> Unit)? = null,
    sideRail: @Composable () -> Unit = {},
    surfaceColor: Color = LocalBackgroundColor.current,
    contentColor: Color = Theme.colorScheme.inverseColor(surfaceColor),
    contentWindowInsets: WindowInsets = WindowInsets.safeDrawing,
    content: @Composable (PaddingValues) -> Unit
) = BoxWithConstraints(
    modifier = Modifier
        .background(surfaceColor)
        .then(modifier),
) {

    /**
     * Provide:
     * - Content color
     * - Shared TopBarState
     * - Shared HazeState
     *
     * TopBarState is scoped to the entire scaffold.
     */
    CompositionLocalProvider(
        LocalContentColor provides contentColor,
        LocalTopBarState provides rememberTopBarState(),
        LocalHazeState provides rememberHazeState()
    ) {

        val isCompactWidth = LocalWindowState.current.isCompactWidth

        /**
         * Responsive behavior:
         * - Compact → bottom bar visible
         * - Expanded → side rail visible
         */
        FoundationLayoutImpl(
            topBar = topBar,
            bottomBar = if (isCompactWidth) bottomBar else remember { {} },
            sideRail = if (!isCompactWidth) sideRail else remember { {} },
            content = content,
            contentWindowInsets = contentWindowInsets
        )

    }

}

/**
 * Core layout engine of [ScaffoldLayout].
 *
 * Uses [SubcomposeLayout] to:
 * 1. Measure top bar
 * 2. Measure bottom bar
 * 3. Measure side rail
 * 4. Measure main content with calculated padding
 *
 * Subcomposition is required because content padding
 * depends on the measured sizes of other slots.
 */
@Composable
private fun FoundationLayoutImpl(
    topBar: @Composable (() -> Unit)?,
    bottomBar: @Composable (() -> Unit)?,
    sideRail: @Composable () -> Unit,
    content: @Composable (PaddingValues) -> Unit,
    contentWindowInsets: WindowInsets
) = SubcomposeLayout { constraints ->

    val layoutWidth = constraints.maxWidth
    val layoutHeight = constraints.maxHeight
    val looseConstraints = constraints.copy(minWidth = 0, minHeight = 0)

    layout(layoutWidth, layoutHeight) {

        /**
         * 1). Measure Side Rail
         *
         * Side rail affects horizontal insets and content positioning.
         */
        val sideRailPlaceables = subcompose(
            slotId = ResponsiveScaffoldLayoutContent.SideRail,
            content = sideRail
        ).map { it.measure(looseConstraints) }

        val sideRailWidth = sideRailPlaceables.maxByOrNull { it.width }?.width
        val sideRailHeight = sideRailPlaceables.maxByOrNull { it.height }?.height

        /**
         * 2). Measure Top Bar
         *
         * If a side rail exists, horizontal constraints are adjusted
         * so the top bar does not overlap it.
         */
        val topBarPlaceables = subcompose(
            slotId = ResponsiveScaffoldLayoutContent.TopBar,
            content = topBar ?: {}
        ).map {

            val leftInset = if (
                layoutDirection == LayoutDirection.Ltr && sideRailWidth != null
            ) sideRailWidth else 0

            val rightInset = if (
                layoutDirection == LayoutDirection.Rtl && sideRailWidth != null
            ) sideRailWidth else 0

            it.measure(
                constraints = looseConstraints.offset(
                    horizontal = (-leftInset) - rightInset,
                )
            )
        }

        val topBarHeight = topBarPlaceables.maxByOrNull { it.height }?.height ?: 0

        /**
         * 3). Measure Bottom Bar
         */
        val bottomBarPlaceables = subcompose(
            slotId = ResponsiveScaffoldLayoutContent.BottomBar,
            content = bottomBar ?: {}
        ).map { it.measure(looseConstraints) }

        val bottomBarHeight = bottomBarPlaceables.maxByOrNull { it.height }?.height ?: 0

        /**
         * 4). Measure Main Content
         *
         * Padding depends on:
         * - Top bar height
         * - Bottom bar height
         * - Side rail width
         * - System window insets
         */
        val bodyContentPlaceables = subcompose(
            ResponsiveScaffoldLayoutContent.MainContent
        ) {

            val insets = contentWindowInsets.asPaddingValues(this@SubcomposeLayout)

            val innerPadding = PaddingValues(
                top = if (topBarPlaceables.isEmpty())
                    insets.calculateTopPadding()
                else 0.dp,
                bottom = if (bottomBarPlaceables.isEmpty())
                    insets.calculateBottomPadding()
                else bottomBarHeight.toDp(),
                start = if (sideRailPlaceables.isEmpty() || sideRailWidth == null)
                    insets.calculateStartPadding(layoutDirection)
                else sideRailWidth.toDp(),
                end = insets.calculateEndPadding(layoutDirection)
            )

            /**
             * Main content container.
             *
             * Applies fading edge masks for:
             * - Top bar
             * - Bottom bar
             *
             * BlendMode.DstIn is used to mask content smoothly.
             */
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = Theme.colorScheme.surface)
            ) {

                Box(
                    modifier = Modifier
                        .graphicsLayer { alpha = .99f }
                        .fillMaxSize()
                        .drawWithContent {

                            drawContent()

                            // Top fade mask
                            if (topBarHeight > 0) {
                                drawRect(
                                    brush = Brush.verticalGradient(
                                        colors = listOf(
                                            Color.Black.copy(alpha = .05f),
                                            Color.Black.copy(alpha = .1f),
                                            Color.Black
                                        ),
                                        startY = 0f,
                                        endY = topBarHeight + 32.dp.toPx()
                                    ),
                                    blendMode = BlendMode.DstIn
                                )
                            }

                            // Bottom fade mask
                            if (bottomBarHeight > 0) {
                                drawRect(
                                    brush = Brush.verticalGradient(
                                        colors = listOf(
                                            Color.Black,
                                            Color.Black.copy(alpha = .1f),
                                            Color.Black.copy(alpha = .05f)
                                        ),
                                        startY = layoutHeight -
                                                (bottomBarHeight + 32.dp.toPx()),
                                        endY = layoutHeight.toFloat()
                                    ),
                                    blendMode = BlendMode.DstIn
                                )
                            }

                        }
                ) {

                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .hazeSource(
                                state = LocalHazeState.current,
                                key = SCAFFOLD_LAYOUT_HAZE_SOURCE_KEY,
                                zIndex = .1f
                            )
                    ) {
                        content(innerPadding)
                    }

                }

            }

        }.map { it.measure(looseConstraints) }

        /**
         * Positioning offsets
         */

        val topBarOffset = IntOffset(
            x = if (layoutDirection == LayoutDirection.Ltr) sideRailWidth ?: 0 else 0,
            y = 0
        )

        val bottomBarOffset = IntOffset(
            x = 0,
            y = layoutHeight - bottomBarHeight
        )

        val sideRailOffset = IntOffset(
            x = if (layoutDirection == LayoutDirection.Ltr) 0
            else layoutWidth - (sideRailWidth ?: 0),
            y = (layoutHeight / 2) - ((sideRailHeight?.div(2)) ?: 0)
        )

        /**
         * Placement order defines visual stacking:
         *
         * MainContent → TopBar → BottomBar → SideRail
         */
        bodyContentPlaceables.forEach { it.place(IntOffset.Zero) }
        topBarPlaceables.forEach { it.place(topBarOffset) }
        bottomBarPlaceables.forEach { it.place(bottomBarOffset) }
        sideRailPlaceables.forEach { it.place(sideRailOffset) }
    }
}

/**
 * Internal slot identifiers used by [SubcomposeLayout].
 */
private enum class ResponsiveScaffoldLayoutContent {
    MainContent,
    TopBar,
    BottomBar,
    SideRail;
}

private const val SCAFFOLD_LAYOUT_HAZE_SOURCE_KEY = "scaffold_layout_content_source"