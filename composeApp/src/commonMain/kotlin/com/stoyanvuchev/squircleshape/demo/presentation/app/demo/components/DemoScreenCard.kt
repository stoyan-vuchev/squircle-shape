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

package com.stoyanvuchev.squircleshape.demo.presentation.app.demo.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.unit.dp
import com.stoyanvuchev.squircleshape.demo.core.ui.Theme
import com.stoyanvuchev.squircleshape.demo.core.ui.component.cardBackgroundModifier
import com.stoyanvuchev.squircleshape.demo.core.ui.component.layout.spacer.HorizontalSpacer
import com.stoyanvuchev.squircleshape.demo.core.ui.component.layout.spacer.VerticalSpacer
import com.stoyanvuchev.squircleshape.demo.core.ui.component.text.Text
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource

@Composable
fun DemoScreenCard(
    modifier: Modifier = Modifier,
    icon: DrawableResource,
    label: String,
    text: String
) {

    Column(
        modifier = Modifier
            .cardBackgroundModifier()
            .then(modifier)
            .padding(20.dp)
    ) {

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Image(
                modifier = Modifier.size(28.dp),
                painter = painterResource(icon),
                contentDescription = null,
                colorFilter = ColorFilter.tint(color = Theme.colorScheme.primary)
            )

            HorizontalSpacer(width = 20.dp)

            Text(
                text = label,
                style = Theme.typography.titleSmall,
                color = Theme.colorScheme.onSurfaceElevationHigh
            )

        }

        VerticalSpacer(height = 16.dp)

        Text(
            text = text,
            style = Theme.typography.bodySmall,
            color = Theme.colorScheme.onSurface
        )

    }

}