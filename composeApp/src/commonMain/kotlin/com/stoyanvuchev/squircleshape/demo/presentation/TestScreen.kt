package com.stoyanvuchev.squircleshape.demo.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.layout
import androidx.compose.ui.unit.Velocity
import androidx.compose.ui.unit.dp
import com.stoyanvuchev.squircleshape.demo.core.ui.component.text.Text

@Composable
fun TestScreen() {
    val spacerHeight = remember { mutableStateOf(0f) }

    val nestedScrollConnection = remember {
        object : NestedScrollConnection {
            override fun onPreScroll(available: Offset, source: NestedScrollSource): Offset {
                if (available.y < 0) {
                    val old = spacerHeight.value
                    spacerHeight.value = (spacerHeight.value + available.y).coerceAtLeast(0f)
                    val conumed = spacerHeight.value - old

                    println("[onPreScroll]: spacerHeight: ${spacerHeight.value} $source")
                    return available.copy(y = conumed)
                }
                return super.onPreScroll(available, source)
            }

            override fun onPostScroll(
                consumed: Offset,
                available: Offset,
                source: NestedScrollSource
            ): Offset {
                if (available.y > 0) {
                    spacerHeight.value += available.y
                    println("[onPostScroll]: spacerHeight: ${spacerHeight.value} $source")

                    return available
                }
                return super.onPostScroll(consumed, available, source)
            }

            override suspend fun onPreFling(available: Velocity): Velocity {
                println("[onPreFling]: ${available.y}")
                return super.onPreFling(available)
            }

            override suspend fun onPostFling(consumed: Velocity, available: Velocity): Velocity {
                println("[onPostFling]: ${available.y}")
                return super.onPostFling(consumed, available)
            }
        }
    }

    LazyColumn(
        modifier = Modifier.nestedScroll(nestedScrollConnection)
    ) {
        item {
            Spacer(
                Modifier
                    .fillMaxWidth()
                    .background(color = Color.Black)
                    .layout { measurable, constraints ->
                        val placeable =
                            measurable.measure(
                                constraints.copy(
                                    minHeight = spacerHeight.value.toInt(),
                                    maxHeight = spacerHeight.value.toInt()
                                )
                            )
                        layout(constraints.maxWidth, spacerHeight.value.toInt()) {
                            placeable.place(0, 0)
                        }
                    })
        }

        items(50) {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(32.dp),
                text = "Card: $it"
            )
        }
    }
}