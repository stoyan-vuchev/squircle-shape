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

package com.stoyanvuchev.squircleshape.demo.core.ui.component.icon

import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.image.LandscapistImage
import com.stoyanvuchev.squircleshape.demo.core.ui.Theme

@Composable
fun AsyncIcon(
    icon: () -> Any?,
    modifier: Modifier = Modifier,
    contentDescription: String? = null,
    contentScale: ContentScale = ContentScale.Fit,
    tint: Color = Theme.colorScheme.onSurface,
    size: Dp? = 24.dp
) {

    val colorFilter = remember(tint) {
        if (tint == Color.Unspecified) null
        else ColorFilter.tint(color = tint)
    }

    val semanticsModifier = remember(contentDescription) {
        if (contentDescription != null) Modifier.semantics {
            this.contentDescription = contentDescription
            this.role = Role.Image
        } else Modifier
    }

    val sizeModifier = remember(size) {
        if (size != null) Modifier.size(size)
        else Modifier
    }

    val imageOptions by remember(colorFilter, contentScale) {
        derivedStateOf {
            ImageOptions(
                colorFilter = colorFilter,
                contentScale = contentScale
            )
        }
    }

    LandscapistImage(
        imageModel = icon,
        modifier = Modifier
            .then(semanticsModifier)
            .then(sizeModifier)
            .then(modifier),
        imageOptions = imageOptions
    )

}