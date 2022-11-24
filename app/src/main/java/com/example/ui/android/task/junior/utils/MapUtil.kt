package com.example.ui.android.task.junior.utils

import android.graphics.drawable.Drawable
import com.example.ui.android.task.junior.models.ZoomLevel
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions

fun GoogleMap.addMarkerIfNecessary(marker: Marker?, markerIcon: Drawable?, location:LatLng): Marker {
    if (marker == null) {

        val markerBitmap = drawableToBitmap(markerIcon)
        return this.addMarker(
            MarkerOptions()
                .icon(BitmapDescriptorFactory.fromBitmap(markerBitmap!!))
                .position(location)
        )!!
    }
        moveCamera(CameraUpdateFactory.newLatLngZoom(marker.position, ZoomLevel.STREETS.value))
        return marker

}

fun GoogleMap.setMinMaxZoomPreferences() {
    setMinZoomPreference(ZoomLevel.CITY.value)
    setMaxZoomPreference(ZoomLevel.BUILDINGS.value)
}