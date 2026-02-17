/*
 * Copyright 2021 The Android Open Source Project
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

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.listSaver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import com.stoyanvuchev.squircleshape.demo.core.ui.component.topbar.TopBarState.Companion.Saver

/**
 *
 * Creates a [TopBarState] that is remembered across compositions.
 *
 * @param initialHeightOffsetLimit the initial value for [TopBarState.heightOffsetLimit],
 * which represents the pixel limit that a top app bar is allowed to collapse when the scrollable
 * content is scrolled.
 *
 * @param initialHeightOffset the initial value for [TopBarState.heightOffset]. The initial
 * offset height offset should be between zero and [initialHeightOffsetLimit].
 *
 */
@Composable
fun rememberTopBarState(
    initialHeightOffsetLimit: Float = -Float.MAX_VALUE,
    initialHeightOffset: Float = 0f
): TopBarState = rememberSaveable(saver = TopBarState.Saver) {
    TopBarState(
        initialHeightOffsetLimit,
        initialHeightOffset
    )
}

@Stable
class TopBarState(
    initialHeightOffsetLimit: Float,
    initialHeightOffset: Float
) {

    private var _heightOffset: Float by mutableFloatStateOf(initialHeightOffset)

    /**
     * The top bar's height offset limit in pixels, which represents the limit that a top app
     * bar is allowed to collapse to.
     *
     * Use this limit to coerce the [heightOffset] value when it's updated.
     */
    var heightOffsetLimit: Float by mutableFloatStateOf(initialHeightOffsetLimit)

    /**
     * The top bar's current height offset in pixels. This height offset is applied to the fixed
     * height of the app bar to control the displayed height when content is being scrolled.
     *
     * Updates to the [heightOffset] value are coerced between zero and [heightOffsetLimit].
     */
    var heightOffset: Float
        get() = _heightOffset
        set(newOffset) {
            _heightOffset = newOffset.coerceIn(
                minimumValue = heightOffsetLimit,
                maximumValue = 0f
            )
        }

    /**
     * A value that represents the collapsed height percentage of the top bar.
     *
     * A `0.0` represents a fully expanded bar, and `1.0` represents a fully collapsed bar (computed
     * as [heightOffset] / [heightOffsetLimit]).
     */
    val collapsedFraction: Float by derivedStateOf {
        if (heightOffsetLimit != 0f) heightOffset / heightOffsetLimit else 0f
    }

    companion object {

        /**
         * The default [Saver] implementation for [TopBarState].
         */
        val Saver: Saver<TopBarState, *> = listSaver(
            save = { listOf(it.heightOffsetLimit, it.heightOffset) },
            restore = {
                TopBarState(
                    initialHeightOffsetLimit = it[0],
                    initialHeightOffset = it[1]
                )
            }
        )

    }

}