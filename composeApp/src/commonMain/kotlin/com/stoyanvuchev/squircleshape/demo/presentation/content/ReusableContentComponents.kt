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

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.stoyanvuchev.squircleshape.demo.core.ui.Theme
import com.stoyanvuchev.squircleshape.demo.core.ui.color.LocalContentColor
import com.stoyanvuchev.squircleshape.demo.core.ui.component.button.Button
import com.stoyanvuchev.squircleshape.demo.core.ui.component.cardBackgroundModifier
import com.stoyanvuchev.squircleshape.demo.core.ui.component.layout.spacer.HorizontalSpacer
import com.stoyanvuchev.squircleshape.demo.core.ui.component.layout.spacer.VerticalSpacer
import com.stoyanvuchev.squircleshape.demo.core.ui.component.text.Text
import org.jetbrains.compose.resources.painterResource
import squircleshape.composeapp.generated.resources.Res
import squircleshape.composeapp.generated.resources.arrow_back
import sv.lib.squircleshape.SquircleShape

@Composable
fun SectionTitle(
    title: String
) = Text(
    modifier = Modifier.padding(horizontal = 8.dp),
    text = title,
    style = Theme.typography.titleSmall,
    color = Theme.colorScheme.onSurfaceElevationHigh
)

@Composable
fun BodyText(
    text: String
) = Text(
    modifier = Modifier.padding(horizontal = 8.dp),
    text = text,
    style = Theme.typography.bodySmall,
    color = Theme.colorScheme.onSurface
)

@Composable
fun Card(
    card: ContentPiece.Card
) = Column(
    modifier = Modifier
        .cardBackgroundModifier()
        .padding(20.dp)
) {

    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Image(
            modifier = Modifier.size(28.dp),
            painter = painterResource(card.icon),
            contentDescription = null,
            colorFilter = ColorFilter.tint(color = Theme.colorScheme.primary)
        )

        HorizontalSpacer(width = 20.dp)

        Text(
            text = card.label,
            style = Theme.typography.titleSmall,
            color = Theme.colorScheme.onSurfaceElevationHigh
        )

    }

    VerticalSpacer(height = 16.dp)

    Text(
        text = card.text,
        style = Theme.typography.bodySmall,
        color = Theme.colorScheme.onSurface
    )

}

@Composable
fun CTA(
    cta: ContentPiece.CTA
) = Column(
    horizontalAlignment = Alignment.CenterHorizontally
) {

    Text(
        modifier = Modifier
            .padding(horizontal = 8.dp)
            .padding(bottom = 20.dp),
        text = cta.title,
        style = Theme.typography.titleSmall,
        color = Theme.colorScheme.onSurfaceElevationHigh,
        textAlign = TextAlign.Center
    )

    Button(
        onClick = cta.onAction,
        backgroundColor = Theme.colorScheme.accent,
        contentColor = Theme.colorScheme.onAccent
    ) {

        Text(
            modifier = Modifier.padding(start = 8.dp),
            text = cta.label,
            style = Theme.typography.titleSmall
        )

        HorizontalSpacer(width = 12.dp)

        Image(
            modifier = Modifier
                .graphicsLayer { rotationZ = 180f }
                .size(24.dp),
            painter = painterResource(Res.drawable.arrow_back),
            contentDescription = null,
            colorFilter = ColorFilter.tint(LocalContentColor.current)
        )

    }

}

@Composable
fun AuthorContent(
    author: ContentPiece.Author
) = Column(
    modifier = Modifier.padding(vertical = 10.dp),
    content = {

        Row(
            modifier = Modifier.padding(horizontal = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Image(
                modifier = Modifier
                    .size(48.dp)
                    .clip(SquircleShape()),
                painter = painterResource(author.avatarRes),
                contentDescription = "Avatar of ${author.name}",
                contentScale = ContentScale.Crop
            )

            Text(
                modifier = Modifier.padding(start = 20.dp),
                text = author.name,
                style = Theme.typography.titleSmall
            )

        }

        if (author.links.isNotEmpty()) {

            VerticalSpacer(height = 20.dp)

            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                content = { author.links.forEach { LinkButton(it) } }
            )

        }

    }
)

@Composable
fun LinksContent(
    section: ContentPiece.LinksSection
) = Column(
    modifier = Modifier.padding(vertical = 10.dp),
    content = {

        SectionTitle(title = section.title)

        VerticalSpacer(height = 20.dp)

        section.links.forEachIndexed { index, link ->
            if (index > 0) VerticalSpacer(height = 8.dp)
            LinkButton(link)
        }

    }
)

@Composable
private fun LinkButton(
    link: ContentPiece.Link
) {

    val uriHandler = LocalUriHandler.current

    Button(
        onClick = remember { { uriHandler.openUri(link.url) } },
        backgroundColor = Theme.colorScheme.surfaceElevationMedium,
        contentColor = Theme.colorScheme.onSurfaceElevationMedium,
        paddingValues = PaddingValues(horizontal = 16.dp, vertical = 10.dp)
    ) {

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {

            Image(
                modifier = Modifier
                    .size(24.dp)
                    .padding(link.iconPaddingDp),
                painter = painterResource(link.iconRes),
                contentDescription = null,
                colorFilter = ColorFilter.tint(LocalContentColor.current)
            )

            HorizontalSpacer(width = 12.dp)

            Text(
                text = link.label,
                style = Theme.typography.bodyLarge
            )

        }

    }

}

@Composable
fun SectionDivider() = HorizontalDivider(
    modifier = Modifier
        .padding(
            horizontal = 10.dp,
            vertical = 40.dp
        )
        .clip(Theme.shapes.small),
    color = Theme.colorScheme.onSurface.copy(alpha = .125f)
)