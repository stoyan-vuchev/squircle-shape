/*
 * Copyright 2026 Assertive UI (assertiveui.com)
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

package com.stoyanvuchev.squircleshape.demo.core.ui.component.topbar.action

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import com.stoyanvuchev.squircleshape.demo.core.ui.component.topbar.action.TopBarActionUtils.topBarActionContainerModifier
import com.stoyanvuchev.squircleshape.demo.core.ui.component.topbar.action.TopBarActionUtils.topBarActionIconModifier
import com.stoyanvuchev.squircleshape.demo.core.ui.component.topbar.TopBar

/**
 *
 * A reusable, accessible top bar action component that displays an icon and responds to user interaction.
 *
 * Designed to be used within custom top bars such as [TopBar] or any composable header area
 * that needs consistent action button behavior. This component handles layout, alignment, styling,
 * and integrates with custom Assertive UI modifiers for hover, press, and focus states.
 *
 * @param icon The [Painter] used to render the icon graphic.
 * @param onClick Callback invoked when the action is clicked.
 * @param contentDescription A content description for accessibility and screen readers.
 *
 * Example usage:
 * ```kotlin
 * CollapsibleTopBar(
 *     title = "Title",
 *     scrollBehavior = scrollBehavior,
 *     primaryActions = {
 *
 *         TopBarAction(
 *             icon = { painterResource(Res.drawable.arrow_left) },
 *             onClick = { /* Handle click event here. */ },
 *             contentDescription = "Navigate Up"
 *         )
 *
 *     },
 *     secondaryActions = {
 *
 *         TopBarAction(
 *             icon = { painterResource(Res.drawable.search) },
 *             onClick = { /* Handle click event here. */ },
 *             contentDescription = "Search"
 *         )
 *
 *         TopBarAction(
 *             icon = { painterResource(Res.drawable.menu) },
 *             onClick = { /* Handle click event here. */ },
 *             contentDescription = "Menu"
 *         )
 *
 *     }
 * )
 * ```
 *
 */
@Composable
fun TopBarAction(
    icon: @Composable () -> Painter,
    onClick: () -> Unit,
    contentDescription: String?
) = Box(
    modifier = Modifier.topBarActionContainerModifier(onClick = onClick),
    contentAlignment = Alignment.Center,
    content = {

        Icon(
            modifier = Modifier.topBarActionIconModifier(),
            painter = icon(),
            contentDescription = contentDescription,
            tint = TopBarActionUtils.iconColor
        )

    }
)

/**
 *
 * A composable slot scope used for defining a row of top bar actions.
 *
 * This typealias is used to simplify the API for action parameters in components like [TopBar],
 * allowing developers to pass in action content that aligns horizontally using [RowScope].
 *
 * Actions defined within this scope are typically aligned to the start or end of a top bar and can
 * include components like a [TopBarAction].
 *
 */
typealias TopBarActionsScope = @Composable (RowScope.() -> Unit)