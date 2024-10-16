package com.stoyanvuchev.squircleshape.app

import App
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application

fun main() = application {

    Window(
        onCloseRequest = ::exitApplication,
        title = "Squircle Shape Demo"
    ) {

        App()

    }

}