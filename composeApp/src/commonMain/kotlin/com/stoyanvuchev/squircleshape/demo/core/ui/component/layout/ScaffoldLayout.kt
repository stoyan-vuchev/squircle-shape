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

package com.stoyanvuchev.squircleshape.demo.core.ui.component.layout

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.UiComposable
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.SubcomposeLayout
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.offset
import com.stoyanvuchev.squircleshape.demo.core.ui.Theme
import com.stoyanvuchev.squircleshape.demo.core.ui.color.LocalBackgroundColor
import com.stoyanvuchev.squircleshape.demo.core.ui.color.LocalContentColor
import com.stoyanvuchev.squircleshape.demo.core.ui.color.inverseColor
import com.stoyanvuchev.squircleshape.demo.core.ui.util.window.LocalWindowState

@Composable
@UiComposable
fun ScaffoldLayout(
    modifier: Modifier = Modifier,
    topBar: @Composable @UiComposable (() -> Unit)? = null,
    bottomBar: @Composable @UiComposable (() -> Unit)? = null,
    sideRail: @Composable @UiComposable () -> Unit = {},
    surfaceColor: Color = LocalBackgroundColor.current,
    contentColor: Color = Theme.colorScheme.inverseColor(surfaceColor),
    contentWindowInsets: WindowInsets = WindowInsets.safeDrawing,
    content: @Composable @UiComposable (PaddingValues) -> Unit
) = BoxWithConstraints(
    modifier = Modifier
        .background(surfaceColor)
        .then(modifier),
    content = {

        CompositionLocalProvider(
            LocalContentColor provides contentColor,
            content = {

                val isCompactWidth = LocalWindowState.current.isCompactWidth

                FoundationLayoutImpl(
                    topBar = topBar,
                    bottomBar = if (isCompactWidth) bottomBar else remember { {} },
                    sideRail = if (!isCompactWidth) sideRail else remember { {} },
                    content = content,
                    contentWindowInsets = contentWindowInsets,
                )

            }
        )

    }
)

