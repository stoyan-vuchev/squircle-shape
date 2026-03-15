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

package com.stoyanvuchev.squircleshape.demo.presentation.app.usage

import androidx.compose.ui.unit.dp
import com.stoyanvuchev.squircleshape.demo.presentation.app.usage.components.BasicExample
import com.stoyanvuchev.squircleshape.demo.presentation.app.usage.components.CanvasExample
import com.stoyanvuchev.squircleshape.demo.presentation.app.usage.components.MaterialThemeExample
import com.stoyanvuchev.squircleshape.demo.presentation.content.ContentGroup
import com.stoyanvuchev.squircleshape.demo.presentation.content.group

fun usageScreenContent(
    onFAQ: () -> Unit
): List<ContentGroup> = listOf(

    group(key = "intro") {

        title(title = "Let's begin with the shape skeleton")

        text(
            text = "The Squircle Shape is a corner-based shape " +
                    "similar to a typical Rounded Corner Shape."
        )

        text(
            text = "To make a shape instance, simply call a SquircleShape() function. " +
                    "The shape can be adjusted by using an optional parameters for a desired result."
        )

        code(
            code = "SquircleShape(\n" +
                    "    percent: Int, // 0 to 50 \n" +
                    "    smoothing: Int // 0 to 100 \n" +
                    ")" + "\n\n" +
                    "SquircleShape(\n" +
                    "    radius: Dp or Float\n" +
                    "    smoothing: Int\n" +
                    ")" + "\n\n" +
                    "SquircleShape(\n" +
                    "    topStart: Int, Dp or Float \n" +
                    "    topEnd: Int, Dp or Float \n" +
                    "    bottomStart: Int, Dp or Float\n" +
                    "    bottomEnd: Int, Dp, or Float \n" +
                    "    smoothing: Int\n" +
                    ")"
        )

    },

    group(key = "basic-example") {
        title(title = "Basic example")
        text(text = "A basic example of a Squircle shape applied to an Image.")
        spacer(key = "image-example-top-spacer")
        composable(
            key = "image-example",
            span = 1,
            content = { BasicExample() }
        )
        code(
            code = "Image(\n" +
                    "    modifier = Modifier\n" +
                    "        .size(256.dp)\n" +
                    "        .clip(shape = SquircleShape()),\n" +
                    "    painter = painterResource(Res.drawable.flowers),\n" +
                    "    contentDescription = \"Sunflowers image clipped to a Squircle Shape.\",\n" +
                    "    contentScale = ContentScale.Crop\n" +
                    ")"
        )
    },

    group(key = "material-theme-example") {
        title(title = "Material Theme example")
        text(text = "An example of reusable top-level shape implementation.")
        spacer(key = "a")
        composable(
            key = "composable",
            content = { MaterialThemeExample() }
        )
        spacer(key = "b")
        code(
            code = "val shapes = Shapes(\n" +
                    "    small = SquircleShape(radius = 10.dp, smoothing = CornerSmoothing.Medium),\n" +
                    "    medium = SquircleShape(radius = 15.dp, smoothing = CornerSmoothing.Medium),\n" +
                    "    large = SquircleShape(percent = 100, smoothing = CornerSmoothing.Medium)\n" +
                    ")\n" +
                    "\n" +
                    "MaterialTheme(\n" +
                    "    shapes = shapes\n" +
                    ") {\n" +
                    "\n" +
                    "    // ...\n" +
                    "\n" +
                    "    Row(\n" +
                    "        horizontalArrangement = Arrangement.spacedBy(8.dp)\n" +
                    "    ) {\n" +
                    "\n" +
                    "        Button(\n" +
                    "            onClick = { /* Do something. */ },\n" +
                    "            shape = MaterialTheme.shapes.small,\n" +
                    "            content = { Text(text = \"Small\") }\n" +
                    "        )\n" +
                    "\n" +
                    "        Button(\n" +
                    "            onClick = { /* Do something. */ },\n" +
                    "            shape = MaterialTheme.shapes.medium,\n" +
                    "            content = { Text(text = \"Medium\") }\n" +
                    "        )\n" +
                    "\n" +
                    "        Button(\n" +
                    "            onClick = { /* Do something. */ },\n" +
                    "            shape = MaterialTheme.shapes.large,\n" +
                    "            content = { Text(text = \"Large\") }\n" +
                    "        )\n" +
                    "\n" +
                    "    }\n" +
                    "\n" +
                    "    // ...\n" +
                    "\n" +
                    "}"
        )
    },

    group(key = "canvas-example") {
        title(title = "Canvas example")
        text(text = "An example of drawing a Squircle shape on a Canvas.")
        spacer(key = "canvas-example-top-spacer", height = 0.dp)
        composable(
            key = "composable",
            span = 1,
            content = { CanvasExample() }
        )
        code(
            code = "val primaryColor = Theme.colorScheme.primary\n" +
                    "val accentColor = Theme.colorScheme.accent\n" +
                    "\n" +
                    "Canvas(\n" +
                    "    modifier = Modifier.size(128.dp),\n" +
                    "    onDraw = {\n" +
                    "\n" +
                    "        val squircleSize = this.size * .73f\n" +
                    "        val cornerRadius = squircleSize.minDimension\n" +
                    "        val smoothing = 50\n" +
                    "        val borderWidth = 8.dp.toPx()\n" +
                    "\n" +
                    "        val secondarySquircleOffset = Offset(\n" +
                    "            x = this.size.width - squircleSize.width,\n" +
                    "            y = 0f\n" +
                    "        )\n" +
                    "\n" +
                    "        drawSquircle(\n" +
                    "            color = accentColor,\n" +
                    "            size = squircleSize,\n" +
                    "            topLeft = secondarySquircleOffset,\n" +
                    "            topLeftCorner = cornerRadius,\n" +
                    "            topRightCorner = cornerRadius,\n" +
                    "            bottomRightCorner = cornerRadius,\n" +
                    "            bottomLeftCorner = cornerRadius,\n" +
                    "            smoothing = smoothing,\n" +
                    "            style = Stroke(width = borderWidth)\n" +
                    "        )\n" +
                    "\n" +
                    "        val primarySquircleOffset = Offset(\n" +
                    "            x = 0f,\n" +
                    "            y = this.size.height - squircleSize.height\n" +
                    "        )\n" +
                    "\n" +
                    "        drawSquircle(\n" +
                    "            color = primaryColor,\n" +
                    "            size = squircleSize,\n" +
                    "            topLeft = primarySquircleOffset,\n" +
                    "            topLeftCorner = cornerRadius,\n" +
                    "            topRightCorner = cornerRadius,\n" +
                    "            bottomRightCorner = cornerRadius,\n" +
                    "            bottomLeftCorner = cornerRadius,\n" +
                    "            smoothing = smoothing,\n" +
                    "            style = Stroke(width = borderWidth)\n" +
                    "        )\n" +
                    "\n" +
                    "    }\n" +
                    ")"
        )
    },

    group(key = "cta") {
        cta(
            title = "For further information",
            label = "Proceed to FAQ",
            onAction = onFAQ
        )
    }

)