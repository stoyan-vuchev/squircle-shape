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

import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.AnimationState
import androidx.compose.animation.core.DecayAnimationSpec
import androidx.compose.animation.core.animateDecay
import androidx.compose.animation.core.animateTo
import androidx.compose.runtime.Stable
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.unit.Velocity
import com.stoyanvuchev.squircleshape.demo.core.util.Platform
import com.stoyanvuchev.squircleshape.demo.core.util.getPlatform
import kotlin.math.abs

/**
 *
 * A [TopBarScrollBehavior] that adjusts its properties to affect the colors and height of a top bar.
 *
 * A top bar that is set up with this [TopBarScrollBehavior] will immediately collapse when
 * the nested content is pulled up, and will expand back the collapsed area when the content is
 * pulled all the way down.
 *
 * @param state a [TopBarState].
 * @param snapAnimationSpec an optional [AnimationSpec] that defines how the top bar snaps to
 * either fully collapsed or fully extended state when a fling or a drag scrolled it into an
 * intermediate position.
 *
 * @param flingAnimationSpec an optional [DecayAnimationSpec] that defined how to fling the top
 * bar when the user flings the top bar itself, or the content below it.
 *
 * @param canScrollItself a callback used to determine whether scroll events are to be
 * handled by this [ExitUntilCollapsedScrollBehavior].
 *
 */
@Stable
internal class ExitUntilCollapsedScrollBehavior(
    override val state: TopBarState,
    override val snapAnimationSpec: AnimationSpec<Float>?,
    override val flingAnimationSpec: DecayAnimationSpec<Float>?,
    override val canScrollItself: () -> Boolean = { true },
    override val canScrollBackward: () -> Boolean = { false },
) : TopBarScrollBehavior {

    override val isPinned: Boolean = false

    override val nestedScrollConnection = object : NestedScrollConnection {

        override fun onPreScroll(
            available: Offset,
            source: NestedScrollSource
        ): Offset {

            if (!canScrollItself()) return Offset.Zero

            val delta = available.y
            val platform = getPlatform()
            val isDesktopLike = platform == Platform.DESKTOP || platform == Platform.WEB

            if (isDesktopLike) {

                // Scroll down → collapse immediately.
                if (delta < 0f) {
                    state.heightOffset = state.heightOffsetLimit
                }

                // Scroll up AND list is at top → expand.
                if (delta > 0f && !canScrollBackward()) {
                    state.heightOffset = 0f
                }

                return Offset.Zero

            }

            // ----- Mobile behavior below -----

            if (delta < 0f && state.heightOffset > state.heightOffsetLimit) {
                val prev = state.heightOffset
                val next = (prev + delta).coerceAtLeast(state.heightOffsetLimit)
                state.heightOffset = next
                return Offset(0f, next - prev)
            }

            return Offset.Zero

        }

        override fun onPostScroll(
            consumed: Offset,
            available: Offset,
            source: NestedScrollSource
        ): Offset {

            val platform = getPlatform()
            val isDesktopLike = platform == Platform.DESKTOP || platform == Platform.WEB
            if (isDesktopLike) return Offset.Zero

            val delta = available.y
            if (delta > 0f && state.heightOffset < 0f && !canScrollBackward()) {
                val prev = state.heightOffset
                val next = (prev + delta).coerceAtMost(0f)
                state.heightOffset = next
                return Offset(0f, next - prev)
            }

            return Offset.Zero

        }

        override suspend fun onPostFling(consumed: Velocity, available: Velocity): Velocity {
            val superConsumed = super.onPostFling(consumed, available)
            return superConsumed + settleAppBar(
                state = state,
                velocity = available.y,
                flingAnimationSpec = flingAnimationSpec,
                snapAnimationSpec = snapAnimationSpec
            )
        }

    }

}

/**
 * Settles the top bar by flinging, in case the given velocity is greater than zero, and snapping
 * after the fling settles.
 */
@Stable
internal suspend fun settleAppBar(
    state: TopBarState,
    velocity: Float,
    flingAnimationSpec: DecayAnimationSpec<Float>?,
    snapAnimationSpec: AnimationSpec<Float>?
): Velocity {

    // Check if the top bar is completely collapsed/expanded. If so, no need to settle the top bar,
    // and just return Zero Velocity.
    // Note that we don't check for 0f due to float precision with the collapsedFraction
    // calculation.
    if (state.collapsedFraction == 0f || state.collapsedFraction == 1f) {
        return Velocity.Zero
    }

    var remainingVelocity = velocity

    // In case there is an initial velocity that was left after a previous user fling, animate to
    // continue the motion to expand or collapse the top bar.
    if (flingAnimationSpec != null && abs(velocity) > 1f) {
        var lastValue = 0f
        AnimationState(
            initialValue = 0f,
            initialVelocity = velocity,
        ).animateDecay(flingAnimationSpec) {

            val delta = value - lastValue
            val initialHeightOffset = state.heightOffset

            state.heightOffset = initialHeightOffset + delta
            val consumed = abs(initialHeightOffset - state.heightOffset)

            lastValue = value
            remainingVelocity = this.velocity

            // avoid rounding errors and stop if anything is unconsumed
            if (abs(delta - consumed) > 0.5f) this.cancelAnimation()

        }
    }

    // Snap if animation specs were provided.
    if (snapAnimationSpec != null) {
        if (state.heightOffset < 0 ||
            state.heightOffset >= state.heightOffsetLimit
        ) {
            AnimationState(initialValue = state.heightOffset).animateTo(
                targetValue = if (state.collapsedFraction < 0.5f) 0f
                else state.heightOffsetLimit,
                animationSpec = snapAnimationSpec
            ) { state.heightOffset = value }
        }
    }

    return Velocity(0f, remainingVelocity)

}

@Stable
interface TopBarScrollBehavior {

    /**
     * A [TopBarState] that is attached to this behavior and is read and updated when scrolling
     * happens.
     */
    val state: TopBarState

    /**
     * Indicates whether the top bar is pinned.
     *
     * A pinned top bar will stay fixed in place when content is scrolled and will not react to any
     * drag gestures.
     */
    val isPinned: Boolean

    /**
     * Indicates whether the top bar can be scrolled.
     */
    val canScrollItself: () -> Boolean

    /**
     * Indicates whether the scrollable content can be scrolled backwards.
     */
    val canScrollBackward: () -> Boolean

    /**
     * An optional [AnimationSpec] that defines how the top bar snaps to either fully collapsed
     * or fully extended state when a fling or a drag scrolled it into an intermediate position.
     */
    val snapAnimationSpec: AnimationSpec<Float>?

    /**
     * An optional [DecayAnimationSpec] that defined how to fling the top bar when the user
     * flings the top bar itself, or the content below it.
     */
    val flingAnimationSpec: DecayAnimationSpec<Float>?

    /**
     * A [NestedScrollConnection] that should be attached to a Modifier.nestedScroll in order to
     * keep track of the scroll events.
     */
    val nestedScrollConnection: NestedScrollConnection

}