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
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import androidx.savedstate.serialization.SavedStateConfiguration
import com.stoyanvuchev.squircleshape.demo.core.ui.util.transitions.defaultPredictivePopTransitionSpec
import com.stoyanvuchev.squircleshape.demo.presentation.app.about.AboutScreen
import com.stoyanvuchev.squircleshape.demo.presentation.app.demo.DemoScreen
import com.stoyanvuchev.squircleshape.demo.presentation.app.faq.FAQScreen
import com.stoyanvuchev.squircleshape.demo.presentation.app.osl.OslScreen
import com.stoyanvuchev.squircleshape.demo.presentation.app.osl.OslScreenViewModel
import com.stoyanvuchev.squircleshape.demo.presentation.app.setup.SetupScreen
import com.stoyanvuchev.squircleshape.demo.presentation.app.usage.UsageScreen
import kotlinx.serialization.Serializable
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun DemoAppNavigation(
    backStack: () -> NavBackStack<NavKey>
) = NavDisplay(
    modifier = Modifier.fillMaxSize(),
    backStack = backStack(),
    onBack = { backStack().removeLastOrNull() ?: backStack().add(DemoAppNavigation.Demo) },
    predictivePopTransitionSpec = defaultPredictivePopTransitionSpec(),
    entryDecorators = listOf(
        rememberSaveableStateHolderNavEntryDecorator(),
        rememberViewModelStoreNavEntryDecorator()
    ),
    entryProvider = entryProvider {

        entry<DemoAppNavigation.Demo> {
            DemoScreen(
                onGetStarted = { backStack() += DemoAppNavigation.Setup }
            )
        }

        entry<DemoAppNavigation.Setup> {
            SetupScreen(
                onNavigateUp = { backStack() -= DemoAppNavigation.Setup },
                onExploreUsage = { backStack() += DemoAppNavigation.Usage }
            )
        }

        entry<DemoAppNavigation.Usage> {
            UsageScreen(
                onNavigateUp = { backStack() -= DemoAppNavigation.Usage },
                onFAQ = { backStack() += DemoAppNavigation.FAQ }
            )
        }

        entry<DemoAppNavigation.FAQ> {
            FAQScreen(
                onNavigateUp = { backStack() -= DemoAppNavigation.FAQ },
                onGoToAbout = { backStack() += DemoAppNavigation.About }
            )
        }

        entry<DemoAppNavigation.About> {
            AboutScreen(
                onNavigateUp = { backStack() -= DemoAppNavigation.About },
                onOsl = { backStack() += DemoAppNavigation.Osl }
            )
        }

        entry<DemoAppNavigation.Osl> {

            val viewModel = koinViewModel<OslScreenViewModel>()
            val libs by viewModel.libs.collectAsStateWithLifecycle()

            OslScreen(
                libs = { libs },
                onNavigateUp = { backStack() -= DemoAppNavigation.Osl }
            )

        }

    }
)

@Composable
fun rememberDemoAppNavigationBackStack(): NavBackStack<NavKey> {
    return rememberNavBackStack(
        configuration = SavedStateConfiguration {
            serializersModule = SerializersModule {
                polymorphic(NavKey::class) {
                    subclass(
                        DemoAppNavigation.Demo::class,
                        DemoAppNavigation.Demo.serializer()
                    )
                    subclass(
                        DemoAppNavigation.Setup::class,
                        DemoAppNavigation.Setup.serializer()
                    )
                    subclass(
                        DemoAppNavigation.Usage::class,
                        DemoAppNavigation.Usage.serializer()
                    )
                    subclass(
                        DemoAppNavigation.FAQ::class,
                        DemoAppNavigation.FAQ.serializer()
                    )
                    subclass(
                        DemoAppNavigation.About::class,
                        DemoAppNavigation.About.serializer()
                    )
                    subclass(
                        DemoAppNavigation.Osl::class,
                        DemoAppNavigation.Osl.serializer()
                    )
                }
            }
        },
        DemoAppNavigation.Demo
    )
}

@Immutable
@Serializable
sealed interface DemoAppNavigation : NavKey {

    @Serializable
    data object Demo : DemoAppNavigation, NavKey

    @Serializable
    data object Setup : DemoAppNavigation, NavKey

    @Serializable
    data object Usage : DemoAppNavigation, NavKey

    @Serializable
    data object FAQ : DemoAppNavigation, NavKey

    @Serializable
    data object About : DemoAppNavigation, NavKey

    @Serializable
    data object Osl : DemoAppNavigation, NavKey

}