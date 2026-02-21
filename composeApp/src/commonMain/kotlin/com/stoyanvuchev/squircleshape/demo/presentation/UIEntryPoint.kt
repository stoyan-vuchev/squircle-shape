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
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.UiComposable
import androidx.compose.ui.unit.dp
import com.stoyanvuchev.squircleshape.demo.core.ui.UIThemeWrapper
import com.stoyanvuchev.squircleshape.demo.core.ui.component.bottombar.BottomBar
import com.stoyanvuchev.squircleshape.demo.core.ui.component.bottombar.action.BottomBarAction
import com.stoyanvuchev.squircleshape.demo.core.ui.component.bottombar.item.BottomBarItem
import com.stoyanvuchev.squircleshape.demo.core.ui.component.layout.ScaffoldLayout
import com.stoyanvuchev.squircleshape.demo.core.ui.component.layout.spacer.VerticalSpacer
import com.stoyanvuchev.squircleshape.demo.core.ui.component.siderail.SideRail
import com.stoyanvuchev.squircleshape.demo.core.ui.component.siderail.action.SideRailAction
import com.stoyanvuchev.squircleshape.demo.core.ui.component.siderail.item.SideRailItem
import com.stoyanvuchev.squircleshape.demo.core.ui.component.text.Text
import com.stoyanvuchev.squircleshape.demo.core.ui.component.topbar.TopBar
import com.stoyanvuchev.squircleshape.demo.core.ui.component.topbar.topBarStickyHeader
import org.jetbrains.compose.resources.painterResource
import squircleshape.composeapp.generated.resources.Res
import squircleshape.composeapp.generated.resources.documentation
import squircleshape.composeapp.generated.resources.home
import squircleshape.composeapp.generated.resources.settings

@OptIn(ExperimentalComposeUiApi::class)
@Composable
@UiComposable
fun UIEntryPoint() = UIThemeWrapper {

    val lazyListState = rememberLazyListState()
    var selectedItem by remember { mutableIntStateOf(0) }
    val onSelected = remember<(Int) -> Unit>(selectedItem) {
        { i -> selectedItem = i }
    }

    ScaffoldLayout(
        modifier = Modifier.fillMaxSize(),
        topBar = {

            TopBar(
                title = "Squircle Demo"
            )

        },
        sideRail = {

            SideRail(
                selectedItemIndex = { selectedItem },
                itemsCount = { 2 },
                action = {

                    SideRailAction(
                        icon = { painterResource(Res.drawable.settings) },
                        onClick = {}
                    )

                }
            ) {

                SideRailItem(
                    icon = { painterResource(Res.drawable.home) },
                    label = "Demo",
                    selected = selectedItem == 0,
                    onSelected = remember { { onSelected(0) } }
                )

                SideRailItem(
                    icon = { painterResource(Res.drawable.documentation) },
                    label = "Docs",
                    selected = selectedItem == 1,
                    onSelected = remember { { onSelected(1) } }
                )

            }

        },
        bottomBar = {

            BottomBar(
                selectedItemIndex = { selectedItem },
                itemsCount = { 2 },
                action = {

                    BottomBarAction(
                        icon = { painterResource(Res.drawable.settings) },
                        onClick = {}
                    )

                }
            ) {

                BottomBarItem(
                    icon = { painterResource(Res.drawable.home) },
                    label = "Demo",
                    selected = selectedItem == 0,
                    onSelected = remember { { onSelected(0) } }
                )

                BottomBarItem(
                    icon = { painterResource(Res.drawable.documentation) },
                    label = "Docs",
                    selected = selectedItem == 1,
                    onSelected = remember { { onSelected(1) } }
                )

            }

        }
    ) { safePadding ->

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            state = lazyListState,
            contentPadding = safePadding,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {

            topBarStickyHeader(
                lazyListState = lazyListState
            )

            item(
                key = "top_spacer",
                contentType = "spacer"
            ) {
                VerticalSpacer(height = 20.dp)
            }

            items(
                count = 500,
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
                VerticalSpacer(height = 32.dp)
            }

        }

    }

}