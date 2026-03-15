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

package com.stoyanvuchev.squircleshape.demo.core.ui.util.window

import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.State
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import com.stoyanvuchev.squircleshape.demo.core.ui.util.window.ScreenOrientation.Companion.isPortrait

/**
 *
 * A [WindowState] empowers the ability to implement adaptive UIs in your app,
 * simply by evaluating the width and the height of a root composable layout.
 *
 * @param rootSize The [DpSize] of the root composable layout (e.g. [BoxWithConstraints]).
 *
 */
@Stable
@Composable
fun rememberWindowState(rootSize: DpSize): State<WindowState> {
    return rememberUpdatedState(
        WindowState(
            availableWidthSize = when {
                rootSize.width < 480.dp -> WindowSize.Compact
                rootSize.width < 900.dp -> WindowSize.Medium
                else -> WindowSize.Large
            },
            availableHeightSize = when {
                rootSize.height < 480.dp -> WindowSize.Compact
                rootSize.height < 900.dp -> WindowSize.Medium
                else -> WindowSize.Large
            },
            availableWidthDp = rootSize.width,
            availableHeightDp = rootSize.height
        )
    )
}

/**
 * A definition of the available width / height area size for a window.
 */
enum class WindowSize {

    /**
     * A constant that represents a small width / height area size.
     */
    Compact,

    /**
     * A constant that represents a medium width / height area size.
     */
    Medium,

    /**
     * A constant that represents a large width / height area size.
     */
    Large;

}

/**
 *
 * A definition of the form factor for a window, used for implementing
 * responsive UIs in the app.
 *
 * ```
 * @Composable
 * fun AppContent() = when (LocalWindowState.current.formFactor) {
 *
 *     // Phone in portrait orientation.
 *     WindowFormFactor.PhoneInPortrait -> {}
 *
 *     // Phone in landscape orientation.
 *     WindowFormFactor.PhoneInLandscape -> {}
 *
 *     // Foldable / Small Tablet in portrait orientation.
 *     WindowFormFactor.FoldableTabletPortrait -> {}
 *
 *     // Foldable / Small Tablet in landscape orientation.
 *     WindowFormFactor.FoldableTabletLandscape -> {}
 *
 *     // Desktop / Large Tablet in portrait orientation.
 *     WindowFormFactor.DesktopPortrait -> {}
 *
 *     // Desktop / Large Tablet in landscape orientation.
 *     WindowFormFactor.DesktopLandscape -> {}
 *
 *     else -> {}
 *
 * }
 * ```
 *
 */
enum class WindowFormFactor {

    /**
     * A constant that represents a small window in portrait screen orientation (possibly a phone).
     */
    PhoneInPortrait,

    /**
     * A constant that represents a small window in landscape screen orientation (possibly a phone).
     */
    PhoneInLandscape,

    /**
     * A constant that represents a medium-sized window in portrait screen orientation (possibly a foldable, or small tablet).
     */
    FoldableTabletPortrait,

    /**
     * A constant that represents a medium-sized window in landscape screen orientation (possibly a foldable, or small tablet).
     */
    FoldableTabletLandscape,

    /**
     * A constant that represents a large window in portrait screen orientation (possibly a large tablet, or a desktop).
     */
    DesktopPortrait,

    /**
     * A constant that represents a large window in portrait screen orientation (possibly a large tablet, or a desktop).
     */
    DesktopLandscape;

}

/**
 * A definition of the screen orientation for a window.
 */
enum class ScreenOrientation {

    /**
     * A constant that represents a portrait (width < height) screen orientation.
     */
    Portrait,

    /**
     * A constant that represents a square (width = height) screen orientation.
     */
    Square,

    /**
     * A constant that represents a landscape (width > height) screen orientation.
     */
    Landscape;

    companion object {

        /**
         * Returns true if the current screen orientation is [Portrait] or [Square],
         * false otherwise.
         */
        val ScreenOrientation.isPortrait get() = this == Portrait || this == Square

    }

}

/**
 * Contains the information about the available width & height in [Dp] and [WindowSize] values,
 * with additional functionality.
 */
@Immutable
class WindowState(
    val availableWidthSize: WindowSize,
    val availableHeightSize: WindowSize,
    val availableWidthDp: Dp,
    val availableHeightDp: Dp
) {

    val isCompactWidth get() = availableWidthSize == WindowSize.Compact
    val isMediumWidth get() = availableWidthSize == WindowSize.Medium
    val isLargeWidth get() = availableWidthSize == WindowSize.Large

    val isCompactHeight get() = availableHeightSize == WindowSize.Compact
    val isMediumHeight get() = availableHeightSize == WindowSize.Medium
    val isLargeHeight get() = availableHeightSize == WindowSize.Large

    /**
     * The current form factor of the window.
     */
    val formFactor: WindowFormFactor
        get() = when {

            isCompactWidth -> WindowFormFactor.PhoneInPortrait

            isMediumWidth && isCompactHeight
                    || isLargeWidth && isCompactHeight -> WindowFormFactor.PhoneInLandscape

            isMediumWidth && isMediumHeight
                    && screenOrientation.isPortrait -> WindowFormFactor.FoldableTabletPortrait

            isMediumWidth && isMediumHeight
                    && !screenOrientation.isPortrait
                    || isLargeWidth && isMediumHeight -> WindowFormFactor.FoldableTabletLandscape

            isLargeWidth && isLargeHeight
                    && screenOrientation.isPortrait -> WindowFormFactor.DesktopPortrait

            isLargeWidth && isCompactHeight
                    || isLargeWidth && isMediumHeight
                    || isLargeWidth && isLargeHeight -> WindowFormFactor.DesktopLandscape

            else -> WindowFormFactor.PhoneInLandscape

        }

    /**
     * The current screen orientation.
     */
    val screenOrientation: ScreenOrientation
        get() = when {
            availableWidthDp < availableHeightDp -> ScreenOrientation.Portrait
            availableWidthDp > availableHeightDp -> ScreenOrientation.Landscape
            else -> ScreenOrientation.Square
        }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || other !is WindowState) return false
        if (availableWidthSize != other.availableWidthSize) return false
        if (availableHeightSize != other.availableHeightSize) return false
        if (availableWidthDp != other.availableWidthDp) return false
        if (availableHeightDp != other.availableHeightDp) return false
        return true
    }

    override fun hashCode(): Int {
        var result = availableWidthSize.hashCode()
        result += availableHeightSize.hashCode()
        result += availableWidthDp.hashCode()
        result += availableHeightDp.hashCode()
        return result
    }

    override fun toString(): String {
        return "WindowState(" +
                "screenWidthSize=$availableWidthSize, " +
                "screenHeightSize=$availableHeightSize, " +
                "screenWidthDp=$availableWidthDp, " +
                "screenHeightDp=$availableHeightDp" +
                ")"
    }

}

/**
 * A CompositionLocal key used for providing a [WindowState] to the UI.
 */
val LocalWindowState = compositionLocalOf<WindowState> {
    error("CompositionLocal LocalWindowState not provided.")
}