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

package com.stoyanvuchev.squircleshape.demo.core.ui.component.siderail

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsHoveredAsState
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.dropShadow
import androidx.compose.ui.draw.innerShadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.shadow.Shadow
import androidx.compose.ui.input.pointer.PointerIcon
import androidx.compose.ui.input.pointer.pointerHoverIcon
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import com.stoyanvuchev.squircleshape.demo.core.ui.LocalHazeState
import com.stoyanvuchev.squircleshape.demo.core.ui.Theme
import com.stoyanvuchev.squircleshape.demo.core.ui.component.interaction.rememberRipple
import dev.chrisbanes.haze.HazeStyle
import dev.chrisbanes.haze.HazeTint
import dev.chrisbanes.haze.hazeEffect

/**
 * Useful utilities for a side rail UI components.
 */
object SideRailUtils {

    /**
     * Returns a [WindowInsets] instance for the bottom and horizontal safe drawing areas.
     *
     * @return A [WindowInsets] representing the requested sides of the safe drawing area.
     */
    @Composable
    internal fun windowInsets(): WindowInsets {
        return WindowInsets.safeDrawing
            .only(
                sides = WindowInsetsSides.Vertical +
                        WindowInsetsSides.Start
            )
    }

    internal fun Modifier.sideRailActionModifier(
        onClick: () -> Unit
    ): Modifier {
        return composed {

            val interactionSource = remember { MutableInteractionSource() }
            val isHovered by interactionSource.collectIsHoveredAsState()
            val isFocused by interactionSource.collectIsHoveredAsState()
            val isPressed by interactionSource.collectIsPressedAsState()

            val contentPadding by animateDpAsState(
                targetValue = rememberUpdatedState(
                    when {
                        isPressed -> 3.dp
                        isFocused -> 1.5.dp
                        isHovered -> 1.5.dp
                        else -> 0.dp
                    }
                ).value,
                animationSpec = remember { spring() }
            )

            val borderColor by animateColorAsState(
                targetValue = rememberUpdatedState(
                    when {
                        isPressed -> Theme.colorScheme.outline.copy(.5f)
                        isHovered -> Theme.colorScheme.outline.copy(.33f)
                        isFocused -> Theme.colorScheme.outline.copy(.33f)
                        else -> Theme.colorScheme.outline
                    }
                ).value,
                animationSpec = remember { spring() }
            )

            Modifier
                .pointerHoverIcon(
                    icon = PointerIcon.Hand
                )
                .clickable(
                    onClick = onClick,
                    interactionSource = interactionSource,
                    indication = rememberRipple()
                )
                .padding(contentPadding)
                .dropShadow(
                    shape = Theme.shapes.medium,
                    shadow = Shadow(
                        radius = 24.dp,
                        color = Theme.colorScheme.shadow,
                        offset = DpOffset(x = 0.dp, y = 4.dp)
                    )
                )
                .clip(Theme.shapes.medium)
                .hazeEffect(
                    state = LocalHazeState.current,
                    style = HazeStyle(
                        backgroundColor = Theme.colorScheme.surface,
                        tint = HazeTint(
                            color = Theme.colorScheme.surfaceElevationHigh.copy(.5f)
                        ),
                        blurRadius = 12.dp
                    )
                )
                .innerShadow(
                    shape = Theme.shapes.medium,
                    shadow = Shadow(
                        radius = 16.dp,
                        brush = Brush.verticalGradient(
                            colors = listOf(
                                Theme.colorScheme.surface.copy(.5f),
                                Theme.colorScheme.surface.copy(0f)
                            )
                        ),
                        offset = DpOffset(x = 0.dp, y = 2.dp)
                    )
                )
                .innerShadow(
                    shape = Theme.shapes.medium,
                    shadow = Shadow(
                        radius = 16.dp,
                        brush = Brush.verticalGradient(
                            colors = listOf(
                                Theme.colorScheme.surfaceElevationHigh.copy(0f),
                                Theme.colorScheme.surfaceElevationHigh
                            )
                        ),
                        offset = DpOffset(x = 0.dp, y = (-2).dp)
                    )
                )
                .border(
                    width = 1.dp,
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            borderColor,
                            borderColor.copy(0f)
                        )
                    ),
                    shape = Theme.shapes.medium
                )

        }
    }

}

val LocalIsSideRailPresent = staticCompositionLocalOf { false }