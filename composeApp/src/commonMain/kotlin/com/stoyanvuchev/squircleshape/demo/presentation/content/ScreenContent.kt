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

import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyGridScope
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.ui.unit.dp
import com.stoyanvuchev.squircleshape.demo.core.ui.component.code.CodeBlock
import com.stoyanvuchev.squircleshape.demo.core.ui.component.layout.spacer.VerticalSpacer
import com.stoyanvuchev.squircleshape.demo.core.ui.component.topbar.topBarStickyHeader

fun LazyGridScope.screenContent(
    pair: () -> Pair<LazyGridState, List<ContentGroup>>
) {

    topBarStickyHeader { pair().first }

    pair().second.forEachIndexed { groupIndex, group ->

        items(
            items = group.pieces,
            key = { piece ->
                "${group.key}-" + when (piece) {
                    is ContentPiece.Custom -> piece.key
                    is ContentPiece.Spacer -> piece.key
                    else -> piece.hashCode().toString()
                }
            },
            span = { piece ->
                GridItemSpan(
                    currentLineSpan = when (piece) {

                        is ContentPiece.Title,
                        is ContentPiece.BodyText,
                        is ContentPiece.Spacer,
                        is ContentPiece.CTA -> this.maxLineSpan

                        is ContentPiece.Card,
                        is ContentPiece.Author,
                        is ContentPiece.LinksSection -> 1

                        is ContentPiece.Code -> this.maxLineSpan.coerceAtMost(2)
                        is ContentPiece.Custom -> piece.span ?: this.maxLineSpan

                    }
                )
            },
            contentType = { it::class.simpleName },
            itemContent = { piece ->
                when (piece) {
                    is ContentPiece.Title -> SectionTitle(title = piece.title)
                    is ContentPiece.BodyText -> BodyText(text = piece.text)
                    is ContentPiece.Code -> CodeBlock(code = piece.code)
                    is ContentPiece.Custom -> piece.content()
                    is ContentPiece.Spacer -> VerticalSpacer(height = piece.height)
                    is ContentPiece.Card -> Card(card = piece)
                    is ContentPiece.CTA -> CTA(cta = piece)
                    is ContentPiece.Author -> AuthorContent(author = piece)
                    is ContentPiece.LinksSection -> LinksContent(section = piece)
                }
            }
        )

        if (groupIndex < pair().second.lastIndex) {
            item(
                span = { GridItemSpan(maxLineSpan) },
                key = "divider-${group.key}",
                content = { SectionDivider() }
            )
        }

    }

    item(
        key = "bottom-spacer",
        span = { GridItemSpan(maxLineSpan) },
        content = { VerticalSpacer(height = 60.dp) }
    )

}