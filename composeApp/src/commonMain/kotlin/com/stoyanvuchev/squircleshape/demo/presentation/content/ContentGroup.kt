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
import androidx.compose.runtime.Stable
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.DrawableResource
import squircleshape.composeapp.generated.resources.Res
import squircleshape.composeapp.generated.resources.github_icon
import squircleshape.composeapp.generated.resources.linkedin_square_icon

@Stable
data class ContentGroup(
    val key: String,
    val pieces: List<ContentPiece>
)

fun group(
    key: String,
    builder: GroupBuilder.() -> Unit
): ContentGroup {
    val pieces = mutableListOf<ContentPiece>()
    GroupBuilder(pieces).apply(builder)
    return ContentGroup(key, pieces.toList())
}

class GroupBuilder(
    private val pieces: MutableList<ContentPiece>
) {

    fun title(title: String) {
        pieces.add(ContentPiece.Title(title))
    }

    fun text(text: String) {
        pieces.add(ContentPiece.BodyText(text))
    }

    fun spacer(
        key: String,
        height: Dp = 20.dp
    ) {
        pieces.add(
            ContentPiece.Spacer(
                key = key,
                height = height
            )
        )
    }

    fun code(code: String) {
        pieces.add(ContentPiece.Code(code))
    }

    fun composable(
        key: String,
        span: Int? = null,
        content: @Composable () -> Unit
    ) {
        pieces.add(
            ContentPiece.Custom(
                key = key,
                span = span,
                content = content
            )
        )
    }

    fun card(
        icon: DrawableResource,
        label: String,
        text: String
    ) {
        pieces.add(
            ContentPiece.Card(
                icon = icon,
                label = label,
                text = text
            )
        )
    }

    fun cta(
        title: String,
        label: String,
        onAction: () -> Unit
    ) {
        pieces.add(
            ContentPiece.CTA(
                title = title,
                label = label,
                onAction = onAction
            )
        )
    }

    fun author(
        name: String,
        avatarRes: DrawableResource,
        block: AuthorBuilder.() -> Unit
    ) {
        val links = mutableListOf<ContentPiece.Link>()
        AuthorBuilder(links).apply(block)
        pieces.add(
            ContentPiece.Author(
                name = name,
                avatarRes = avatarRes,
                links = links.toList()
            )
        )
    }

    fun links(
        title: String,
        block: LinksBuilder.() -> Unit
    ) {
        val links = mutableListOf<ContentPiece.Link>()
        LinksBuilder(links).apply(block)
        pieces.add(
            ContentPiece.LinksSection(
                links = links.toList(),
                title = title
            )
        )
    }

}

class AuthorBuilder(
    private val links: MutableList<ContentPiece.Link>
) {

    fun github(
        url: String,
        label: String = "GitHub Profile"
    ) {
        links.add(
            ContentPiece.Link(
                label = label,
                url = url,
                iconRes = Res.drawable.github_icon
            )
        )
    }

    fun linkedIn(
        url: String,
        label: String = "LinkedIn Profile"
    ) {
        links.add(
            ContentPiece.Link(
                label = label,
                url = url,
                iconRes = Res.drawable.linkedin_square_icon
            )
        )
    }

}

class LinksBuilder(
    private val links: MutableList<ContentPiece.Link>
) {

    fun item(
        label: String,
        url: String,
        icon: DrawableResource,
        iconPaddingDp: Dp = 0.dp
    ) {
        links.add(
            ContentPiece.Link(
                label = label,
                url = url,
                iconRes = icon,
                iconPaddingDp = iconPaddingDp
            )
        )
    }

}