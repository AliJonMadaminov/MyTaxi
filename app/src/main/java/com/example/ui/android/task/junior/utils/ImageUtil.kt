package com.example.ui.android.task.junior.utils

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import androidx.core.graphics.drawable.toBitmap
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async

fun drawableToBitmap(markerDrawable: Drawable?): Bitmap? {
    return markerDrawable?.toBitmap(markerDrawable.intrinsicWidth, markerDrawable.intrinsicHeight)
}
