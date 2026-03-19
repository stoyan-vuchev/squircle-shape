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

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.stoyanvuchev.squircleshape.demo.core.ui.UIThemeWrapper
import com.stoyanvuchev.squircleshape.demo.core.ui.component.layout.scaffold.ScaffoldLayout
import com.stoyanvuchev.squircleshape.demo.di.appModule
import org.koin.compose.KoinApplication
import org.koin.compose.viewmodel.koinViewModel
import org.koin.dsl.KoinAppDeclaration

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun DemoApp(
    koinAppDeclaration: KoinAppDeclaration? = null
) = KoinApplication(
    application = {
        koinAppDeclaration?.invoke(this)
        modules(appModule)
    }
) {

    val viewModel = koinViewModel<DemoAppViewModel>()
    val latestRelease by viewModel.latestRelease.collectAsStateWithLifecycle()

    CompositionLocalProvider(LocalLatestRelease provides latestRelease) {

        val backStack = rememberDemoAppNavigationBackStack()

        var isListVisible by remember { mutableStateOf(false) }
        val onIsListVisible = remember(isListVisible) {
            { isListVisible = !isListVisible }
        }

        val selectedItem by rememberUpdatedState(backStack.last() as DemoAppNavigation)
        val onSelected = remember<(DemoAppNavigation) -> Unit>(selectedItem) {
            { item ->
                if (item != selectedItem) {
                    backStack.add(item)
                    isListVisible = false
                }
            }
        }

        UIThemeWrapper {

            ScaffoldLayout(
                sideRail = {

                    DemoAppSideRail(
                        selectedItem = { selectedItem },
                        onSelected = onSelected
                    )

                },
                bottomBar = {

                    DemoAppBottomBar(
                        selectedItem = { selectedItem },
                        isListVisible = isListVisible,
                        onSelected = onSelected,
                        onIsListVisible = onIsListVisible
                    )

                },
                content = {

                    DemoAppNavigation(
                        backStack = { backStack }
                    )

                }
            )

        }

    }

}