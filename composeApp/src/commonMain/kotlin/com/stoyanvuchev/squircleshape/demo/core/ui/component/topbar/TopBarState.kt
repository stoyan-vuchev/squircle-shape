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

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.compositionLocalOf
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
    initialHeightOffsetLimit: Float = TopBarUtils.initialHeightOffsetLimit(),
    initialHeightOffset: Float = 0f
): TopBarState = rememberSaveable(saver = Saver) {
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
            _heightOffset = newOffset
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

/**
 * A CompositionLocal key for accessing a [TopBarState] in nested composition.
 */
val LocalTopBarState = compositionLocalOf {
    TopBarState(
        initialHeightOffsetLimit = Float.MAX_VALUE,
        initialHeightOffset = 0f
    )
}