package com.stoyanvuchev.squircleshape.app

import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.window.ComposeViewport
import com.stoyanvuchev.squircleshape.demo.presentation.DemoApp
import kotlinx.browser.document

@OptIn(ExperimentalComposeUiApi::class)
fun main() {
    ComposeViewport(document.body!!) {
        DemoApp()
    }
}