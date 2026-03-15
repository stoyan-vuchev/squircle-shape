package com.stoyanvuchev.squircleshape.demo.core.clipboard

import android.content.ClipData
import androidx.compose.ui.platform.ClipEntry

actual fun clipEntryOf(
    string: String
): ClipEntry = ClipEntry(
    ClipData.newPlainText(
        "",
        string
    )
)