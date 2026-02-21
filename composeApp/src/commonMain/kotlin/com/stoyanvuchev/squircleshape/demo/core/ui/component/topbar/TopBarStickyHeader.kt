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

import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.grid.LazyGridScope
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.platform.LocalDensity
import com.stoyanvuchev.squircleshape.demo.core.ui.component.layout.ScaffoldLayout
import com.stoyanvuchev.squircleshape.demo.core.ui.component.layout.spacer.VerticalSpacer
import kotlinx.coroutines.flow.collectLatest

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
    lazyListState: LazyListState
) = stickyHeader(
    key = TOP_BAR_STICKY_HEADER_KEY,
    contentType = TOP_BAR_STICKY_HEADER_CONTENT_TYPE,
    content = {

        TopBarScrollEffect(
            scrollState = remember(lazyListState) {
                LazyListTopBarScrollState(lazyListState)
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
    lazyGridState: LazyGridState
) = stickyHeader(
    key = TOP_BAR_STICKY_HEADER_KEY,
    contentType = TOP_BAR_STICKY_HEADER_CONTENT_TYPE,
    content = {

        TopBarScrollEffect(
            scrollState = remember(lazyGridState) {
                LazyGridTopBarScrollState(lazyGridState)
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
    scrollState: TopBarScrollState
) {

    val topBarState = LocalTopBarState.current
    val density = LocalDensity.current

    // Maximum expanded height of the top bar
    val maxHeight = TopBarUtils.largeContainerHeight()

    // Minimum (pinned) height of the top bar
    val pinnedHeight = TopBarUtils.smallContainerHeight()

    // Memoized setter to avoid unnecessary allocations during collection
    val onApplyOffset = remember<(Float) -> Unit>(topBarState) {
        { newOffset -> topBarState.heightOffset = newOffset }
    }

    // Reacts to scroll position updates.
    // [snapshotFlow] converts state reads into a cold flow,
    // emitting only when values change.
    LaunchedEffect(scrollState, density) {
        snapshotFlow { scrollState.indexAndOffset }
            .collectLatest { (index, offset) ->

                // Convert Dp heights to pixels
                val maxHeightPx = with(density) { maxHeight.toPx() }
                val pinnedHeightPx = with(density) { pinnedHeight.toPx() }

                // Total collapsible range
                val collapseRange = maxHeightPx - pinnedHeightPx

                /**
                 * If the first visible item is not the sticky header anymore,
                 * the top bar must be fully collapsed.
                 *
                 * Otherwise collapse proportionally to scroll offset.
                 */
                val newOffset = when {
                    index > 0 -> collapseRange
                    else -> offset
                        .coerceIn(0, collapseRange.toInt())
                        .toFloat()
                }

                // Ensure offset stays within valid range
                onApplyOffset(newOffset.coerceIn(0f, collapseRange))

            }
    }

    // Spacer that reserves the expanded height inside the lazy container.
    // This keeps scroll math correct while the actual TopBar is rendered separately.
    VerticalSpacer(height = maxHeight)

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

}

/**
 * Convenience extension for reading scroll index + offset together.
 */
private val TopBarScrollState.indexAndOffset: Pair<Int, Int>
    get() = firstVisibleItemIndex to firstVisibleItemScrollOffset