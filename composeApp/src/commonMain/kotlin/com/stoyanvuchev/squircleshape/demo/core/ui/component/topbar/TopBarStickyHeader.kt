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

import androidx.compose.animation.core.EaseInOutCubic
import androidx.compose.animation.core.tween
import androidx.compose.foundation.gestures.animateScrollBy
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.grid.LazyGridScope
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.Stable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.snapshotFlow
import com.stoyanvuchev.squircleshape.demo.core.ui.component.layout.scaffold.ScaffoldLayout
import com.stoyanvuchev.squircleshape.demo.core.ui.component.layout.spacer.VerticalSpacer
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import kotlin.math.abs
import kotlin.math.roundToInt

/**
 * Adds a sticky header entry to a [LazyListScope] that drives the
 * [TopBarState] collapse progress based on scroll position.
 *
 * This does NOT render the actual top bar.
 * It only:
 * 1. Observes scroll position
 * 2. Calculates collapse offset
 * 3. Updates [LocalTopBarState]
 * 4. Inserts a spacer equal to the maximum top bar height
 *
 * The actual [TopBar] composable is rendered in [ScaffoldLayout].
 */
fun LazyListScope.topBarStickyHeader(
    lazyListState: () -> LazyListState
) = stickyHeader(
    key = TOP_BAR_STICKY_HEADER_KEY,
    contentType = TOP_BAR_STICKY_HEADER_CONTENT_TYPE,
    content = {

        val scope = rememberCoroutineScope()

        TopBarScrollEffect(
            scrollState = remember(lazyListState) {
                LazyListTopBarScrollState(lazyListState())
            },
            onSnap = { scrollOffset ->
                scope.launch {
                    lazyListState().animateScrollBy(
                        value = scrollOffset,
                        animationSpec = tween(
                            easing = EaseInOutCubic
                        )
                    )
                }
            }
        )

    }
)

/**
 * Grid equivalent of [topBarStickyHeader] for [LazyGridScope].
 *
 * Uses the same scroll-collapse logic but adapts to [LazyGridState].
 */
fun LazyGridScope.topBarStickyHeader(
    lazyGridState: () -> LazyGridState
) = stickyHeader(
    key = TOP_BAR_STICKY_HEADER_KEY,
    contentType = TOP_BAR_STICKY_HEADER_CONTENT_TYPE,
    content = {

        val scope = rememberCoroutineScope()

        TopBarScrollEffect(
            scrollState = remember(lazyGridState) {
                LazyGridTopBarScrollState(lazyGridState())
            },
            onSnap = { scrollOffset ->
                scope.launch {
                    lazyGridState().animateScrollBy(
                        value = scrollOffset,
                        animationSpec = tween(
                            easing = EaseInOutCubic
                        )
                    )
                }
            }
        )

    }
)

/**
 * Observes scroll changes from a generic [TopBarScrollState]
 * and updates the shared [TopBarState] collapse offset.
 *
 * This composable:
 * - Converts scroll offset to collapse offset
 * - Clamps values safely
 * - Keeps behavior identical for list & grid
 *
 * It acts as the behavior layer between scroll containers
 * and the visual TopBar component.
 */
