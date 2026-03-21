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

package com.stoyanvuchev.squircleshape.demo.presentation.app.faq

import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.retain.retain
import com.stoyanvuchev.squircleshape.demo.core.ui.component.layout.scaffold.NestedScaffoldLayout
import com.stoyanvuchev.squircleshape.demo.core.ui.component.topbar.TopBar
import com.stoyanvuchev.squircleshape.demo.presentation.content.screenContent
import com.stoyanvuchev.squircleshape.demo.presentation.defaults.DefaultBackgroundDecoration
import com.stoyanvuchev.squircleshape.demo.presentation.defaults.DefaultLazyVerticalGrid
import com.stoyanvuchev.squircleshape.demo.presentation.defaults.DefaultTopBarAction
import squircleshape.composeapp.generated.resources.Res
import squircleshape.composeapp.generated.resources.faq_outlined

@Composable
fun FAQScreen(
    onNavigateUp: () -> Unit,
    onGoToAbout: () -> Unit
) {

    val lazyGridState = rememberLazyGridState()
    val screenContent = retain { faqScreenContent(onGoToAbout) }

    NestedScaffoldLayout(
        topBar = {

            TopBar(
                title = "FAQ",
                primaryActions = { DefaultTopBarAction(onNavigateUp = onNavigateUp) }
            )

        },
        background = { safePadding ->

            DefaultBackgroundDecoration(
                safePadding = safePadding,
                icon = { Res.drawable.faq_outlined }
            )

        },
        content = {

            DefaultLazyVerticalGrid(
                lazyGridState = lazyGridState,
                content = { screenContent { lazyGridState to screenContent } }
            )

        }
    )

}