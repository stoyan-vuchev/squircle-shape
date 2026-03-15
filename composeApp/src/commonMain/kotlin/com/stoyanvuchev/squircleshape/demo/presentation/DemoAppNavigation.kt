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

package com.stoyanvuchev.squircleshape.demo.presentation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.ui.NavDisplay
import com.stoyanvuchev.squircleshape.demo.core.ui.util.transitions.defaultPredictivePopTransitionSpec
import com.stoyanvuchev.squircleshape.demo.presentation.app.about.AboutScreen
import com.stoyanvuchev.squircleshape.demo.presentation.app.demo.DemoScreen
import com.stoyanvuchev.squircleshape.demo.presentation.app.faq.FAQScreen
import com.stoyanvuchev.squircleshape.demo.presentation.app.setup.SetupScreen
import com.stoyanvuchev.squircleshape.demo.presentation.app.usage.UsageScreen

@Composable
fun DemoAppNavigation(
    backStack: SnapshotStateList<DemoAppNavigation>
) = NavDisplay(
    modifier = Modifier.fillMaxSize(),
    backStack = backStack,
    onBack = { backStack.removeLastOrNull() },
    predictivePopTransitionSpec = defaultPredictivePopTransitionSpec(),
    entryProvider = { key ->
        when (key) {

            is DemoAppNavigation.Demo -> NavEntry(key) {
                DemoScreen(
                    onGetStarted = { backStack.add(DemoAppNavigation.Setup) }
                )
            }

            is DemoAppNavigation.Setup -> NavEntry(key) {
                SetupScreen(
                    onNavigateUp = { backStack.removeLastOrNull() },
                    onExploreUsage = { backStack.add(DemoAppNavigation.Usage) }
                )
            }

            is DemoAppNavigation.Usage -> NavEntry(key) {
                UsageScreen(
                    onNavigateUp = { backStack.removeLastOrNull() },
                    onFAQ = { backStack.add(DemoAppNavigation.FAQ) }
                )
            }

            is DemoAppNavigation.FAQ -> NavEntry(key) {
                FAQScreen(
                    onNavigateUp = { backStack.removeLastOrNull() },
                    onGoToAbout = { backStack.add(DemoAppNavigation.About) }
                )
            }

            is DemoAppNavigation.About -> NavEntry(key) {
                AboutScreen(
                    onNavigateUp = { backStack.removeLastOrNull() }
                )
            }

        }
    }
)

@Immutable
sealed interface DemoAppNavigation {
    data object Demo : DemoAppNavigation
    data object Setup : DemoAppNavigation
    data object Usage : DemoAppNavigation
    data object FAQ : DemoAppNavigation
    data object About : DemoAppNavigation
}