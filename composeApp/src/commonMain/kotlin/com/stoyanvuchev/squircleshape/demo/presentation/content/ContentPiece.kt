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

package com.stoyanvuchev.squircleshape.demo.presentation.content

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.DrawableResource

@Immutable
sealed interface ContentPiece {

    data class Title(val title: String) : ContentPiece
    data class BodyText(val text: String) : ContentPiece
    data class Code(val code: String) : ContentPiece

    data class Custom(
        val key: String,
        val span: Int? = null,
        val content: @Composable () -> Unit
    ) : ContentPiece

    data class Spacer(
        val key: String,
        val height: Dp
    ) : ContentPiece

    data class Card(
        val icon: DrawableResource,
        val label: String,
        val text: String
    ) : ContentPiece

    data class CTA(
        val title: String,
        val label: String,
        val onAction: () -> Unit
    ) : ContentPiece

    data class Author(
        val name: String,
        val avatarRes: DrawableResource,
        val links: List<Link> = emptyList()
    ) : ContentPiece

    data class LinksSection(
        val links: List<Link>,
        val title: String
    ) : ContentPiece

    data class Link(
        val label: String,
        val url: String,
        val iconRes: DrawableResource,
        val iconPaddingDp: Dp = 0.dp
    )

}