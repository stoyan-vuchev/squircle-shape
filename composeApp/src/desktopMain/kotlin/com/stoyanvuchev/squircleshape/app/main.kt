package com.stoyanvuchev.squircleshape.app

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.stoyanvuchev.squircleshape.demo.presentation.DemoApp

fun main() = application {

    Window(
        onCloseRequest = ::exitApplication,
        title = "Squircle Shape Demo"
    ) {

        DemoApp()

    }

}