@Composable
@UiComposable
private fun FoundationLayoutImpl(
    topBar: @Composable (() -> Unit)?,
    bottomBar: @Composable (() -> Unit)?,
    sideRail: @Composable () -> Unit,
    content: @Composable (PaddingValues) -> Unit,
    contentWindowInsets: WindowInsets
) = SubcomposeLayout { constraints ->

    val layoutWidth = constraints.maxWidth
    val layoutHeight = constraints.maxHeight
    val looseConstraints = constraints.copy(minWidth = 0, minHeight = 0)

    layout(
        width = layoutWidth,
        height = layoutHeight
    ) {

        // Defining side rail placeables.
        val sideRailPlaceables = subcompose(
            slotId = ResponsiveScaffoldLayoutContent.SideRail,
            content = sideRail
        ).map { it.measure(looseConstraints) }

        val sideRailWidth = sideRailPlaceables.maxByOrNull { it.width }?.width
        val sideRailHeight = sideRailPlaceables.maxByOrNull { it.height }?.height

        // Defining top bar placeables & height.
        val topBarPlaceables = subcompose(
            slotId = ResponsiveScaffoldLayoutContent.TopBar,
            content = topBar ?: {}
        ).map {

            val leftInset = if (
                layoutDirection == LayoutDirection.Ltr
                && sideRailWidth != null
            ) sideRailWidth else 0

            val rightInset = if (
                layoutDirection == LayoutDirection.Rtl
                && sideRailWidth != null
            ) sideRailWidth else 0

            it.measure(
                constraints = looseConstraints.offset(
                    horizontal = (-leftInset) - rightInset,
                )
            )

        }
        val topBarHeight = topBarPlaceables.maxByOrNull { it.height }?.height ?: 0

        // Defining bottom bar placeables & height.
        val bottomBarPlaceables = subcompose(
            slotId = ResponsiveScaffoldLayoutContent.BottomBar,
            content = bottomBar ?: {}
        ).map { it.measure(looseConstraints) }
        val bottomBarHeight = bottomBarPlaceables.maxByOrNull { it.height }?.height ?: 0

        // Defining body content placeables.
        val bodyContentPlaceables = subcompose(
            ResponsiveScaffoldLayoutContent.MainContent
        ) {

            val insets = contentWindowInsets.asPaddingValues(this@SubcomposeLayout)
            val innerPadding = PaddingValues(
                top = if (topBarPlaceables.isEmpty()) insets.calculateTopPadding()
                else topBarHeight.toDp(),
                bottom = if (bottomBarPlaceables.isEmpty()) {
                    insets.calculateBottomPadding()
                } else bottomBarHeight.toDp(),
                start = if (sideRailPlaceables.isEmpty() || sideRailWidth == null) {
                    insets.calculateStartPadding((this@SubcomposeLayout).layoutDirection)
                } else sideRailWidth.toDp(),
                end = insets.calculateEndPadding((this@SubcomposeLayout).layoutDirection)
            )

            Box(
                modifier = Modifier
                    .graphicsLayer { this.alpha = .99f }
                    .fillMaxSize()
                    .drawWithContent {

                        // Draw the body content.
                        drawContent()

                        // Apply fading edge for the top bar.
                        drawRect(
                            brush = Brush.verticalGradient(
                                colors = listOf(
                                    Color.Black.copy(alpha = .0f),
                                    Color.Black.copy(alpha = .2f),
                                    Color.Black
                                ),
                                startY = 0f,
                                endY = topBarHeight.toFloat() + 32.dp.toPx()
                            ),
                            blendMode = BlendMode.DstIn
                        )

                        // Apply fading edge for the bottom bar.
                        drawRect(
                            brush = Brush.verticalGradient(
                                colors = listOf(
                                    Color.Black,
                                    Color.Black.copy(alpha = .2f),
                                    Color.Black.copy(alpha = .0f)
                                ),
                                startY = layoutHeight.toFloat() -
                                        (bottomBarHeight.toFloat() + 32.dp.toPx()),
                                endY = layoutHeight.toFloat()
                            ),
                            blendMode = BlendMode.DstIn
                        )

                    },
                content = { content(innerPadding) }
            )

        }.map { it.measure(looseConstraints) }

        /*
         * Defining each content placeables position offset.
         */

        // Defining the top bar placeables position offset.
        val topBarPlaceablesOffset = IntOffset(
            x = if (layoutDirection == LayoutDirection.Ltr) sideRailWidth ?: 0 else 0,
            y = 0
        )

        // Defining the bottom bar placeables position offset.
        val bottomBarPlaceablesPositionOffset = IntOffset(
            x = 0,
            y = layoutHeight - bottomBarHeight
        )

        // Defining the side rail placeables position offset.
        val sideRailPlaceablesPositionOffset = IntOffset(
            x = if (layoutDirection == LayoutDirection.Ltr) 0
            else layoutWidth - (sideRailWidth ?: 0),
            y = (layoutHeight / 2) - ((sideRailHeight?.div(2)) ?: 0)
        )

        /*
         * Placing the placeables by maintaining the layout hierarchy:
         * MainContent >> TopBar >> BottomBar >> SideRail
         */

        // Placing the body content placeables.
        bodyContentPlaceables.forEach { it.place(position = IntOffset.Zero) }

        // Placing the top bar placeables.
        topBarPlaceables.forEach { it.place(position = topBarPlaceablesOffset) }

        // Placing the bottom bar placeables.
        bottomBarPlaceables.forEach { it.place(position = bottomBarPlaceablesPositionOffset) }

        // Placing the side rail placeables.
        sideRailPlaceables.forEach { it.place(position = sideRailPlaceablesPositionOffset) }

    }

}

/** Constants defining [ScaffoldLayout]'s content hierarchy slots. */
private enum class ResponsiveScaffoldLayoutContent {
    MainContent, TopBar, BottomBar, SideRail;
}