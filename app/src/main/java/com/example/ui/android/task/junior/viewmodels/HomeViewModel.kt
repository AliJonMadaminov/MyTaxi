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

    val currentLocation:MutableLiveData<LatLng> = MutableLiveData(LatLng(41.31114164054522, 69.27959980798161))

    private val _currentLocationName: MutableLiveData<String> = MutableLiveData()
    val currentLocationName: LiveData<String>
        get() = _currentLocationName

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

}

class HomeViewModelFactory(private val app: Application) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            return HomeViewModel(app) as T
        }

        throw java.lang.IllegalArgumentException("Unknown class has been passed. Expected: HomeViewModel")
    }

}