package com.example.ui.android.task.junior

import android.Manifest
import android.content.Context
import androidx.activity.result.ActivityResultLauncher
import androidx.appcompat.app.AlertDialog

fun ActivityResultLauncher<Array<String>>.requestLocationPermission() {
    this.launch(
        arrayOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
        )
    )
}

fun Map<String, Boolean>.allPermissionsGranted():Boolean {
    for (permissionGranted in this.values) {
        if (!permissionGranted) {
            return false
        }
    }
    return true
}