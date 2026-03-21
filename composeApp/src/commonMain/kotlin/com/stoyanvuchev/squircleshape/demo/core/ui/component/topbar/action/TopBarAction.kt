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

package com.stoyanvuchev.squircleshape.demo.core.ui.component.topbar.action

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.RowScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import com.stoyanvuchev.squircleshape.demo.core.ui.component.icon.AsyncIcon
import com.stoyanvuchev.squircleshape.demo.core.ui.component.topbar.TopBar
import com.stoyanvuchev.squircleshape.demo.core.ui.component.topbar.action.TopBarActionUtils.topBarActionContainerModifier
import com.stoyanvuchev.squircleshape.demo.core.ui.component.topbar.action.TopBarActionUtils.topBarActionIconModifier

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
    icon: () -> Any?,
    onClick: () -> Unit,
    contentDescription: String?
) = Box(
    modifier = Modifier.topBarActionContainerModifier(onClick = onClick),
    contentAlignment = Alignment.Center,
    content = {

        AsyncIcon(
            icon = icon,
            modifier = Modifier.topBarActionIconModifier(),
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