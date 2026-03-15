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

package com.stoyanvuchev.squircleshape.demo.core.ui.component.code

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalClipboard
import androidx.compose.ui.unit.dp
import com.stoyanvuchev.squircleshape.demo.core.clipboard.clipEntryOf
import com.stoyanvuchev.squircleshape.demo.core.ui.Theme
import com.stoyanvuchev.squircleshape.demo.core.ui.component.codeBackgroundModifier
import com.stoyanvuchev.squircleshape.demo.core.ui.component.outlinedButtonBackgroundModifier
import com.stoyanvuchev.squircleshape.demo.core.ui.component.text.Text
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.painterResource
import squircleshape.composeapp.generated.resources.Res
import squircleshape.composeapp.generated.resources.checkmark_outlined
import squircleshape.composeapp.generated.resources.copy_outlined

@OptIn(FlowPreview::class)
@Composable
fun CodeBlock(
    modifier: Modifier = Modifier,
    code: String
) = Box(
    modifier = Modifier
        .codeBackgroundModifier()
        .then(modifier)
) {

    val verticalScrollState = rememberScrollState()
    val horizontalScrollState = rememberScrollState()

    SelectionContainer {

        Text(
            modifier = Modifier
                .heightIn(
                    min = 80.dp,
                    max = 256.dp
                )
                .verticalScroll(state = verticalScrollState)
                .horizontalScroll(state = horizontalScrollState)
                .padding(end = 64.dp)
                .padding(20.dp),
            text = code,
            style = Theme.typography.code
        )

    }

    val clipboard = LocalClipboard.current
    val scope = rememberCoroutineScope()
    var isCopied by remember { mutableStateOf(false) }
    val onCopy = remember<() -> Unit>(scope, isCopied) {
        {
            scope.launch {
                if (!isCopied) clipboard.setClipEntry(clipEntryOf(code))
                else this.cancel()
            }.invokeOnCompletion { isCopied = true }
        }
    }

    LaunchedEffect(isCopied) {
        snapshotFlow { isCopied }
            .debounce(1000L)
            .collectLatest { if (it) isCopied = false }
    }

    AnimatedContent(
        modifier = Modifier
            .padding(16.dp)
            .align(Alignment.TopEnd),
        targetState = isCopied,
        contentAlignment = Alignment.Center,
        transitionSpec = remember {
            { fadeIn() + scaleIn() togetherWith scaleOut() + fadeOut() }
        }
    ) { copied ->

        if (!copied) {

            Box(
                modifier = Modifier
                    .size(48.dp)
                    .outlinedButtonBackgroundModifier()
                    .clickable(
                        enabled = !copied,
                        onClick = onCopy
                    ),
                contentAlignment = Alignment.Center
            ) {

                Image(
                    modifier = Modifier.size(24.dp),
                    painter = painterResource(Res.drawable.copy_outlined),
                    contentDescription = null,
                    colorFilter = ColorFilter.tint(Theme.colorScheme.onSurface)
                )

            }

        } else {

            Box(
                modifier = Modifier
                    .size(48.dp)
                    .outlinedButtonBackgroundModifier(),
                contentAlignment = Alignment.Center
            ) {

                Image(
                    modifier = Modifier.size(24.dp),
                    painter = painterResource(Res.drawable.checkmark_outlined),
                    contentDescription = null,
                    colorFilter = ColorFilter.tint(Theme.colorScheme.onSurface)
                )

            }

        }

    }

}