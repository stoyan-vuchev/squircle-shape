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

package com.stoyanvuchev.squircleshape.demo.presentation.app.usage.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.stoyanvuchev.squircleshape.demo.core.ui.component.text.Text
import com.stoyanvuchev.squircleshape.demo.core.ui.isInDarkThemeMode
import sv.lib.squircleshape.CornerSmoothing
import sv.lib.squircleshape.SquircleShape

@Composable
fun MaterialThemeExample() = Row(
    modifier = Modifier.padding(horizontal = 10.dp),
    horizontalArrangement = Arrangement.spacedBy(8.dp)
) {

    val shapes = Shapes(
        small = SquircleShape(radius = 10.dp, smoothing = CornerSmoothing.Medium),
        medium = SquircleShape(radius = 15.dp, smoothing = CornerSmoothing.Medium),
        large = SquircleShape(percent = 100, smoothing = CornerSmoothing.Medium)
    )

    val colorScheme by rememberUpdatedState(
        if (isInDarkThemeMode()) darkColorScheme()
        else lightColorScheme()
    )

    MaterialTheme(
        shapes = shapes,
        colorScheme = colorScheme
    ) {

        Button(
            onClick = remember { { /* Do something. */ } },
            shape = MaterialTheme.shapes.small,
            content = { Text(text = "Small", color = MaterialTheme.colorScheme.onPrimary) }
        )

        Button(
            onClick = remember { { /* Do something. */ } },
            shape = MaterialTheme.shapes.medium,
            content = { Text(text = "Medium", color = MaterialTheme.colorScheme.onPrimary) }
        )

        Button(
            onClick = remember { { /* Do something. */ } },
            shape = MaterialTheme.shapes.large,
            content = { Text(text = "Large", color = MaterialTheme.colorScheme.onPrimary) }
        )

    }

}