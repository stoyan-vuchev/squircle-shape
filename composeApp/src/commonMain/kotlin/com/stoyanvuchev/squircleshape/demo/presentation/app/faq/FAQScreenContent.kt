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

package com.stoyanvuchev.squircleshape.demo.presentation.app.faq

import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.stoyanvuchev.squircleshape.demo.presentation.app.faq.components.CornerRadiusComparison
import com.stoyanvuchev.squircleshape.demo.presentation.app.faq.components.SquircleToRoundedCornerShapeComparison
import com.stoyanvuchev.squircleshape.demo.presentation.app.usage.components.BasicExample
import com.stoyanvuchev.squircleshape.demo.presentation.app.usage.components.CanvasExample
import com.stoyanvuchev.squircleshape.demo.presentation.app.usage.components.MaterialThemeExample
import com.stoyanvuchev.squircleshape.demo.presentation.content.ContentGroup
import com.stoyanvuchev.squircleshape.demo.presentation.content.group
import org.jetbrains.compose.resources.painterResource
import squircleshape.composeapp.generated.resources.Res
import squircleshape.composeapp.generated.resources.flowers
import sv.lib.squircleshape.SquircleShape
import kotlin.math.roundToInt

fun faqScreenContent(onGoToAbout: () -> Unit): List<ContentGroup> = listOf(

    group(key = "what-is-squircle") {

        title(title = "1. What is a Squircle?")

        text(
            text = "A squircle is a shape between a square and a circle. " +
                    "Unlike a rounded rectangle, which uses simple circular corner arcs, " +
                    "a squircle uses a continuous curve that blends smoothly " +
                    "between edges and corners."
        )

        text(
            text = "This produces softer geometry and is commonly used in modern UI systems " +
                    "such as app icons and interface components."
        )

    },

    group(key = "why-squircle") {

        title(title = "2. Why use Squircle Shape instead of Rounded Corner Shape?")

        text(
            text = "Rounded Corner Shape uses circular arcs for corners. " +
                    "While this works well in many cases, the transition between " +
                    "the edge and the corner can feel slightly abrupt."
        )

        composable(
            key = "comparison",
            content = { SquircleToRoundedCornerShapeComparison() }
        )

        text(
            text = "Squircle Shape uses a continuous curve, " +
                    "which produces smoother transitions and a more organic appearance."
        )

        text(
            text = "This makes it particularly suitable for expressive UI designs " +
                    "and modern interface styles."
        )

    },

    group(key = "squircle-performance") {
        title(title = "3. Does using Squircle Shape affect performance?")
        text(text = "No.")
        text(
            text = "The shape is rendered using a cached path and is designed " +
                    "to work efficiently with Compose's rendering system."
        )
        text(
            text = "It does not introduce additional recomposition overhead " +
                    "and performs similarly to other custom shapes in Compose."
        )
    },

    group(key = "squircle-with-material-theme") {
        title(title = "4. Can Squircle Shape be used with Material Theme?")
        text(text = "Yes.")
        text(
            text = "Squircle Shape can be applied directly to components " +
                    "using Modifier.clip() or integrated into MaterialTheme.shapes " +
                    "to be used across your UI."
        )
        spacer(key = "material-theme-example-top-spacer")
        composable(
            key = "composable",
            content = { MaterialThemeExample() }
        )
        spacer(key = "material-theme-example-bottom-spacer")
        code(
            code = "val shapes = Shapes(\n" +
                    "    small = Squircle Shape(radius = 10.dp, smoothing = CornerSmoothing.Medium),\n" +
                    "    medium = Squircle Shape(radius = 15.dp, smoothing = CornerSmoothing.Medium),\n" +
                    "    large = Squircle Shape(percent = 100, smoothing = CornerSmoothing.Medium)\n" +
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

    group(key = "supported-platforms") {
        title(title = "5. Which platforms are supported?")
        text(text = "The library is built for Compose Multiplatform and works on:")
        text(
            text = "• Android\n" +
                    "• iOS\n" +
                    "• Desktop (JVM)\n" +
                    "• Web (WasmJS)"
        )
    },

    group(key = "customize-squircle") {
        title(title = "6. Can I customize the squircle shape?")
        text(text = "Yes.")
        text(
            text = "Squircle Shape supports configurable corner radius and smoothing, " +
                    "allowing you to create a wide range of shapes — " +
                    "from subtle curves to highly rounded squircles."
        )
    },

    group(key = "corner-radii-difference") {
        title(title = "7. Why does the corner radius look smaller when smoothing increases?")
        text(
            text = "When the smoothing value increases, " +
                    "the squircle curve transitions more gradually " +
                    "between the edge and the corner."
        )

        composable(
            key = "corner-radius-comparison",
            content = { CornerRadiusComparison() }
        )

        text(
            text = "Instead of using a simple circular arc, " +
                    "the corner becomes part of a continuous curve."
        )
        text(
            text = "Because of this, the visual corner radius may appear slightly smaller, " +
                    "even though the radius value itself has not changed."
        )
        text(
            text = "If you want to maintain a similar visual curvature " +
                    "when increasing smoothing, you may need to slightly increase the radius."
        )
    },

    group(key = "why-was-squircle-created") {
        title(title = "8. Why was Squircle Shape created?")
        text(
            text = "Squircle Shape was created to explore expressive UI geometry in Compose " +
                    "and provide a flexible alternative to standard rounded shapes."
        )
        text(
            text = "It is designed to integrate naturally with Compose UI " +
                    "while giving developers more control over shape aesthetics."
        )
    },

    group(key = "squircle-mask") {
        title(title = "9. Can Squircle Shape be used for images, masks, or custom drawing?")
        text(text = "Yes.")
        text(text = "The library supports two main usage patterns.")
        title(title = "a). As a Shape")
        text(
            text = "You can use Squircle Shape with Modifier.clip() to clip images, " +
                    "containers, cards, or other composables."
        )
        text(text = "This is the most common approach when building UI components.")
        spacer(key = "basic-example-top-spacer")
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
        spacer(key = "basic-example-bottom-spacer")
        title(title = "b). Directly on a Canvas")
        text(
            text = "The library also provides DrawScope.drawSquircle() functions " +
                    "that allow you to draw squircles directly on a Canvas or inside a " +
                    "Modifier.drawBehind {} block."
        )
        text(text = "This is useful for:")
        text(
            text = "• Custom graphics\n" +
                    "• Charts or diagrams\n" +
                    "• Procedural UI elements\n" +
                    "• Complex drawing effects"
        )
        text(
            text = "Both Color and Brush versions are available, " +
                    "allowing you to use gradients and other advanced drawing styles."
        )
        spacer(key = "canvas-example-top-spacer", height = 0.dp)
        composable(
            key = "canvas-example",
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

    group(key = "animated-squircle") {
        title(title = "10. Can Squircle Shape be animated?")
        text(
            text = "Squircle Shape works well with Compose animations. " +
                    "You can animate parameters such as radius or smoothing " +
                    "to create dynamic transitions between different shapes."
        )
        text(
            text = "Animating these parameters allows you to create smooth morphing effects " +
                    "between rounded rectangles and more pronounced squircles."
        )
        spacer(key = "animated-example-top-spacer", height = 0.dp)
        composable(
            key = "animated-example-composable",
            span = 1,
            content = {

                Box(
                    modifier = Modifier
                        .size(256.dp)
                        .aspectRatio(1f),
                    contentAlignment = Alignment.Center
                ) {

                    val infiniteTransition = rememberInfiniteTransition()
                    val animatedFraction by infiniteTransition.animateFloat(
                        initialValue = 0f,
                        targetValue = 1f,
                        animationSpec = infiniteRepeatable(
                            animation = keyframes {
                                durationMillis = 1000
                                0f at 0
                                1f at durationMillis
                            },
                            repeatMode = RepeatMode.Reverse
                        )
                    )

                    val animatedRadius by remember {
                        derivedStateOf { 128.dp * animatedFraction }
                    }

                    val animatedSmoothing by remember {
                        derivedStateOf { (100 * animatedFraction).roundToInt() }
                    }

                    Image(
                        modifier = Modifier
                            .size(256.dp)
                            .clip(
                                shape = SquircleShape(
                                    radius = animatedRadius,
                                    smoothing = animatedSmoothing
                                )
                            ),
                        painter = painterResource(Res.drawable.flowers),
                        contentDescription = "Sunflowers image clipped to animated Squircle Shape.",
                        contentScale = ContentScale.Crop
                    )

                }

            }
        )
        code(
            code = "val infiniteTransition = rememberInfiniteTransition()\n" +
                    "val animatedFraction by infiniteTransition.animateFloat(\n" +
                    "    initialValue = 0f,\n" +
                    "    targetValue = 1f,\n" +
                    "    animationSpec = infiniteRepeatable(\n" +
                    "        animation = keyframes {\n" +
                    "            durationMillis = 1000\n" +
                    "            0f at 0\n" +
                    "            1f at durationMillis\n" +
                    "        },\n" +
                    "        repeatMode = RepeatMode.Reverse\n" +
                    "    )\n" +
                    ")\n" +
                    "\n" +
                    "val animatedRadius by remember {\n" +
                    "    derivedStateOf { 128.dp * animatedFraction }\n" +
                    "}\n" +
                    "\n" +
                    "val animatedSmoothing by remember {\n" +
                    "    derivedStateOf { (100 * animatedFraction).roundToInt() }\n" +
                    "}\n" +
                    "\n" +
                    "Image(\n" +
                    "    modifier = Modifier\n" +
                    "        .size(256.dp)\n" +
                    "        .clip(\n" +
                    "            shape = Squircle Shape(\n" +
                    "                radius = animatedRadius,\n" +
                    "                smoothing = animatedSmoothing\n" +
                    "            )\n" +
                    "        ),\n" +
                    "    painter = painterResource(Res.drawable.flowers),\n" +
                    "    contentDescription = \"Sunflowers image clipped to animated Squircle Shape.\",\n" +
                    "    contentScale = ContentScale.Crop\n" +
                    ")"
        )
    },

    group(key = "squircle-scale") {
        title(title = "11. Does the shape scale correctly when resized?")
        text(text = "Yes.")
        text(
            text = "Squircle Shape calculates its path based on the size of the composable " +
                    "it is applied to. This means the shape adapts automatically " +
                    "when the component changes size."
        )
        text(
            text = "You can safely use it in layouts where elements resize dynamically, " +
                    "such as responsive UI or animated components."
        )
    },

    group(key = "squircle-recomposition") {
        title(title = "12. Does Squircle Shape cause recompositions?")
        text(text = "No.")
        text(
            text = "The shape itself does not trigger recompositions. " +
                    "When used correctly with stable parameters, " +
                    "it behaves like other Compose shapes and does not " +
                    "introduce additional recomposition overhead."
        )
        text(
            text = "Only changes to the shape's parameters (such as radius or smoothing) " +
                    "will cause updates to the drawing path."
        )
    },

    group(key = "cta") {
        cta(
            title = "Wanna know more\nabout the project?",
            label = "About the Project",
            onAction = onGoToAbout
        )
    }

)