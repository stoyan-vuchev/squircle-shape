package com.stoyanvuchev.squircleshape.demo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.stoyanvuchev.squircleshape.demo.presentation.UIEntryPoint
import com.stoyanvuchev.systemuibarstweaker.ProvideSystemUIBarsTweaker

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ProvideSystemUIBarsTweaker {

                UIEntryPoint()

            }
        }
    }

}