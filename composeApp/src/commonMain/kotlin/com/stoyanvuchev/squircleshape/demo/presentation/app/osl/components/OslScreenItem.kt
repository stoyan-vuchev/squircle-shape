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

package com.stoyanvuchev.squircleshape.demo.presentation.app.osl.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.mikepenz.aboutlibraries.entity.Library
import com.stoyanvuchev.squircleshape.demo.core.ui.Theme
import com.stoyanvuchev.squircleshape.demo.core.ui.component.cardBackgroundModifier
import com.stoyanvuchev.squircleshape.demo.core.ui.component.interaction.rememberRipple
import com.stoyanvuchev.squircleshape.demo.core.ui.component.layout.spacer.HorizontalSpacer
import com.stoyanvuchev.squircleshape.demo.core.ui.component.layout.spacer.VerticalSpacer
import com.stoyanvuchev.squircleshape.demo.core.ui.component.text.Text
import org.jetbrains.compose.resources.painterResource
import squircleshape.composeapp.generated.resources.Res
import squircleshape.composeapp.generated.resources.open

@Composable
fun OslScreenItem(
    modifier: Modifier = Modifier,
    library: () -> Library
) {

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .cardBackgroundModifier()
            .then(modifier)
    ) {

        Row(
            modifier = Modifier
                .padding(horizontal = 20.dp)
                .padding(top = 14.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {

            Text(
                modifier = Modifier.weight(1f),
                text = library().name,
                style = Theme.typography.bodyLarge,
                color = Theme.colorScheme.onSurfaceElevationHigh,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            library().artifactVersion?.let {
                Text(
                    text = it,
                    style = Theme.typography.label
                )
            }

        }

        VerticalSpacer(height = 4.dp)

        library().developers.forEachIndexed { index, developer ->
            if (index > 0) VerticalSpacer(height = 4.dp)
            Text(
                modifier = Modifier.padding(horizontal = 20.dp),
                text = developer.name ?: "",
                style = Theme.typography.bodySmall
            )
        }

        VerticalSpacer(height = 2.dp)

        Row(
            modifier = Modifier.padding(
                horizontal = 10.dp,
                vertical = 8.dp
            )
        ) {

            library().licenses.forEachIndexed { index, license ->

                if (index > 0) HorizontalSpacer(width = 4.dp)
                val uriHandler = LocalUriHandler.current

                Box(
                    modifier = Modifier
                        .clickable(
                            onClick = remember(license, uriHandler) {
                                { license.url?.let { uriHandler.openUri(it) } }
                            },
                            interactionSource = remember { MutableInteractionSource() },
                            indication = rememberRipple()
                        )
                        .padding(
                            horizontal = 14.dp,
                            vertical = 10.dp
                        ),
                    contentAlignment = Alignment.Center
                ) {

                    Text(
                        text = license.name,
                        style = Theme.typography.code,
                        color = Theme.colorScheme.accent
                    )

                }

            }

            library().scm?.url?.let {

                Spacer(modifier = Modifier.weight(1f))

                val uriHandler = LocalUriHandler.current

                Box(
                    modifier = Modifier
                        .clickable(
                            onClick = remember(it, uriHandler) {
                                { uriHandler.openUri(it) }
                            },
                            interactionSource = remember { MutableInteractionSource() },
                            indication = rememberRipple()
                        )
                        .padding(10.dp),
                    contentAlignment = Alignment.Center
                ) {

                    Image(
                        modifier = Modifier.size(24.dp),
                        painter = painterResource(Res.drawable.open),
                        contentDescription = null,
                        colorFilter = ColorFilter.tint(
                            color = Theme.colorScheme.primary
                        )
                    )

                }

            }

        }

    }

}