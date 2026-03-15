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

package com.stoyanvuchev.squircleshape.demo.presentation.app.demo

import com.stoyanvuchev.squircleshape.demo.presentation.content.ContentGroup
import com.stoyanvuchev.squircleshape.demo.presentation.app.demo.components.DemoSquircle
import com.stoyanvuchev.squircleshape.demo.presentation.content.group
import com.stoyanvuchev.squircleshape.demo.presentation.defaults.LICENSE_STRING
import squircleshape.composeapp.generated.resources.Res
import squircleshape.composeapp.generated.resources.compose_multiplatform
import squircleshape.composeapp.generated.resources.performance
import squircleshape.composeapp.generated.resources.squircle_icon_bold

fun demoScreenContent(
    isLargeWidth: Boolean,
    onGetStarted: () -> Unit
): List<ContentGroup> = listOf(

    group(key = "intro") {

        text(
            text = if (isLargeWidth) "A production-ready squircle for Compose Multiplatform.\n" +
                    "Designed for expressive UI systems with zero overhead."
            else "A production-ready squircle for Compose Multiplatform. " +
                    "Designed for expressive UI systems with zero overhead."
        )

        composable(
            key = "demo-squircle",
            content = { DemoSquircle() }
        )

        card(
            icon = Res.drawable.performance,
            label = "Performance",
            text = "Path rendering with zero recomposition overhead."
        )

        card(
            icon = Res.drawable.squircle_icon_bold,
            label = "Composability",
            text = "Works with MaterialTheme and other custom UIs."
        )

        card(
            icon = Res.drawable.compose_multiplatform,
            label = "Multiplatform",
            text = "Android, iOS, Desktop (JVM), WasmJS supported."
        )

    },

    group(key = "cta") {
        cta(
            title = "Ready to implement?",
            label = "Get Started",
            onAction = onGetStarted
        )
    },

    group(key = "license") {
        title(title = "License")
        code(code = LICENSE_STRING)
    }

)