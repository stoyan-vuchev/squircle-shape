package com.stoyanvuchev.squircleshape.app

import App
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.core.bundle.Bundle
import com.stoyanvuchev.systemuibarstweaker.ProvideSystemUIBarsTweaker

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent { ProvideSystemUIBarsTweaker { App() } }
    }

}