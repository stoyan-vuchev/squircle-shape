/*
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

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.UiComposable
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import com.stoyanvuchev.squircleshape.demo.core.ui.UIThemeWrapper
import com.stoyanvuchev.squircleshape.demo.core.ui.component.layout.ScaffoldLayout
import com.stoyanvuchev.squircleshape.demo.core.ui.component.text.Text
import com.stoyanvuchev.squircleshape.demo.core.ui.component.topbar.CollapsibleTopBar
import com.stoyanvuchev.squircleshape.demo.core.ui.component.topbar.TopBarUtils

@Composable
@UiComposable
fun UIEntryPoint() = UIThemeWrapper {

    val lazyListState = rememberLazyListState()
    val scrollBehavior = TopBarUtils.exitUntilCollapsedScrollBehavior(
        canScrollItself = { !lazyListState.canScrollBackward },
        canScrollBackward = { !lazyListState.canScrollBackward }
    )

    ScaffoldLayout(
        modifier = Modifier.fillMaxSize(),
        topBar = {

            CollapsibleTopBar(
                title = "Title",
                scrollBehavior = scrollBehavior
            )

        }
    ) { safePadding ->

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .nestedScroll(scrollBehavior.nestedScrollConnection),
            state = lazyListState,
            contentPadding = safePadding,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {

            item(
                key = "top_spacer",
                contentType = "spacer"
            ) {
                Spacer(modifier = Modifier.height(32.dp))
            }

            items(
                count = 200,
                key = { "item_$it" },
                contentType = { "item" }
            ) {

                Text(
                    modifier = Modifier.padding(horizontal = 20.dp),
                    text = "Item: ${it + 1}"
                )

            }

            item(
                key = "bottom_spacer",
                contentType = "spacer"
            ) {
                Spacer(modifier = Modifier.height(32.dp))
            }

        }

    }

}