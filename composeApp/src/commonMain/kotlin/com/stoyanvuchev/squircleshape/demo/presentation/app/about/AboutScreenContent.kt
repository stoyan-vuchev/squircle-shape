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

package com.stoyanvuchev.squircleshape.demo.presentation.app.about

import androidx.compose.ui.unit.dp
import com.stoyanvuchev.squircleshape.demo.presentation.content.ContentGroup
import com.stoyanvuchev.squircleshape.demo.presentation.content.group
import com.stoyanvuchev.squircleshape.demo.presentation.defaults.LICENSE_STRING
import squircleshape.composeapp.generated.resources.Res
import squircleshape.composeapp.generated.resources.compose_multiplatform
import squircleshape.composeapp.generated.resources.github_icon
import squircleshape.composeapp.generated.resources.issue_icon
import squircleshape.composeapp.generated.resources.kotlin_icon
import squircleshape.composeapp.generated.resources.license
import squircleshape.composeapp.generated.resources.pfp

fun aboutScreenContent(
    onOsl: () -> Unit
): List<ContentGroup> = listOf(

    group(key = "intro") {
        text(
            text = "Squircle Shape is a production-ready squircle implementation " +
                    "for Compose Multiplatform."
        )
    },

    group(key = "links") {

        author(
            name = "Stoyan Vuchev",
            avatarRes = Res.drawable.pfp
        ) {
            github(url = "https://github.com/stoyan-vuchev")
            linkedIn(url = "https://linkedin.com/in/stoyan-vuchev")
        }

        links(title = "Project") {
            item(
                label = "GitHub Repository",
                url = "https://github.com/stoyan-vuchev/squircle-shape",
                icon = Res.drawable.github_icon
            )
            item(
                label = "Report an Issue",
                url = "https://github.com/stoyan-vuchev/squircle-shape/issues/new",
                icon = Res.drawable.issue_icon
            )
            itemWithAction(
                label = "Open Source Licenses",
                icon = Res.drawable.license,
                onAction = onOsl
            )
        }

        links(title = "Built with") {
            item(
                label = "Kotlin",
                url = "https://kotlinlang.org/",
                icon = Res.drawable.kotlin_icon,
                iconPaddingDp = 2.dp
            )
            item(
                label = "Compose Multiplatform",
                url = "https://kotlinlang.org/compose-multiplatform/",
                icon = Res.drawable.compose_multiplatform
            )
        }

    },

    group(key = "license") {
        title(title = "License")
        code(code = LICENSE_STRING)
    }

)