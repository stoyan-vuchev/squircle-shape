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
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.SubcomposeLayout
import androidx.compose.ui.unit.IntOffset
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
import com.stoyanvuchev.squircleshape.demo.core.ui.util.window.WindowState
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.hazeSource
import dev.chrisbanes.haze.rememberHazeState

/**
 * A responsive nested scaffold layout used for displaying nested content inside a [ScaffoldLayout].
 *
 * Responsibilities:
 * - Hosts top bar and main content
 * - Handles responsive layout behavior (compact vs expanded width)
 * - Provides shared [TopBarState] and [HazeState] through CompositionLocal
 * - Applies fading edge effects for a top bar
 *
 */
@Composable
fun NestedScaffoldLayout(
    modifier: Modifier = Modifier,
    topBar: @Composable (() -> Unit)? = null,
    surfaceColor: Color = LocalBackgroundColor.current,
    contentColor: Color = Theme.colorScheme.inverseColor(surfaceColor),
    background: @Composable (BoxScope.(PaddingValues) -> Unit)? = null,
    content: @Composable (PaddingValues) -> Unit
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
        LocalIsNestedScaffold provides true
    ) {

        NestedScaffoldLayoutImpl(
            topBar = topBar,
            content = content,
            background = background
        )

    }

}

@Composable
private fun NestedScaffoldLayoutImpl(
    topBar: @Composable (() -> Unit)?,
    content: @Composable (PaddingValues) -> Unit,
    background: @Composable (BoxScope.(PaddingValues) -> Unit)?,
    contentPadding: PaddingValues = LocalScaffoldLayoutPadding.current,
    windowState: WindowState = LocalWindowState.current
) = SubcomposeLayout { constraints ->

    val layoutWidth = constraints.maxWidth
    val layoutHeight = constraints.maxHeight
    val looseConstraints = constraints.copy(minWidth = 0, minHeight = 0)

    val horizontalPadding = when {
        windowState.isMediumWidth -> 40.dp
        windowState.isLargeWidth -> 80.dp
        else -> 20.dp
    }.roundToPx()

    layout(layoutWidth, layoutHeight) {

        val topBarPlaceables = subcompose(
            slotId = NestedScaffoldLayoutImplContent.TopBar,
            content = topBar ?: {}
        ).map {

            val leftInset = (contentPadding
                .calculateLeftPadding(layoutDirection)
                .roundToPx() - horizontalPadding).coerceAtLeast(0)

            val rightInset = (contentPadding
                .calculateRightPadding(layoutDirection)
                .roundToPx() - horizontalPadding).coerceAtLeast(0)

            it.measure(
                constraints = looseConstraints.offset(
                    horizontal = (-leftInset) - rightInset,
                )
            )

        }

        val backgroundPlaceables = subcompose(
            slotId = NestedScaffoldLayoutImplContent.Background
        ) {

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = Theme.colorScheme.surface)
                    .hazeSource(
                        state = LocalHazeState.current,
                        key = BACKGROUND_HAZE_SOURCE_KEY
                    )
            ) {

                background?.let {
                    background(LocalScaffoldLayoutPadding.current)
                }

            }

        }.map { it.measure(looseConstraints) }

        val bodyContentPlaceables = subcompose(
            slotId = NestedScaffoldLayoutImplContent.MainContent
        ) {

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .hazeSource(
                        state = LocalHazeState.current,
                        key = CONTENT_HAZE_SOURCE_KEY
                    )
            ) {

                val padding = LocalScaffoldLayoutPadding.current
                val innerPadding by rememberUpdatedState(
                    PaddingValues(
                        top = if (topBarPlaceables.isEmpty()) padding.calculateTopPadding() else 0.dp,
                        bottom = padding.calculateBottomPadding(),
                        start = padding.calculateStartPadding(layoutDirection),
                        end = padding.calculateEndPadding(layoutDirection)
                    )
                )

                content(innerPadding)

            }

        }.map { it.measure(looseConstraints) }

        val topBarOffset = IntOffset(
            x = (contentPadding
                .calculateLeftPadding(layoutDirection)
                .roundToPx() - horizontalPadding).coerceAtLeast(0),
            y = 0
        )

        backgroundPlaceables.forEach { it.place(IntOffset.Zero) }
        bodyContentPlaceables.forEach { it.place(IntOffset.Zero) }
        topBarPlaceables.forEach { it.place(topBarOffset) }

    }

}

/**
 * Internal slot identifiers used by [SubcomposeLayout].
 */
private enum class NestedScaffoldLayoutImplContent {
    MainContent,
    Background,
    TopBar
}

private const val BACKGROUND_HAZE_SOURCE_KEY = "nested_scaffold_background_source_key"
private const val CONTENT_HAZE_SOURCE_KEY = "nested_scaffold_content_source_key"