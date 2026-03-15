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

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.union
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import com.stoyanvuchev.squircleshape.demo.core.ui.component.layout.scaffold.LocalIsNestedScaffold
import com.stoyanvuchev.squircleshape.demo.core.ui.component.siderail.LocalIsSideRailPresent
import com.stoyanvuchev.squircleshape.demo.core.ui.component.siderail.SideRail
import com.stoyanvuchev.squircleshape.demo.core.ui.util.window.LocalWindowState
import com.stoyanvuchev.squircleshape.demo.core.ui.util.window.ScreenOrientation

/**
 * Useful utilities for a top bar UI components.
 */
object TopBarUtils {

    /**
     * Returns a [WindowInsets] instance for the top and horizontal safe drawing areas,
     * with optional control over the start side inset.
     *
     * This is especially useful when building layouts where a [SideRail] is present —
     * allowing the top bar to ignore start padding and remain properly aligned without extra spacing.
     *
     * @return A [WindowInsets] representing the requested sides of the safe drawing area.
     */
    @Composable
    fun windowInsets(): WindowInsets {

        val ignoreStart by rememberUpdatedState(
            !LocalIsNestedScaffold.current
                    && LocalIsSideRailPresent.current
        )

        val ignoreHorizontal by rememberUpdatedState(
            LocalIsNestedScaffold.current
        )

        val windowState = LocalWindowState.current
        val density = LocalDensity.current
        val layoutDirection = LocalLayoutDirection.current
        val start by rememberUpdatedState(
            with(density) {
                when {
                    windowState.isLargeWidth -> 12.dp
                    else -> 0.dp
                }.roundToPx()
            }
        )

        val horizontal by rememberUpdatedState(
            with(density) {
                when {
                    windowState.isMediumWidth -> 20.dp
                    windowState.isLargeWidth -> 64.dp
                    else -> 0.dp
                }.roundToPx()
            }
        )

        return rememberUpdatedState(
            WindowInsets.safeDrawing
                .only(
                    sides = if (!ignoreHorizontal) {
                        WindowInsetsSides.Top + if (!ignoreStart) {
                            WindowInsetsSides.Horizontal
                        } else WindowInsetsSides.End
                    } else WindowInsetsSides.Top
                ).union(
                    if (ignoreStart) WindowInsets(
                        left = if (layoutDirection == LayoutDirection.Ltr) start else horizontal,
                        right = if (layoutDirection == LayoutDirection.Rtl) start else horizontal,
                    ) else WindowInsets(
                        left = horizontal,
                        right = horizontal
                    )
                )
        ).value

    }

    /**
     * The smallest possible height of the top bar.
     */
    @Composable
    internal fun smallContainerHeight(): Dp {
        return with(LocalDensity.current) {
            windowInsets().getTop(this).toDp() + 100.dp
        }
    }

    /**
     *
     * Returns the height of the largest possible height of the top bar.
     *
     * @param factor The multiplication factor to determine the top bar height from the window height.
     *
     */
    @Composable
    internal fun largeContainerHeight(
        // 1/4 of the window height by default.
        factor: Float = 1f / 4f
    ): Dp {

        val minHeight = 200.dp
        val pinnedHeight = smallContainerHeight()
        val configuration = LocalWindowState.current
        val topBarState = LocalTopBarState.current
        val density = LocalDensity.current

        // If the screen orientation is set to landscape, return a minimum
        // top bar container height of 200.dp, otherwise calculate the maximum
        // possible top bar container height using by dividing the window height
        // by the specified factor and return the result with a minimum of 200.dp

        return with(density) {
            if (configuration.screenOrientation != ScreenOrientation.Portrait) {

                val newHeight = minHeight
                    .plus(windowInsets().getTop(this).toDp())
                    .plus(windowInsets().getBottom(this).toDp())

                // Update the top bar height offset limit.
                topBarState.heightOffsetLimit = newHeight
                    .minus(pinnedHeight).toPx()

                // Return the calculated minimum height.
                newHeight

            } else {

                // Calculate the maximum height.
                val maxHeight = configuration.availableHeightDp
                    .times(factor.coerceIn(.25f, .5f))
                    .coerceAtLeast(minHeight)
                    .plus(windowInsets().getTop(this).toDp())
                    .plus(windowInsets().getBottom(this).toDp())

                // Update the top bar height offset limit.
                topBarState.heightOffsetLimit = maxHeight
                    .minus(pinnedHeight).toPx()

                // Return the calculated maximum height.
                maxHeight

            }
        }

    }

    @Composable
    internal fun initialHeightOffsetLimit(): Float {
        return with(LocalDensity.current) {
            largeContainerHeight()
                .minus((smallContainerHeight()))
                .toPx()
        }
    }

}