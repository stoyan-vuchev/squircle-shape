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

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.stoyanvuchev.squircleshape.demo.core.ui.Theme
import com.stoyanvuchev.squircleshape.demo.core.ui.component.AnimatedReveal
import com.stoyanvuchev.squircleshape.demo.core.ui.component.bottombar.BottomBar
import com.stoyanvuchev.squircleshape.demo.core.ui.component.bottombar.action.BottomBarAction
import com.stoyanvuchev.squircleshape.demo.core.ui.component.bottombar.item.BottomBarItem
import com.stoyanvuchev.squircleshape.demo.core.ui.component.layout.spacer.HorizontalSpacer
import com.stoyanvuchev.squircleshape.demo.core.ui.component.navigation.navlist.NavigationList
import com.stoyanvuchev.squircleshape.demo.core.ui.component.navigation.navlist.NavigationListItem
import org.jetbrains.compose.resources.painterResource
import squircleshape.composeapp.generated.resources.Res
import squircleshape.composeapp.generated.resources.documentation
import squircleshape.composeapp.generated.resources.explore_outlined
import squircleshape.composeapp.generated.resources.faq_outlined
import squircleshape.composeapp.generated.resources.info_outlined
import squircleshape.composeapp.generated.resources.menu
import squircleshape.composeapp.generated.resources.settings_outlined
import squircleshape.composeapp.generated.resources.squircle_icon_outlined

@Composable
fun DemoAppBottomBar(
    selectedItem: () -> DemoAppNavigation,
    isListVisible: Boolean,
    onSelected: (DemoAppNavigation) -> Unit,
    onIsListVisible: () -> Unit,
) {

    val selectedItemIndex by rememberUpdatedState(
        when (selectedItem()) {
            is DemoAppNavigation.Demo -> 0
            else -> 1
        }
    )

    BottomBar(
        selectedItemIndex = selectedItemIndex,
        itemsCount = 2,
        action = {

            AnimatedReveal(
                modifier = Modifier.fillMaxHeight(),
                visible = selectedItemIndex > 0
            ) {

                Row {

                    HorizontalSpacer(width = 12.dp)

                    BottomBarAction(
                        icon = { painterResource(Res.drawable.menu) },
                        onClick = onIsListVisible,
                        contentColor = Theme.colorScheme.accent
                    )

                }

            }

        },
        navList = {

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {

                val selectedListItemIndex by rememberUpdatedState(
                    when (selectedItem()) {
                        is DemoAppNavigation.Demo -> 0
                        is DemoAppNavigation.Setup -> 0
                        is DemoAppNavigation.Usage -> 1
                        is DemoAppNavigation.FAQ -> 2
                        is DemoAppNavigation.About -> 3
                    }
                )

                val isListVisible by rememberUpdatedState(
                    isListVisible && selectedItemIndex > 0
                )

                Spacer(modifier = Modifier.weight(1f))

                AnimatedReveal(
                    visible = isListVisible
                ) {

                    NavigationList(
                        modifier = Modifier
                            .padding(vertical = 20.dp),
                        selectedItemIndex = selectedListItemIndex,
                        itemsCount = 4
                    ) {

                        NavigationListItem(
                            label = "Setup",
                            icon = Res.drawable.settings_outlined,
                            selected = { selectedItem() == DemoAppNavigation.Setup },
                            onSelected = remember { { onSelected(DemoAppNavigation.Setup) } }
                        )

                        NavigationListItem(
                            label = "Usage",
                            icon = Res.drawable.explore_outlined,
                            selected = { selectedItem() == DemoAppNavigation.Usage },
                            onSelected = remember { { onSelected(DemoAppNavigation.Usage) } }
                        )

                        NavigationListItem(
                            label = "FAQ",
                            icon = Res.drawable.faq_outlined,
                            selected = { selectedItem() == DemoAppNavigation.FAQ },
                            onSelected = remember { { onSelected(DemoAppNavigation.FAQ) } }
                        )

                        NavigationListItem(
                            label = "About",
                            icon = Res.drawable.info_outlined,
                            selected = { selectedItem() == DemoAppNavigation.About },
                            onSelected = remember { { onSelected(DemoAppNavigation.About) } }
                        )

                    }

                }

            }

        }
    ) {

        BottomBarItem(
            icon = { painterResource(Res.drawable.squircle_icon_outlined) },
            label = "Demo",
            selected = selectedItem() == DemoAppNavigation.Demo,
            onSelected = remember { { onSelected(DemoAppNavigation.Demo) } }
        )

        BottomBarItem(
            icon = { painterResource(Res.drawable.documentation) },
            label = "Docs",
            selected = selectedItem() != DemoAppNavigation.Demo,
            onSelected = remember { { onSelected(DemoAppNavigation.Setup) } }
        )

    }

}