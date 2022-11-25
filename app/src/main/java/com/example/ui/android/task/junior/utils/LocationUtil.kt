package com.example.ui.android.task.junior.utils

import android.graphics.drawable.Drawable
import android.location.Address
import android.location.Geocoder
import com.example.ui.android.task.junior.models.ZoomLevel
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.coroutines.*

fun GoogleMap.addMarkerIfNecessary(
    marker: Marker?,
    markerIcon: Drawable?,
    location: LatLng
): Marker {
    val localMarker:Marker = if (marker == null) {

        val markerBitmap = drawableToBitmap(markerIcon)
         this.addMarker(
            MarkerOptions()
                .icon(BitmapDescriptorFactory.fromBitmap(markerBitmap!!))
                .position(location)
        )!!
    }else{
        marker
    }
    moveCamera(CameraUpdateFactory.newLatLngZoom(localMarker.position, ZoomLevel.STREETS.value))
    return localMarker

}

fun GoogleMap.setMinMaxZoomPreferences() {
    setMinZoomPreference(ZoomLevel.CITY.value)
    setMaxZoomPreference(ZoomLevel.BUILDINGS.value)
}

private fun getCurrentAddress(
    geocoder: Geocoder,
    location: LatLng
): List<Address> {

    return geocoder.getFromLocation(
        location.latitude,
        location.longitude,
        1
    )
}

fun Geocoder.getCurrentAddress(location: LatLng): Address? {
    var address: Address? = null
    val addressList = getCurrentAddress(this@getCurrentAddress, location)
    if (addressList.isNotEmpty()) {
        address = addressList[0]
    }
    return address
}