@Composable
private fun TopBarScrollEffect(
    scrollState: TopBarScrollState,
    onSnap: (Float) -> Unit
) {

    // The state of the top bar.
    val topBarState = LocalTopBarState.current

    // Total collapsible range
    val collapseRange by remember {
        derivedStateOf { topBarState.heightOffsetLimit }
    }

    // Memoized setter to avoid unnecessary allocations during collection
    val onApplyOffset = remember<(Float) -> Unit>(topBarState) {
        { newOffset -> topBarState.heightOffset = newOffset }
    }

    // Reacts to scroll position updates.
    // [snapshotFlow] converts state reads into a cold flow,
    // emitting only when values change.
    LaunchedEffect(scrollState, collapseRange) {
        snapshotFlow { scrollState.indexAndOffset }
            .distinctUntilChanged()
            .collectLatest { (index, offset) ->

                /**
                 * If the first visible item is not the sticky header anymore,
                 * the top bar must be fully collapsed.
                 *
                 * Otherwise, collapse proportionally to scroll offset.
                 */
                val newOffset = when {
                    index > 0 -> collapseRange
                    else -> {
                        val raw = offset.toFloat()
                        if (abs(raw - topBarState.heightOffset) < 2f) topBarState.heightOffset // ignore micro changes
                        else raw.coerceIn(0f, collapseRange)
                    }
                }

                // Ensure offset stays within valid range
                onApplyOffset(newOffset)

            }
    }

    LaunchedEffect(scrollState, collapseRange, topBarState) {
        snapshotFlow { scrollState.indexOffsetAndScrollProgress }
            .distinctUntilChanged()
            .collectLatest { (currentIndex, currentOffset, isScrolling) ->

                // Scroll has just stopped → debounce + check if snap needed
                delay(100L)
                if (!isScrolling) {

                    // Only consider snapping if we're at the top item and offset is meaningful
                    val collapseRangePx = collapseRange.roundToInt()
                    val minSnapDistancePx = 12
                    if (currentIndex != 0 ||
                        currentOffset == 0 ||
                        currentOffset == collapseRangePx ||
                        abs(currentOffset - collapseRangePx) < minSnapDistancePx ||
                        abs(currentOffset) < minSnapDistancePx
                    ) return@collectLatest

                    // Only snap if partially collapsed (index 0, offset not at boundary).
                    if (
                        currentIndex == 0
                        && currentOffset != 0
                        && currentOffset != collapseRange.roundToInt()
                    ) {

                        val fraction = topBarState.collapsedFraction
                        val targetOffset = if (fraction > .5f) {
                            collapseRange / 2 // Snap to collapsed
                        } else -collapseRange // Snap to expanded

                        // Animate scroll to target (drives heightOffset smoothly).
                        if (fraction != 1f && fraction != 0f) {
                            onSnap(targetOffset)
                        }

                    }

                }

            }
    }

    VerticalSpacer(
        height = TopBarUtils.largeContainerHeight()
    )

}

private const val TOP_BAR_STICKY_HEADER_KEY = "top_bar_sticky_header_key"
private const val TOP_BAR_STICKY_HEADER_CONTENT_TYPE = "top_bar_sticky_header"

/**
 * Abstraction layer for scroll containers.
 *
 * Allows LazyListState and LazyGridState to share
 * the same collapse logic without duplication.
 */
@Stable
private interface TopBarScrollState {
    val firstVisibleItemIndex: Int
    val firstVisibleItemScrollOffset: Int
    val isScrollInProgress: Boolean
}

/**
 * Adapter for [LazyListState].
 */
private class LazyListTopBarScrollState(
    private val state: LazyListState
) : TopBarScrollState {

    override val firstVisibleItemIndex: Int
        get() = state.firstVisibleItemIndex

    override val firstVisibleItemScrollOffset: Int
        get() = state.firstVisibleItemScrollOffset

    override val isScrollInProgress: Boolean
        get() = state.isScrollInProgress

}

/**
 * Adapter for [LazyGridState].
 */
private class LazyGridTopBarScrollState(
    private val state: LazyGridState
) : TopBarScrollState {

    override val firstVisibleItemIndex: Int
        get() = state.firstVisibleItemIndex

    override val firstVisibleItemScrollOffset: Int
        get() = state.firstVisibleItemScrollOffset

    override val isScrollInProgress: Boolean
        get() = state.isScrollInProgress

}

/**
 * Convenience extension for reading scroll index + offset together.
 */
private val TopBarScrollState.indexAndOffset: Pair<Int, Int>
    get() = firstVisibleItemIndex to firstVisibleItemScrollOffset

/**
 * Convenience extension for reading scroll index + offset + isScrollInProgress together.
 */
private val TopBarScrollState.indexOffsetAndScrollProgress: Triple<Int, Int, Boolean>
    get() = Triple(firstVisibleItemIndex, firstVisibleItemScrollOffset, isScrollInProgress)