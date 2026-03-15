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

package com.stoyanvuchev.squircleshape.demo.core.ui.component.layout.scaffold

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.Placeable
import androidx.compose.ui.layout.SubcomposeLayout
import androidx.compose.ui.layout.SubcomposeMeasureScope
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.offset
import com.stoyanvuchev.squircleshape.demo.core.ui.LocalHazeState
import com.stoyanvuchev.squircleshape.demo.core.ui.Theme
import com.stoyanvuchev.squircleshape.demo.core.ui.color.LocalBackgroundColor
import com.stoyanvuchev.squircleshape.demo.core.ui.color.LocalContentColor
import com.stoyanvuchev.squircleshape.demo.core.ui.color.inverseColor
import com.stoyanvuchev.squircleshape.demo.core.ui.component.siderail.LocalIsSideRailPresent
import com.stoyanvuchev.squircleshape.demo.core.ui.component.siderail.SideRailUtils
import com.stoyanvuchev.squircleshape.demo.core.ui.component.topbar.LocalTopBarState
import com.stoyanvuchev.squircleshape.demo.core.ui.component.topbar.TopBarState
import com.stoyanvuchev.squircleshape.demo.core.ui.component.topbar.rememberTopBarState
import com.stoyanvuchev.squircleshape.demo.core.ui.util.window.LocalWindowState
import com.stoyanvuchev.squircleshape.demo.core.ui.util.window.WindowFormFactor
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
    bottomBar: @Composable (ColumnScope.() -> Unit)? = null,
    sideRail: @Composable (ColumnScope.() -> Unit)? = null,
    surfaceColor: Color = LocalBackgroundColor.current,
    contentColor: Color = Theme.colorScheme.inverseColor(surfaceColor),
    contentWindowInsets: WindowInsets = WindowInsets.safeDrawing,
    background: @Composable (BoxScope.(PaddingValues) -> Unit)? = null,
    content: @Composable () -> Unit
) = BoxWithConstraints(
    modifier = Modifier
        .fillMaxSize()
        .background(surfaceColor)
        .then(modifier),
) {

    CompositionLocalProvider(
        LocalContentColor provides contentColor,
        LocalTopBarState provides rememberTopBarState(),
        LocalHazeState provides rememberHazeState(),
        LocalIsSideRailPresent provides remember(sideRail) { sideRail != null }
    ) {

        val isCompactWidth = LocalWindowState.current.isCompactWidth

        ScaffoldLayoutImpl(
            topBar = topBar,
            bottomBar = if (isCompactWidth) bottomBar else remember { null },
            sideRail = if (!isCompactWidth) sideRail else remember { null },
            content = content,
            background = background,
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
private fun ScaffoldLayoutImpl(
    topBar: @Composable (() -> Unit)?,
    bottomBar: @Composable (ColumnScope.() -> Unit)?,
    sideRail: @Composable (ColumnScope.() -> Unit)?,
    content: @Composable () -> Unit,
    background: @Composable (BoxScope.(PaddingValues) -> Unit)?,
    contentWindowInsets: WindowInsets
) = SubcomposeLayout { constraints ->

    val layoutWidth = constraints.maxWidth
    val layoutHeight = constraints.maxHeight
    val looseConstraints = constraints.copy(minWidth = 0, minHeight = 0)
    val insets = contentWindowInsets.asPaddingValues(this@SubcomposeLayout)

    layout(layoutWidth, layoutHeight) {

        val sideRailPlaceables = subcompose(
            slotId = ScaffoldLayoutContent.SideRail,
            content = {

                sideRail?.let {

                    val windowState = LocalWindowState.current

                    val startPadding = remember(windowState) {
                        when (windowState.formFactor) {
                            WindowFormFactor.FoldableTabletPortrait -> 8.dp
                            WindowFormFactor.FoldableTabletLandscape -> 20.dp
                            WindowFormFactor.DesktopPortrait -> 20.dp
                            WindowFormFactor.DesktopLandscape -> layoutWidth.toDp() * .1f
                            else -> 0.dp
                        }
                    }

                    val endPadding = remember(windowState) {
                        when (windowState.formFactor) {
                            WindowFormFactor.FoldableTabletPortrait -> 20.dp
                            WindowFormFactor.FoldableTabletLandscape -> 40.dp
                            WindowFormFactor.DesktopPortrait -> 20.dp
                            WindowFormFactor.DesktopLandscape -> 64.dp
                            else -> 20.dp
                        }
                    }

                    Column(
                        modifier = Modifier
                            .fillMaxHeight()
                            .padding(start = startPadding, end = endPadding)
                            .windowInsetsPadding(SideRailUtils.windowInsets()),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.Start,
                        content = sideRail
                    )

                }

            }
        ).map { it.measure(looseConstraints) }

        val sideRailWidth = sideRailPlaceables.maxByOrNull { it.width }?.width
        val sideRailHeight = sideRailPlaceables.maxByOrNull { it.height }?.height

        val topBarPlaceables = subcompose(
            slotId = ScaffoldLayoutContent.TopBar,
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

        val bottomBarPlaceables = subcompose(
            slotId = ScaffoldLayoutContent.BottomBar,
            content = {
                bottomBar?.let {
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        content = bottomBar
                    )
                }
            }
        ).map { it.measure(looseConstraints) }

        val bottomBarHeight = bottomBarPlaceables.maxByOrNull { it.height }?.height ?: 0

        val backgroundPlaceables = subcompose(
            ScaffoldLayoutContent.Background
        ) {

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = Theme.colorScheme.surface)
            ) {

                background?.let {

                    val innerPadding by calculateInnerPadding(
                        scope = { this@SubcomposeLayout },
                        insets = insets,
                        bottomBarPlaceables = { bottomBarPlaceables },
                        sideRailPlaceables = { sideRailPlaceables },
                        bottomBarHeight = bottomBarHeight,
                        sideRailWidth = sideRailWidth
                    )

                    background(innerPadding)

                }

            }

        }.map { it.measure(looseConstraints) }

        val bodyContentPlaceables = subcompose(
            ScaffoldLayoutContent.MainContent
        ) {

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .hazeSource(
                        state = LocalHazeState.current,
                        key = CONTENT_HAZE_SOURCE_KEY
                    )
            ) {

                val innerPadding by calculateInnerPadding(
                    scope = { this@SubcomposeLayout },
                    insets = insets,
                    bottomBarPlaceables = { bottomBarPlaceables },
                    sideRailPlaceables = { sideRailPlaceables },
                    bottomBarHeight = bottomBarHeight,
                    sideRailWidth = sideRailWidth
                )

                CompositionLocalProvider(
                    LocalScaffoldLayoutPadding provides innerPadding,
                    content = { content() }
                )

            }

        }.map { it.measure(looseConstraints) }

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

        backgroundPlaceables.forEach { it.place(IntOffset.Zero) }
        bodyContentPlaceables.forEach { it.place(IntOffset.Zero) }
        topBarPlaceables.forEach { it.place(topBarOffset) }
        bottomBarPlaceables.forEach { it.place(bottomBarOffset) }
        sideRailPlaceables.forEach { it.place(sideRailOffset) }

    }

}

