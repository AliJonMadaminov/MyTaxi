package com.example.ui.android.task.junior

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import androidx.core.graphics.drawable.toBitmap
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async

suspend fun drawableToBitmap(markerDrawable: Drawable?): Bitmap? {
    return CoroutineScope(Dispatchers.Default).async {
        markerDrawable?.toBitmap(markerDrawable.intrinsicWidth, markerDrawable.intrinsicHeight)
    }.await()
}
