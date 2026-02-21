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

package com.stoyanvuchev.squircleshape.demo.core.ui.component.bottombar

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
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.dropShadow
import androidx.compose.ui.draw.innerShadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.shadow.Shadow
import androidx.compose.ui.input.pointer.PointerIcon
import androidx.compose.ui.input.pointer.pointerHoverIcon
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import com.stoyanvuchev.squircleshape.demo.core.ui.LocalHazeState
import com.stoyanvuchev.squircleshape.demo.core.ui.Theme
import com.stoyanvuchev.squircleshape.demo.core.ui.component.interaction.rememberRipple
import dev.chrisbanes.haze.HazeStyle
import dev.chrisbanes.haze.HazeTint
import dev.chrisbanes.haze.hazeEffect

/**
 * Useful utilities for a bottom bar UI components.
 */
object BottomBarUtils {

    /**
     * Returns a [WindowInsets] instance for the bottom and horizontal safe drawing areas.
     *
     * @return A [WindowInsets] representing the requested sides of the safe drawing area.
     */
    @Composable
    internal fun windowInsets(): WindowInsets {
        return WindowInsets.safeDrawing
            .only(
                sides = WindowInsetsSides.Bottom +
                        WindowInsetsSides.Horizontal
            )
    }

    /**
     * The container height of the bottom bar.
     */
    @Composable
    internal fun containerHeight(): Dp {
        return 72.dp + with(LocalDensity.current) {
            windowInsets().getBottom(this).toDp()
        }
    }

    internal fun Modifier.bottomBarBlurModifier(): Modifier {
        return composed {
            Modifier
                .dropShadow(
                    shape = Theme.rangedShape,
                    shadow = Shadow(
                        radius = 24.dp,
                        color = Theme.colorScheme.surface.copy(.5f),
                        offset = DpOffset(x = 0.dp, y = 4.dp)
                    )
                )
                .clip(Theme.rangedShape)
                .hazeEffect(
                    state = LocalHazeState.current,
                    style = HazeStyle(
                        backgroundColor = Theme.colorScheme.surface,
                        tint = HazeTint(
                            color = Theme.colorScheme.surfaceElevationMedium.copy(.5f)
                        ),
                        blurRadius = 12.dp
                    )
                )
                .innerShadow(
                    shape = Theme.rangedShape,
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
                    shape = Theme.rangedShape,
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
                            Theme.colorScheme.outline,
                            Theme.colorScheme.outline.copy(0f)
                        )
                    ),
                    shape = Theme.rangedShape
                )
        }
    }

    internal fun Modifier.bottomBarActionModifier(
        onClick: () -> Unit
    ): Modifier {
        return composed {

            val interactionSource = remember { MutableInteractionSource() }
            val isHovered by interactionSource.collectIsHoveredAsState()
            val isFocused by interactionSource.collectIsHoveredAsState()
            val isPressed by interactionSource.collectIsPressedAsState()

            val contentPadding by animateDpAsState(
                targetValue = when {
                    isPressed -> 3.dp
                    isFocused -> 1.5.dp
                    isHovered -> 1.5.dp
                    else -> 0.dp
                },
                animationSpec = spring()
            )

            val borderColor by animateColorAsState(
                targetValue = when {
                    isPressed -> Theme.colorScheme.outline.copy(.5f)
                    isHovered -> Theme.colorScheme.outline.copy(.33f)
                    isFocused -> Theme.colorScheme.outline.copy(.33f)
                    else -> Theme.colorScheme.outline
                },
                animationSpec = spring()
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
                .clip(Theme.rangedShape)
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
                    shape = Theme.rangedShape,
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
                    shape = Theme.rangedShape,
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
                    shape = Theme.rangedShape
                )

        }
    }

}