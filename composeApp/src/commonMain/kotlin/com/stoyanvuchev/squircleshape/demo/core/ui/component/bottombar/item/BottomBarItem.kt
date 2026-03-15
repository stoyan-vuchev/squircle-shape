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

package com.stoyanvuchev.squircleshape.demo.core.ui.component.bottombar.item

import androidx.compose.animation.animateColor
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.input.pointer.PointerIcon
import androidx.compose.ui.input.pointer.pointerHoverIcon
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.stoyanvuchev.squircleshape.demo.core.ui.Theme
import com.stoyanvuchev.squircleshape.demo.core.ui.component.interaction.rememberRipple
import com.stoyanvuchev.squircleshape.demo.core.ui.component.text.Text

@Composable
fun RowScope.BottomBarItem(
    modifier: Modifier = Modifier,
    icon: @Composable () -> Painter,
    label: String? = null,
    selected: Boolean,
    onSelected: () -> Unit
) {

    val transition = updateTransition(
        targetState = selected
    )

    val contentColor by transition.animateColor(
        targetValueByState = { isSelected ->
            if (isSelected) Theme.colorScheme.primary
            else Theme.colorScheme.onSurface
        },
        transitionSpec = {
            spring(
                dampingRatio = Spring.DampingRatioNoBouncy,
                stiffness = Spring.StiffnessMediumLow
            )
        }
    )

    Row(
        modifier = Modifier
            .pointerHoverIcon(icon = PointerIcon.Hand)
            .padding(4.dp)
            .fillMaxHeight()
            .weight(1f)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = rememberRipple(),
                onClick = onSelected,
                role = Role.Tab
            )
            .padding(start = 12.dp, end = 16.dp)
            .then(modifier),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp, Alignment.CenterHorizontally)
    ) {

        Image(
            modifier = Modifier.size(24.dp),
            painter = icon(),
            colorFilter = ColorFilter.tint(color = contentColor),
            contentDescription = null
        )

        Text(
            text = label ?: "",
            color = contentColor,
            style = Theme.typography.label,
            overflow = TextOverflow.Ellipsis,
            maxLines = 1
        )

    }

}