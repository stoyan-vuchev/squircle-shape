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

package com.stoyanvuchev.squircleshape.demo.presentation.app.osl

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.CircularWavyProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.mikepenz.aboutlibraries.Libs
import com.stoyanvuchev.squircleshape.demo.core.ui.Theme
import com.stoyanvuchev.squircleshape.demo.core.ui.component.layout.scaffold.LocalScaffoldLayoutPadding
import com.stoyanvuchev.squircleshape.demo.core.ui.component.layout.scaffold.NestedScaffoldLayout
import com.stoyanvuchev.squircleshape.demo.core.ui.component.layout.spacer.VerticalSpacer
import com.stoyanvuchev.squircleshape.demo.core.ui.component.topbar.TopBar
import com.stoyanvuchev.squircleshape.demo.core.ui.component.topbar.topBarStickyHeader
import com.stoyanvuchev.squircleshape.demo.presentation.app.osl.components.OslScreenItem
import com.stoyanvuchev.squircleshape.demo.presentation.defaults.DefaultBackgroundDecoration
import com.stoyanvuchev.squircleshape.demo.presentation.defaults.DefaultTopBarAction
import squircleshape.composeapp.generated.resources.Res
import squircleshape.composeapp.generated.resources.license_outlined

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun OslScreen(
    libs: () -> Libs?,
    onNavigateUp: () -> Unit
) {

    val lazyGridState = rememberLazyGridState()

    NestedScaffoldLayout(
        topBar = {

            TopBar(
                title = "Open Source Licenses",
                primaryActions = { DefaultTopBarAction(onNavigateUp = onNavigateUp) }
            )

        },
        background = { safePadding ->

            DefaultBackgroundDecoration(
                safePadding = safePadding,
                icon = Res.drawable.license_outlined
            )

        },
        content = {

            AnimatedContent(
                modifier = Modifier.fillMaxSize(),
                targetState = libs() != null,
                transitionSpec = remember { { fadeIn() togetherWith fadeOut() } }
            ) { loadedLibs ->

                if (loadedLibs) {

                    LazyVerticalGrid(
                        modifier = Modifier.fillMaxSize(),
                        state = lazyGridState,
                        contentPadding = LocalScaffoldLayoutPadding.current,
                        verticalArrangement = Arrangement.spacedBy(8.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        columns = GridCells.Adaptive(minSize = 360.dp)
                    ) {

                        topBarStickyHeader { lazyGridState }

                        libs()?.let { data ->

                            item(
                                key = "top_spacer",
                                contentType = "spacer",
                                span = { GridItemSpan(this.maxLineSpan) },
                                content = { VerticalSpacer(height = 32.dp) }
                            )

                            items(
                                items = data.libraries,
                                key = { it.uniqueId },
                                contentType = { "card" },
                                itemContent = { OslScreenItem { it } }
                            )

                            item(
                                key = "bottom_spacer",
                                contentType = "spacer",
                                span = { GridItemSpan(this.maxLineSpan) },
                                content = { VerticalSpacer(height = 128.dp) }
                            )

                        }

                    }

                } else {

                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {

                        CircularWavyProgressIndicator(
                            color = Theme.colorScheme.primary,
                            trackColor = Theme.colorScheme.primary.copy(alpha = .33f)
                        )

                    }

                }

            }

        }
    )

}