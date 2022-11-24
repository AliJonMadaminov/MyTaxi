package com.example.ui.android.task.junior.viewmodels

import android.app.Application
import android.location.Address
import android.location.Geocoder
import androidx.appcompat.content.res.AppCompatResources
import androidx.lifecycle.*
import com.example.ui.android.task.junior.R
import com.example.ui.android.task.junior.models.Client
import com.example.ui.android.task.junior.models.ZoomLevel
import com.example.ui.android.task.junior.utils.drawableToBitmap
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.coroutines.launch

class HomeViewModel(app: Application) : AndroidViewModel(app) {

    val client = Client("Iroda", "Saidova", "+998(93) 000-11-22", R.drawable.girl, listOf())

    private var marker: Marker? = null

    private val _currentLocationName: MutableLiveData<String> = MutableLiveData()
    val currentLocationName: LiveData<String>
        get() = _currentLocationName


    fun initializeMarker(googleMap: GoogleMap) {
        viewModelScope.launch {
            if (marker == null) {
                val amirTemurSquare = LatLng(41.31114164054522, 69.27959980798161)
                val markerDrawable = AppCompatResources.getDrawable(
                    getApplication<Application>().applicationContext,
                    R.drawable.blue_map_pin
                )

                val markerIcon = drawableToBitmap(markerDrawable)
                marker = googleMap.addMarker(
                    MarkerOptions()
                        .icon(BitmapDescriptorFactory.fromBitmap(markerIcon!!))
                        .position(amirTemurSquare)
                )
            }

            googleMap.moveCamera(
                CameraUpdateFactory.newLatLngZoom(
                    marker?.position!!,
                    ZoomLevel.STREETS.value
                )
            )
        }
    }

    fun moveMarkerWithCamera(googleMap: GoogleMap) {
        googleMap.setOnCameraMoveListener {
            if (marker != null) {
                marker?.position = googleMap.cameraPosition.target
            }
        }
    }

    fun updateCurrentLocationNameOnCameIdle(googleMap: GoogleMap, geocoder: Geocoder) {
        googleMap.setOnCameraIdleListener {

            val placeNames = geocoder.getFromLocation(
                googleMap.cameraPosition.target.latitude,
                googleMap.cameraPosition.target.longitude,
                2
            )

            updateCurrentLocationName(placeNames)
        }
    }

    private fun updateCurrentLocationName(placeNames: MutableList<Address>) {
        if (placeNames.isNotEmpty()) {
            val placeName = placeNames[0]
            if (placeName.featureName != null && placeName.subLocality != null) {

                _currentLocationName.postValue(buildString {
                    append(placeName.subLocality)
                    append(", ")
                    append(placeName.featureName)
                })
            }
        }
    }

    fun setMinMaxZoomPreferences(googleMap: GoogleMap) {
        googleMap.setMinZoomPreference(ZoomLevel.CITY.value)
        googleMap.setMaxZoomPreference(ZoomLevel.BUILDINGS.value)
    }

}

class HomeViewModelFactory(private val app: Application) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            return HomeViewModel(app) as T
        }

        throw java.lang.IllegalArgumentException("Unknown class has been passed. Expected: HomeViewModel")
    }

}