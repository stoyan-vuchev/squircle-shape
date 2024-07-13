import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FixedSmallTopAppBar(
    title: String,
    navigationButton: @Composable () -> Unit = {},
    actions: @Composable RowScope.() -> Unit = {}
) {

    TopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.swapBackgroundForSurfaceVariant(),
            scrolledContainerColor = MaterialTheme.colorScheme.swapBackgroundForSurfaceVariant(),
            titleContentColor = MaterialTheme.colorScheme.onSurface
        ),
        title = {

            Text(
                modifier = Modifier.padding(horizontal = 8.dp),
                text = title
            )

        },
        navigationIcon = navigationButton,
        actions = actions
    )

}