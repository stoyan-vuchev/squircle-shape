package sv.app.squircleshape.demo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.core.view.WindowCompat
import sv.app.squircleshape.demo.core.theme.SquircleShapeDemoTheme
import sv.app.squircleshape.demo.core.theme.swapBackgroundForSurfaceVariant
import sv.app.squircleshape.demo.presentation.AppNavHost

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {
            SquircleShapeDemoTheme {

                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.swapBackgroundForSurfaceVariant(),
                    content = { AppNavHost() }
                )

            }
        }

    }

}