/**
 * Internal slot identifiers used by [SubcomposeLayout].
 */
private enum class ScaffoldLayoutContent {
    MainContent,
    Background,
    TopBar,
    BottomBar,
    SideRail;
}

private const val CONTENT_HAZE_SOURCE_KEY = "scaffold_content_source_key"

@Composable
private fun calculateInnerPadding(
    scope: () -> SubcomposeMeasureScope,
    insets: PaddingValues,
    bottomBarPlaceables: () -> List<Placeable>,
    sideRailPlaceables: () -> List<Placeable>,
    bottomBarHeight: Int,
    sideRailWidth: Int?
): State<PaddingValues> {

    val windowState = LocalWindowState.current
    val horizontalPadding = remember(windowState) {
        when {
            windowState.isMediumWidth -> 40.dp
            windowState.isLargeWidth -> 80.dp + (windowState.availableWidthDp * .1f)
            else -> 20.dp
        }
    }

    return rememberUpdatedState(
        with(scope()) {
            PaddingValues(
                top = 0.dp,
                bottom = if (bottomBarPlaceables().isEmpty()) insets.calculateBottomPadding()
                else bottomBarHeight.toDp(),
                start = if (sideRailPlaceables().isEmpty() || sideRailWidth == null)
                    insets.calculateStartPadding(layoutDirection) + horizontalPadding
                else sideRailWidth.toDp(),
                end = insets.calculateEndPadding(layoutDirection) + horizontalPadding
            )
        }
    )

}