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

package com.stoyanvuchev.squircleshape.demo.core.ui.util.transitions

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.ContentTransform
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Stable
import androidx.navigation3.scene.Scene
import androidx.navigationevent.NavigationEvent.Companion.EDGE_LEFT

@Stable
fun <T : Any> defaultPredictivePopTransitionSpec(): AnimatedContentTransitionScope<Scene<T>>.(Int) -> ContentTransform =
    { edge ->

        val towards = if (edge == EDGE_LEFT) AnimatedContentTransitionScope.SlideDirection.Right
        else AnimatedContentTransitionScope.SlideDirection.Left

        ContentTransform(
            targetContentEnter = slideIntoContainer(
                towards = towards,
                initialOffset = { it / 4 },
                animationSpec = tween(500, easing = FastOutSlowInEasing),
            ),
            initialContentExit = slideOutOfContainer(
                towards = towards,
                animationSpec = tween(500, easing = FastOutSlowInEasing),
            ),
            targetContentZIndex = 1f
        )

    }