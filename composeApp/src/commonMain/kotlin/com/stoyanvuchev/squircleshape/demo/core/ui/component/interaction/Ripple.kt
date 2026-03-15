/*
 * Copyright 2025 Assertive UI (assertiveui.com)
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

package com.stoyanvuchev.squircleshape.demo.core.ui.component.interaction

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.IndicationNodeFactory
import androidx.compose.foundation.interaction.HoverInteraction
import androidx.compose.foundation.interaction.InteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.geometry.center
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.ContentDrawScope
import androidx.compose.ui.node.DelegatableNode
import androidx.compose.ui.node.DrawModifierNode
import com.stoyanvuchev.squircleshape.demo.core.ui.Theme
import com.stoyanvuchev.squircleshape.demo.core.ui.color.LocalBackgroundColor
import com.stoyanvuchev.squircleshape.demo.core.ui.color.inverseColor
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

/**
 * Creates and remembers a [Ripple] indication that provides a radial ripple-like feedback
 * effect in response to user interactions such as press, release, hover, and cancel.
 *
 * By default, the ripple color is derived from the current [Theme.colorScheme]
 * using an inverted background color, but a custom [color] may also be supplied.
 *
 * This function should be used in modifier chains that expect an [IndicationNodeFactory],
 * typically in clickable or interactive components.
 *
 * Example:
 * ```
 * Box(
 *     modifier = Modifier
 *         .size(64.dp)
 *         .indication(
 *             interactionSource = remember { MutableInteractionSource() },
 *             indication = rememberRipple()
 *         )
 *         .clickable { /* Handle click */  }
 * )
 * ```
 *
 * @param color The ripple color. Defaults to an inverse of the current [LocalBackgroundColor].
 * @return A remembered [Ripple] instance.
 */
@Composable
fun rememberRipple(
    color: Color = Theme.colorScheme.inverseColor(LocalBackgroundColor.current)
): Ripple {
    val ripple by rememberUpdatedState(Ripple(color))
    return ripple
}

/**
 * A ripple indication used to visually represent user interactions (press, hover, release, etc.)
 * by drawing an animated radial gradient circle behind the component's content.
 *
 * The ripple effect is implemented with spring-based animations for both opacity (`alpha`)
 * and expansion factor (`alphaFactor`), making it feel responsive and fluid.
 *
 * The ripple responds to the following interactions collected from [InteractionSource]:
 * - [PressInteraction.Press] → Expands and increases opacity.
 * - [PressInteraction.Release], [PressInteraction.Cancel], [HoverInteraction.Exit] → Fades out and contracts.
 * - [HoverInteraction.Enter] → Shows a subtle hover highlight centered on the component.
 *
 * Internally, ripple animations are delegated to a [ScaleIndicationNode], which integrates
 * with the Compose node system as a [DrawModifierNode].
 *
 * @property color The base color of the ripple, with alpha values animated dynamically.
 *
 * @see rememberRipple
 */
@Stable
class Ripple(
    private val color: Color
) : IndicationNodeFactory {

    /**
     * Creates a new [DelegatableNode] responsible for managing interaction state and
     * drawing the ripple effect.
     *
     * @param interactionSource The [InteractionSource] used to observe user interactions.
     */
    override fun create(
        interactionSource: InteractionSource
    ): DelegatableNode = ScaleIndicationNode(
        interactionSource = interactionSource,
        color = color
    )

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || other !is Ripple) return false
        if (color != other.color) return false
        return true
    }

    override fun hashCode(): Int = color.hashCode()

    /**
     * A private [DrawModifierNode] responsible for rendering and animating the ripple effect.
     *
     * The ripple is drawn as a radial gradient circle, with its position and radius
     * animated based on the last known press or hover interaction.
     *
     * Animations include:
     * - [animateToPressed]: Expands ripple at press location with increased opacity.
     * - [animateToHovered]: Expands ripple slightly at center with low opacity.
     * - [animateToResting]: Fades ripple out and resets radius factor.
     *
     * The node subscribes to the [interactionSource] lifecycle in [onAttach] and reacts
     * in real-time to emitted [InteractionSource.interactions].
     */
    private class ScaleIndicationNode(
        private val interactionSource: InteractionSource,
        private val color: Color
    ) : Modifier.Node(), DrawModifierNode {

        var drawSize = Size.Zero
        var currentPressPositionX = Animatable(drawSize.center.x)
        var currentPressPositionY = Animatable(drawSize.center.y)

        var alpha = Animatable(0f)
        var alphaFactor = Animatable(.1f)

        private suspend fun animateToPressed(
            pressPosition: Offset
        ) = coroutineScope {
            launch { currentPressPositionX.animateTo(pressPosition.x) }
            launch { currentPressPositionY.animateTo(pressPosition.y) }
            launch {
                alpha.animateTo(
                    .4f,
                    spring(
                        dampingRatio = Spring.DampingRatioNoBouncy,
                        stiffness = Spring.StiffnessLow
                    )
                )
            }
            launch {
                alphaFactor.animateTo(
                    .5f,
                    spring(
                        dampingRatio = Spring.DampingRatioNoBouncy,
                        stiffness = Spring.StiffnessLow
                    )
                )
            }
        }

        private suspend fun animateToHovered() = coroutineScope {
            launch { currentPressPositionX.animateTo(drawSize.center.x) }
            launch { currentPressPositionY.animateTo(drawSize.center.y) }
            launch {
                alpha.animateTo(
                    .075f,
                    spring(
                        dampingRatio = Spring.DampingRatioNoBouncy,
                        stiffness = Spring.StiffnessLow
                    )
                )
            }
            launch {
                alphaFactor.animateTo(
                    .67f,
                    spring(
                        dampingRatio = Spring.DampingRatioNoBouncy,
                        stiffness = Spring.StiffnessLow
                    )
                )
            }
        }

        private suspend fun animateToResting() = coroutineScope {
            launch {
                alpha.animateTo(
                    0f,
                    spring(
                        dampingRatio = Spring.DampingRatioNoBouncy,
                        stiffness = Spring.StiffnessLow
                    )
                )
            }
            launch {
                alphaFactor.animateTo(
                    .1f,
                    spring(
                        dampingRatio = Spring.DampingRatioNoBouncy,
                        stiffness = Spring.StiffnessLow
                    )
                )
            }
        }

        override fun onAttach() {
            coroutineScope.launch {
                interactionSource.interactions.collectLatest { interaction ->
                    when (interaction) {
                        is PressInteraction.Press -> animateToPressed(interaction.pressPosition)
                        is PressInteraction.Release -> animateToResting()
                        is PressInteraction.Cancel -> animateToResting()
                        is HoverInteraction.Exit -> animateToResting()
                        is HoverInteraction.Enter -> animateToHovered()
                    }
                }
            }
        }

        override fun ContentDrawScope.draw() {
            this@draw.drawContent()
            drawSize = lazy { this@draw.size }.value
            drawCircle(
                brush = Brush.radialGradient(
                    colors = listOf(
                        color.copy(alpha.value),
                        color.copy(0f)
                    ),
                    center = Offset(
                        currentPressPositionX.value,
                        currentPressPositionY.value
                    ),
                    radius = this.size.maxDimension * alphaFactor.value
                ),
                radius = this.size.maxDimension,
                center = Offset(
                    currentPressPositionX.value,
                    currentPressPositionY.value
                )
            )
        }

    }

}