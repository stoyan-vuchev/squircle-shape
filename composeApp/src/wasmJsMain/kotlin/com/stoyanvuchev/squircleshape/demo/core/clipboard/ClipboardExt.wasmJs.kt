package com.stoyanvuchev.squircleshape.demo.core.clipboard

import androidx.compose.ui.platform.ClipEntry

actual fun clipEntryOf(string: String): ClipEntry = ClipEntry.withPlainText(string)