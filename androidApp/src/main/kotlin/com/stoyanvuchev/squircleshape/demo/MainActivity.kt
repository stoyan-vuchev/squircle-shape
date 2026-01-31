package com.stoyanvuchev.squircleshape.demo

import App
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.ui.platform.LocalContext
import com.stoyanvuchev.systemuibarstweaker.ProvideSystemUIBarsTweaker

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ProvideSystemUIBarsTweaker {

                val darkTheme = isSystemInDarkTheme()
                val colorScheme = when {

                    Build.VERSION.SDK_INT_FULL >= Build.VERSION_CODES_FULL.S -> {
                        val context = LocalContext.current
                        if (darkTheme) dynamicDarkColorScheme(context)
                        else dynamicLightColorScheme(context)
                    }

                    else -> {
                        if (darkTheme) darkColorScheme()
                        else lightColorScheme()
                    }

                }

                App(colorScheme = colorScheme)

            }
        }
    }

}