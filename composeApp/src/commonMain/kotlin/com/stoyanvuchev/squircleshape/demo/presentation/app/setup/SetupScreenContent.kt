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

package com.stoyanvuchev.squircleshape.demo.presentation.app.setup

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.PointerIcon
import androidx.compose.ui.input.pointer.pointerHoverIcon
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.unit.dp
import com.stoyanvuchev.squircleshape.demo.core.ui.Theme
import com.stoyanvuchev.squircleshape.demo.core.ui.component.icon.AsyncIcon
import com.stoyanvuchev.squircleshape.demo.core.ui.component.interaction.rememberRipple
import com.stoyanvuchev.squircleshape.demo.core.ui.component.layout.spacer.HorizontalSpacer
import com.stoyanvuchev.squircleshape.demo.core.ui.component.text.Text
import com.stoyanvuchev.squircleshape.demo.domain.model.LatestRelease
import com.stoyanvuchev.squircleshape.demo.presentation.content.ContentGroup
import com.stoyanvuchev.squircleshape.demo.presentation.content.group
import squircleshape.composeapp.generated.resources.Res
import squircleshape.composeapp.generated.resources.github_icon

fun setupScreenContent(
    latestRelease: LatestRelease,
    onExploreUsage: () -> Unit
): List<ContentGroup> = listOf(

    group(key = "multiplatform-setup") {

        title(title = "Gradle Kotlin DSL (Multiplatform)")

        text(text = "1). Add the dependency in your shared module's build.gradle.kts")

        composable(
            key = "latest-version",
            content = {

                Row(
                    modifier = Modifier
                        .padding(horizontal = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Text(
                        text = "The latest release version is:",
                        style = Theme.typography.bodySmall,
                        color = Theme.colorScheme.onSurface
                    )

                    HorizontalSpacer(width = 8.dp)

                    SelectionContainer {
                        Text(
                            modifier = Modifier
                                .background(
                                    color = Theme.colorScheme.surfaceElevationMedium,
                                    shape = Theme.shapes.small
                                )
                                .padding(
                                    horizontal = 8.dp,
                                    vertical = 2.dp
                                ),
                            text = latestRelease.version,
                            style = Theme.typography.code,
                            color = Theme.colorScheme.accent
                        )
                    }

                    HorizontalSpacer(width = 8.dp)

                    val uriHandler = LocalUriHandler.current

                    AsyncIcon(
                        icon = { Res.drawable.github_icon },
                        modifier = Modifier
                            .pointerHoverIcon(PointerIcon.Hand)
                            .clickable(
                                interactionSource = remember { MutableInteractionSource() },
                                indication = rememberRipple(),
                                onClick = { uriHandler.openUri(latestRelease.url) }
                            ),
                        tint = Theme.colorScheme.accent,
                        size = 18.dp
                    )

                }

            }
        )

        code(
            code = "sourceSets {\n" +
                    " \n" +
                    "    commonMain.dependencies {\n" +
                    "\n" +
                    "        // ...\n\n" +
                    "        implementation(\"com.stoyanvuchev:squircle-shape:${latestRelease.version}\")\n" +
                    "      \n" +
                    "    }\n" +
                    "\n" +
                    "    // ...\n" +
                    "\n" +
                    "}"
        )

        spacer(key = "a")

        text(
            text = "Or if you're using a version catalog (e.g. libs.versions.toml)," +
                    " declare it in the catalog instead."
        )

        code(
            code = "[versions]\n" +
                    "squircleShape = \"${latestRelease.version}\"\n" +
                    "\n" +
                    "[libraries]\n" +
                    "squircle-shape = { group = \"com.stoyanvuchev\", name = \"squircle-shape\", version.ref = \"squircleShape\" }"
        )

        spacer(key = "b")

        text(text = "Then include the dependency in your shared module build.gradle.kts file.")

        code(
            code = "sourceSets {\n" +
                    "\n" +
                    "    commonMain.dependencies {\n" +
                    "\n" +
                    "        // ...\n\n" +
                    "        implementation(libs.squircle.shape)\n" +
                    "\n" +
                    "    }\n" +
                    "\n" +
                    "    // ...\n" +
                    "\n" +
                    "}"
        )

        spacer(key = "c")

        text(text = "2). Sync and rebuild the project.")

    },

    group(key = "android-setup") {

        title(title = "Gradle Kotlin DSL Setup (For Android-only projects)")

        text(text = "1). Add the Squircle Shape dependency in your module build.gradle.kts file.")

        composable(
            key = "latest-version",
            content = {

                Row(
                    modifier = Modifier
                        .padding(horizontal = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Text(
                        text = "The latest release version is:",
                        style = Theme.typography.bodySmall,
                        color = Theme.colorScheme.onSurface
                    )

                    HorizontalSpacer(width = 8.dp)

                    SelectionContainer {
                        Text(
                            modifier = Modifier
                                .background(
                                    color = Theme.colorScheme.surfaceElevationMedium,
                                    shape = Theme.shapes.small
                                )
                                .padding(
                                    horizontal = 8.dp,
                                    vertical = 2.dp
                                ),
                            text = latestRelease.version,
                            style = Theme.typography.code,
                            color = Theme.colorScheme.accent
                        )
                    }

                    HorizontalSpacer(width = 8.dp)

                    val uriHandler = LocalUriHandler.current

                    AsyncIcon(
                        icon = { Res.drawable.github_icon },
                        modifier = Modifier
                            .pointerHoverIcon(PointerIcon.Hand)
                            .clickable(
                                interactionSource = remember { MutableInteractionSource() },
                                indication = rememberRipple(),
                                onClick = { uriHandler.openUri(latestRelease.url) }
                            ),
                        tint = Theme.colorScheme.accent,
                        size = 18.dp
                    )

                }

            }
        )

        code(
            code = "dependencies {\n" +
                    "\n" +
                    "    // ...\n" +
                    "\n" +
                    "    implementation(\"com.stoyanvuchev:squircle-shape-android:${latestRelease.version}\")\n" +
                    "\n" +
                    "}"
        )

        spacer(key = "d")

        text(
            text = "Or if you're using a version catalog (e.g. libs.versions.toml)," +
                    " declare it in the catalog instead."
        )

        code(
            code = "[versions]\n" +
                    "squircleShape = \"${latestRelease.version}\"\n" +
                    "\n" +
                    "[libraries]\n" +
                    "squircle-shape = { group = \"com.stoyanvuchev\", name = \"squircle-shape-android\", version.ref = \"squircleShape\" }"
        )

        spacer(key = "e")

        text(text = "Then include the dependency in your app module build.gradle.kts file.")

        code(
            code = "dependencies {\n" +
                    "\n" +
                    "  // ...\n" +
                    "\n" +
                    "  implementation(libs.squircle.shape)\n" +
                    "\n" +
                    "}"
        )

        spacer(key = "f")

        text(text = "2). Sync and rebuild the project.")

    },

    group(key = "cta") {
        cta(
            title = "Ready to explore?",
            label = "Explore Usage",
            onAction = onExploreUsage
        )
    }

)