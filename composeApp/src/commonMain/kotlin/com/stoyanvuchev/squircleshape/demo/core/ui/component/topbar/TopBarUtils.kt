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

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
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
     * This is especially useful when building layouts where a [SideNavRail] is present —
     * allowing the top bar to ignore start padding and remain properly aligned without extra spacing.
     *
     * @param ignoreStart If true, ignores the start (left on LTR, right on RTL) safe inset and only considers the end side.
     * Useful when a [SideNavRail] or other permanent navigation rail is present.
     *
     * @return A [WindowInsets] representing the requested sides of the safe drawing area.
     */
    @Composable
    fun windowInsets(
        ignoreStart: Boolean = false
    ): WindowInsets {
        return rememberUpdatedState(
            WindowInsets.safeDrawing
                .only(
                    sides = WindowInsetsSides.Top + if (!ignoreStart) {
                        WindowInsetsSides.Horizontal
                    } else WindowInsetsSides.End
                )
        ).value
    }

    /**
     * The smallest possible height of the top bar.
     */
    @Composable
    internal fun smallContainerHeight(): Dp {
        return 80.dp + with(LocalDensity.current) {
            windowInsets().getTop(this).toDp()
        }
    }

    /**
     *
     * Returns the height of an largest possible height of the top bar.
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
        val configuration = LocalWindowState.current
        var maxHeight by remember { mutableStateOf<Dp?>(null) }

        // If the screen orientation is set to landscape, return a minimum
        // top bar container height of 200.dp, otherwise calculate the maximum
        // possible top bar container height using by dividing the window height
        // by the specified factor and return the result with a minimum of 200.dp

        return if (configuration.screenOrientation != ScreenOrientation.Portrait) minHeight
        else maxHeight ?: LocalDensity.current.run {

            // Calculate the maximum height.
            maxHeight = ((configuration.availableHeightDp) * factor.coerceIn(.25f, .5f))
                .coerceAtLeast(minHeight)

            // Return the calculated maximum height.
            maxHeight!!

        }

    }

    @Composable
    internal fun initialHeightOffsetLimit() = with(LocalDensity.current) {
        (largeContainerHeight() - smallContainerHeight()).toPx()
    }

}